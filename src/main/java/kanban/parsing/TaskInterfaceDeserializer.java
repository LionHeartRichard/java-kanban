package kanban.parsing;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Status;

public class TaskInterfaceDeserializer extends JsonDeserializer<TaskInterface> {
	@Override
	public TaskInterface deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(jsonParser);

		String value = node.textValue();
		String[] filds = value.split(",");

		if (filds.length != 5 && filds.length != 7)
			throw new IOException("Invalid value format for TaskInterfaceDeserializer: " + value);

		String type = filds[0];
		String id = filds[1];
		String name = filds[2];
		String description = filds[3];
		Status status = Status.valueOf(filds[4]);
		String startTime = null;
		int duration = 0;

		if (filds.length == 7) {
			startTime = filds[5];
			duration = Integer.parseInt(filds[6]);
		}

		if ("SUBTASK".equals(type)) {
			if (filds.length == 7)
				return new Subtask(type, id, name, description, status, startTime, duration);
			return new Subtask(type, id, name, description, status);
		} else if ("TASK".equals(type)) {
			if (filds.length == 7)
				return new Task(type, id, name, description, status, startTime, duration);
			return new Task(type, id, name, description, status);
		} else if ("EPIC".equals(type)) {
			if (filds.length == 7)
				return new Epic(type, id, name, description, status, startTime, duration);
			return new Epic(type, id, name, description, status);
		} else {
			throw new IOException("Unknown type: " + type);
		}
	}
}
