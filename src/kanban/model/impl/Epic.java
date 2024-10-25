package kanban.model.impl;

import kanban.util.Status;

public class Epic extends Task {

	private static final String PREFIX = "Epic-";
	private static long count;

	public Epic() {
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}

	public Epic(String name, String description) {
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}
}
