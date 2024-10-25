package kanban.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kanban.model.TaskInterface;
import kanban.util.Status;

public class TaskFactory {

	private Map<String, TaskInterface> map = new HashMap<>();

	public void register(String id, TaskInterface task) {
		map.put(id, task);
	}

	public void clearTasks() {
		map.clear();
	}

	public TaskInterface getTaskById(String id) {
		if (map.containsKey(id))
			return map.get(id);
		return null;
	}

	public void deleteTaskById(String id) {
		if (map.containsKey(id))
			map.remove(id);
	}

	public Map<String, TaskInterface> getMapContainsAllTasks() {
		return map;
	}

	public List<TaskInterface> getListTasksByStatus(Status status) {
		List<TaskInterface> result = new ArrayList<>();
		for (TaskInterface task : map.values()) {
			if (task.getStatus() == status)
				result.add(task);
		}
		return result;
	}

}
