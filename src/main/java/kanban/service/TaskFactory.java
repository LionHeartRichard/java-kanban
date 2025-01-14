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

	private Map<String, TaskInterface> mapFactory = new HashMap<>();

	public int size() {
		return mapFactory.size();
	}

	public boolean isEmpty() {
		return mapFactory.isEmpty();
	}

	public void register(String id, TaskInterface task) {
		mapFactory.put(id, task);
	}

	public boolean containsTask(String id) {
		return mapFactory.containsKey(id);
	}

	public void removeTasks() {
		mapFactory.clear();
	}

	public TaskInterface getTaskById(String id) {
		if (mapFactory.containsKey(id))
			return mapFactory.get(id);
		return null;
	}

	public TaskInterface getTaskByIdNotCheckNull(String id) {
		return mapFactory.get(id);
	}

	public void removeTaskById(String id) {
		if (mapFactory.containsKey(id))
			mapFactory.remove(id);
	}

	public Map<String, TaskInterface> getMapContainsAllTasks() {
		return mapFactory;
	}

	public List<TaskInterface> getListTasksByStatus(Status status) {
		List<TaskInterface> result = new ArrayList<>();
		for (TaskInterface task : mapFactory.values()) {
			if (task.getStatus() == status)
				result.add(task);
		}
		return result;
	}

	public List<TaskInterface> getTasks() {
		List<TaskInterface> result = new ArrayList<>(mapFactory.values());
		return result;
	}

	public Set<TaskInterface> getSetTasks() {
		Set<TaskInterface> result = new HashSet<>(mapFactory.values());
		return result;
	}

}
