package kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;

import kanban.service.TaskFactory;
import kanban.util.Status;

public interface TaskInterface {

	default void registerMyself(TaskFactory factory) {
		factory.register(getId(), this);
	}

	LocalDateTime getStartTime();
	
	Duration getDuration();

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
