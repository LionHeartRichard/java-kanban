package kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;

import kanban.service.TaskFactory;
import kanban.util.Status;

public interface TaskInterface {

	default boolean validDuration(TaskInterface other) {
		LocalDateTime start = this.getStartTime();
		LocalDateTime end = this.getEndTime();
		if (start.isBefore(other.getStartTime()) && end.isAfter(other.getStartTime()))
			return false;
		if (start.isBefore(other.getEndTime()) && end.isAfter(other.getEndTime()))
			return false;
		return true;
	}

	default void registerMyself(TaskFactory factory) {
		factory.register(getId(), this);
	}

	LocalDateTime getStartTime();

	Duration getDuration();

	LocalDateTime getEndTime();

	String getType();

	String getId();

	String getName();

	String getDescription();

	Status getStatus();

	boolean changeStatus();

	void setType(String type);

	void setId(String id);

	void setName(String name);

	void setDescription(String description);

	void setStatus(Status status);
}
