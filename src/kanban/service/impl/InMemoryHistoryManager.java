package kanban.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kanban.model.TaskInterface;
import kanban.service.HistoryManager;
import kanban.util.CuctomList;
import kanban.util.Node;

public class InMemoryHistoryManager implements HistoryManager {

	private CuctomList<String, TaskInterface> history;

	public InMemoryHistoryManager() {
		history = new CuctomList<>();
	}

	@Override
	public void add(TaskInterface task) {
		history.add(task);
	}

	@Override
	public List<TaskInterface> getHistory() {
		return history.getQueue();
	}

	@Override
	public void remove(String id) {
		history.remove(id);
	}

}
