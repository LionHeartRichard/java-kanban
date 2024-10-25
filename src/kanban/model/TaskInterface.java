package kanban.model;

import kanban.service.TaskFactory;
import kanban.util.Status;

public interface TaskInterface {

	default void registerMyself(TaskFactory factory) {
		factory.register(getId(), this);
	}

	String getId();

	boolean changeStatus();

	Status getStatus();

	boolean setName(String name);

	String getName();

	boolean setDescription(String description);

	String getDescription();

}
