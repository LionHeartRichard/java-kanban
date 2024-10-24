package kanban.model.impl;

public class Subtask extends Task {

	public Subtask(Task task, long id) {
		super(task);
		this.id = id;
	}

}
