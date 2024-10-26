package kanban.model.impl;

import kanban.util.Status;

public class Subtask extends Task {

	private static final String PREFIX = "Subtask-";
	private static long count;

	public Subtask() {
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}

	public Subtask(String name, String description) {
		++count;
		id = PREFIX + count;
		status = Status.NEW;
		this.name = name;
		this.description = description;
	}

}
