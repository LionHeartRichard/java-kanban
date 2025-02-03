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
		String[] fields = value.split(",");

		if (fields.length != 5 && fields.length != 7)
			throw new IOException("Invalid value format for TaskInterfaceDeserializer: " + value);

		String type = fields[0];
		String id = fields[1];
		String name = fields[2];
		String description = fields[3];
		Status status = Status.valueOf(fields[4]);
		String startTime = null;
		int duration = 0;

		if (fields.length == 7) {
			startTime = fields[5];
			duration = Integer.parseInt(fields[6]);
		}

		if ("subtasks".equals(type)) {
			if (fields.length == 7)
				return new Subtask(type, id, name, description, status, startTime, duration);
			return new Subtask(type, id, name, description, status);
		} else if ("tasks".equals(type)) {
			if (fields.length == 7)
				return new Task(type, id, name, description, status, startTime, duration);
			return new Task(type, id, name, description, status);
		} else if ("epics".equals(type)) {
			if (fields.length == 7)
				return new Epic(type, id, name, description, status, startTime, duration);
			return new Epic(type, id, name, description, status);
		} else {
			throw new IOException("Unknown type: " + type);
		}
	}
}
