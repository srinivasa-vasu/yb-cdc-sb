package io.dsql.cdc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {

	@Value("${supplier.datasource.host}")
	private String supplierHost;

	@Value("${supplier.datasource.database}")
	private String supplierDBId;

	@Value("${supplier.datasource.port}")
	private String supplierPort;

	@Value("${supplier.datasource.username}")
	private String userName;

	@Value("${supplier.datasource.password}")
	private String secret;

	@Value("${supplier.datasource.streamId}")
	private String streamId;

	@Value("${supplier.datasource.tableList}")
	private String tableList;

	@Bean
	public io.debezium.config.Configuration sourceConnector() {
		return io.debezium.config.Configuration.create()
				.with("name", "ybdb-source-connector")
				.with("connector.class", "io.debezium.connector.yugabytedb.YugabyteDBConnector")
				.with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
				.with("offset.storage.file.filename", "/tmp/offsets.dat")
				.with("offset.flush.interval.ms", "60000")
				.with("database.server.name", "ybdb1")
				.with("database.hostname", supplierHost)
				.with("database.port", supplierPort)
				.with("database.user", userName)
				.with("database.password", secret)
				.with("database.dbname", supplierDBId)
				.with("table.include.list", tableList)
				.with("include.schema.changes", "true")
				.with("database.master.addresses", supplierHost + ":7100")
				.with("database.streamid", streamId)
				.with("snapshot.mode", "never").build();
	}

}
