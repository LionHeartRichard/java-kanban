package main.kanban.parsing;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import main.kanban.model.impl.Epic;
import main.kanban.model.impl.Subtask;
import main.kanban.model.impl.Task;
import main.kanban.util.Status;

public class TaskInterfaceKeyDeserializer extends KeyDeserializer {

	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {

		String[] filds = key.split(",");

		if (filds.length != 5)
			throw new IOException("Invalid key format for TaskInterfaceKeyDeserializer: " + key);

		String type = filds[0];
		String id = filds[1];
		String name = filds[2];
		String description = filds[3];
		Status status = Status.valueOf(filds[4]);

		if ("SUBTASK".equals(type)) {
			return new Subtask(type, id, name, description, status);
		} else if ("TASK".equals(type)) {
			return new Task(type, id, name, description, status);
		} else if ("EPIC".equals(type)) {
			return new Epic(type, id, name, description, status);
		} else {
			throw new IOException("Unknown type: " + type);
		}
	}
}