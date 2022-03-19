package io.dsql.cdc.consumer.db;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Todo implements Serializable {

	@PrimaryKey
	private UUID id;

	private String task;

	private boolean status;

}
