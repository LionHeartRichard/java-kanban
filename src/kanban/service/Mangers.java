package kanban.service;

import kanban.service.impl.InMemoryHistoryManager;
import kanban.service.impl.InMemoryTaskManager;

public class Mangers {

	public static TaskManager getDefault() {
		return new InMemoryTaskManager();
	}

	public static HistoryManager getDefaultHistory() {
		return new InMemoryHistoryManager();
	}
}
