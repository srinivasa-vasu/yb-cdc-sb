package io.dsql.cdc;

import java.util.Map;

import io.debezium.data.Envelope.Operation;

public interface ITodoService {

	void replicateData(Map<String, Object> customerData, Operation operation);

}
