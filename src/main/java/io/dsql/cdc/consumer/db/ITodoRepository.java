package io.dsql.cdc.consumer.db;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITodoRepository extends CassandraRepository<Todo, UUID> {

}
