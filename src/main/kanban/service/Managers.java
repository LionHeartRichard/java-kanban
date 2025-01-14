package main.kanban.service;

import main.kanban.service.impl.InMemoryHistoryManager;
import main.kanban.service.impl.InMemoryTaskManager;

public class Managers {

	public static TaskManager getDefault() {
		return new InMemoryTaskManager();
	}

	public static HistoryManager getDefaultHistory() {
		return new InMemoryHistoryManager();
	}
}
