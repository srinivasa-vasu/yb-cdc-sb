package io.dsql.cdc;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.connect.data.Struct;

@FunctionalInterface
public interface PayloadExtract {

	Map<String, Object> payload(Struct data);

	default <T> T convert(Struct data, Class<T> toValueType) {
		return new ObjectMapper().convertValue(payload(data), toValueType);
	}

}
