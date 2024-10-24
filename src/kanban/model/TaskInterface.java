package kanban.model;

import kanban.model.util.Status;

public interface TaskInterface {

	long getId();

	boolean changeStatus();

	Status getStatus();

	void setName(String name);

	String getName();

	void setDescription(String description);

	String getDescription();

}
