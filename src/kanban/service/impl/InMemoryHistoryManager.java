package kanban.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kanban.model.TaskInterface;
import kanban.service.HistoryManager;

public class InMemoryHistoryManager implements HistoryManager {

	private Map<String, TaskInterface> history;

	public InMemoryHistoryManager() {
		history = new HashMap<>();
	}

	@Override
	public void add(TaskInterface task) {
		history.put(task.getId(), task);
	}

	@Override
	public List<TaskInterface> getHistory() {
		return new ArrayList<>(history.values());
	}

}
