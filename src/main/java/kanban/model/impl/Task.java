package kanban.model.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import kanban.model.TaskInterface;
import kanban.util.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task implements TaskInterface {

	private static final String PREFIX = "T-";
	private static long count;
	@JsonIgnore
	protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

	protected String type;
	protected String id;
	protected String name;
	protected String description;
	protected Status status;
	protected LocalDateTime startTime;
	protected Duration duration;

	public Task() {
		type = "TASK";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}

	public Task(String name, String description) {
		type = "TASK";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
		this.name = name;
		this.description = description;
	}

	@JsonCreator
	public Task(String type, String id, String name, String description, Status status) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	@JsonCreator
	public Task(String type, String id, String name, String description, Status status, String startTime,
			int duration) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.startTime = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
		this.duration = Duration.ofMinutes(duration);
	}

	@Override
	@JsonIgnore
	public LocalDateTime getEndTime() {
		return startTime.plus(duration);
	}

	@Override
	public boolean changeStatus() {
		if (status == Status.NEW) {
			status = Status.IN_PROGRESS;
			return true;
		}
		if (status == Status.IN_PROGRESS) {
			status = Status.DONE;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return id.equals(other.id);
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
