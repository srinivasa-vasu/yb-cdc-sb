package io.dsql.cdc;

import io.debezium.data.Envelope.Operation;
import io.dsql.cdc.consumer.db.ITodoRepository;
import io.dsql.cdc.consumer.db.Todo;
import org.apache.kafka.connect.data.Struct;

import org.springframework.stereotype.Service;

@Service
public class TodoService implements ITodoService {

	private final ITodoRepository todoRepository;

	TodoService(ITodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public void replicateData(PayloadExtract payloadExtract, Struct record, Operation operation) {
		var todo = payloadExtract.convert(record, Todo.class);
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
