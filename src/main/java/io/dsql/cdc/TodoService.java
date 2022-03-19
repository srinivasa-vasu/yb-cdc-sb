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
		var todo = new ObjectMapper().convertValue(todoEvent, Todo.class);
		if (Operation.DELETE == operation) {
			todoRepository.deleteById(todo.getId());
		}
		else {
			var _todo = todoRepository.findById(todo.getId());
			_todo.ifPresent(state -> state.setStatus(todo.isStatus()));
			todoRepository.save(_todo.isPresent() ? _todo.get() : todo);
		}
	}

}
