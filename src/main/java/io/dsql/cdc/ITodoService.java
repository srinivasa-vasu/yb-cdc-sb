package io.dsql.cdc;

import io.debezium.data.Envelope.Operation;
import org.apache.kafka.connect.data.Struct;

public interface ITodoService {

	void replicateData(PayloadExtract payloadExtract, Struct record, Operation operation);

}
