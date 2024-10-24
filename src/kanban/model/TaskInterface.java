package kanban.model;

import kanban.model.util.Status;

public interface TaskInterface {

	String getId();

	boolean changeStatus();

	Status getStatus();

	boolean setName(String name);

	String getName();

	boolean setDescription(String description);

	String getDescription();

}
