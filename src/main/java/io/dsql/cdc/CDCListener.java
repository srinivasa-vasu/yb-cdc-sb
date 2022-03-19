package io.dsql.cdc;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import org.springframework.stereotype.Component;

import static io.debezium.data.Envelope.FieldName.AFTER;
import static io.debezium.data.Envelope.FieldName.BEFORE;
import static io.debezium.data.Envelope.FieldName.OPERATION;
import static io.debezium.data.Envelope.Operation;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class CDCListener {

	private final Executor executor = Executors.newSingleThreadExecutor();

	private final ITodoService todoService;

	// private final MessageSender sender;

	private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

	public CDCListener(Configuration connectorConfig, ITodoService todoService) {

		this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
				.using(connectorConfig.asProperties()).notifying(this::handleChangeEvent).build();
		this.todoService = todoService;
	}

	private void handleChangeEvent(RecordChangeEvent<SourceRecord> event) {
		SourceRecord sourceRecord = event.record();
		log.info("Key = '" + sourceRecord.key() + "' value = '" + sourceRecord.value() + "'");
		Struct value = (Struct) sourceRecord.value();
		if (value != null) {
			Operation operation = Operation.forCode((String) value.get(OPERATION));
			if (operation != Operation.READ) {
				String record = operation == Operation.DELETE ? BEFORE : AFTER;
				Struct struct = (Struct) value.get(record);
				Map<String, Object> payload = struct.schema().fields().stream().map(Field::name)
						.filter(fieldName -> struct.get(fieldName) != null)
						.map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
						.collect(toMap(Pair::getKey, Pair::getValue));
				todoService.replicateData(payload, operation);
				// sender.sendEvent(payload);
				log.info("Updated Data: {} with Operation: {}", payload, operation.name());
			}
		}
	}

	@PostConstruct
	private void start() {
		this.executor.execute(debeziumEngine);
	}

	@PreDestroy
	private void stop() throws IOException {
		if (this.debeziumEngine != null) {
			this.debeziumEngine.close();
		}
	}

}
