package kanban.model.impl;

import java.util.Objects;

import kanban.model.TaskInterface;
import kanban.model.util.Status;

public class Task implements TaskInterface {

	protected long id;
	protected String name;
	protected String description;
	protected Status status;

	public Task(long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		status = Status.NEW;
	}

	public Task(Task task) {
		this.id = task.id;
		this.name = task.name;
		this.description = task.description;
		status = task.status;
	}

	@Override
	public long getId() {
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
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDescription(String descrition) {
		this.description = descrition;
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

}
