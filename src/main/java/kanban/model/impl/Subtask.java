package kanban.model.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import kanban.util.Status;

public class Subtask extends Task {

	private static final String PREFIX = "S-";
	private static long count;

	public Subtask() {
		type = "SUBTASK";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}

	public Subtask(String name, String description) {
		type = "SUBTASK";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
		this.name = name;
		this.description = description;
	}

	@JsonCreator
	public Subtask(String type, String id, String name, String description, Status status) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	@JsonCreator
	public Subtask(String type, String id, String name, String description, Status status, String startTime,
			int durationMinutes) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.startTime = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
		this.duration = Duration.ofMinutes(durationMinutes);
	}

	@Override
	@JsonValue
	public String toString() {
		if (startTime != null && duration != null)
			return type + "," + id + "," + name + "," + description + "," + status + ","
					+ startTime.format(DATE_TIME_FORMATTER) + "," + duration.toMinutes();
		return type + "," + id + "," + name + "," + description + "," + status;
	}
}
