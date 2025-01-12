package kanban.model.impl;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

	protected String type;
	protected String id;
	protected String name;
	protected String description;
	protected Status status;

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
	public Task(@JsonProperty("type") String type, @JsonProperty("id") String id, @JsonProperty("name") String name,
			@JsonProperty("description") String description, @JsonProperty("status") Status status) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
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
		return id == other.id;
	}

	@Override
	@JsonValue
	public String toString() {
		return type + "," + id + "," + name + "," + description + "," + status;
	}
}
