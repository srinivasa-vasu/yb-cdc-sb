package io.dsql.cdc;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope.Operation;
import io.dsql.cdc.consumer.db.ITodoRepository;
import io.dsql.cdc.consumer.db.Todo;

import org.springframework.stereotype.Service;

@Service
public class TodoService implements ITodoService {

	private final ITodoRepository todoRepository;

	TodoService(ITodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public void replicateData(Map<String, Object> todoEvent, Operation operation) {
		final Todo todo = new ObjectMapper().convertValue(todoEvent, Todo.class);
		if (Operation.DELETE == operation) {
			todoRepository.deleteById(todo.getId());
		}
		else {
			todoRepository.save(todo);
		}
	}

}
