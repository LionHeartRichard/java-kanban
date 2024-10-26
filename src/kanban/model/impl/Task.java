package kanban.model.impl;

import java.util.Objects;
import kanban.model.TaskInterface;
import kanban.util.Status;

public class Task implements TaskInterface {

	private static final String PREFIX = "Task-";
	private static long count;

	protected String id;
	protected String name;
	protected String description;
	protected Status status;

	public Task() {
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}

	public Task(String name, String description) {
		++count;
		id = PREFIX + count;
		status = Status.NEW;
		this.name = name;
		this.description = description;
	}

	@Override
	public String getId() {
		return id;
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
	public Status getStatus() {
		return status;
	}

	@Override
	public boolean setName(String name) {
		if (name == null || name.isEmpty())
			return false;
		this.name = name;
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean setDescription(String descrition) {
		if (descrition == null || descrition.isEmpty())
			return false;
		this.description = descrition;
		return true;
	}

	@Override
	public String getDescription() {
		return description;
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
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", status=" + status + "]";
	}

}
