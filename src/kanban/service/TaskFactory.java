package kanban.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kanban.model.TaskInterface;
import kanban.util.Status;

public class TaskFactory {

	private Map<String, TaskInterface> map = new HashMap<>();

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public void register(String id, TaskInterface task) {
		map.put(id, task);
	}

	public boolean containsTask(String id) {
		return map.containsKey(id);
	}

	public void removeTasks() {
		map.clear();
	}

	public TaskInterface getTaskById(String id) {
		if (map.containsKey(id))
			return map.get(id);
		return null;
	}

	public TaskInterface getTaskByIdNotCheckNull(String id) {
		return map.get(id);
	}

	public void removeTaskById(String id) {
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

	public List<TaskInterface> getTasks() {
		List<TaskInterface> result = new ArrayList<>(map.values());
		return result;
	}

	public Set<TaskInterface> getSetTasks() {
		Set<TaskInterface> result = new HashSet<>(map.values());
		return result;
	}

}
