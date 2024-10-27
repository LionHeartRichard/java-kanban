package kanban.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Graph;
import kanban.util.Status;

public class TaskManager {

	private boolean isInit = false;
	private Graph<String> cacheGraph;
	private TaskFactory cacheFactory;

	public boolean initManagerTasks(List<TaskInterface> tasks) {
		if (tasks == null || tasks.isEmpty())
			return isInit;
		cacheFactory = getTaskFactory(tasks);
		cacheGraph = getGraph();
		isInit = true;
		return isInit;
	}

	private TaskFactory getTaskFactory(List<TaskInterface> tasks) {
		TaskFactory factory = new TaskFactory();
		tasks.forEach(v -> v.registerMyself(factory));
		return factory;
	}

	private Graph<String> getGraph() {
		Graph<String> graph = new Graph<>();
		cacheFactory.getTasks().forEach(v -> graph.addVertex(v.getId()));
		return graph;
	}

	public boolean addTask(TaskInterface task) {
		if (isInit) {
			if (cacheFactory.containsTask(task.getId()))
				return false;
			task.registerMyself(cacheFactory);
			cacheGraph.addVertex(task.getId());
			return isInit;
		}
		return isInit;
	}

	public boolean addTask(TaskInterface topTask, TaskInterface task) {
		if (isInit) {
			if (!cacheFactory.containsTask(topTask.getId())) {
				addTaskNotCheckNull(topTask);
			}
			if (!cacheFactory.containsTask(task.getId())) {
				addTaskNotCheckNull(task);
			}
			cacheGraph.addEdgeWithoutCheckNullByKeyMap(topTask.getId(), task.getId());

		}
		return isInit;
	}

	public boolean addTask(TaskInterface topTask, Set<TaskInterface> tasks) {
		if (isInit) {
			addTaskNotCheckNull(topTask);
			String idTopTask = topTask.getId();
			for (TaskInterface task : tasks) {
				addTaskNotCheckNull(task);
				cacheGraph.addEdgeWithoutCheckNullByKeyMap(idTopTask, task.getId());
			}
		}
		return isInit;
	}

	public boolean addTask(TaskInterface topTask, List<TaskInterface> tasks) {
		if (isInit) {
			addTaskNotCheckNull(topTask);
			String idTopTask = topTask.getId();
			for (TaskInterface task : tasks) {
				addTaskNotCheckNull(task);
				cacheGraph.addEdgeWithoutCheckNullByKeyMap(idTopTask, task.getId());
			}
		}
		return isInit;
	}

	private void addTaskNotCheckNull(TaskInterface task) { // check - repeat add!!!!!!!!!!
		task.registerMyself(cacheFactory);
		cacheGraph.addVertex(task.getId());
	}

	public boolean containsTaskById(String id) {
		return cacheFactory.containsTask(id);
	}

	public void removeTasks() {
		cacheFactory.removeTasks();
		cacheGraph.removeVertices();
	}

	public TaskInterface getTaskById(String id) {
		return cacheFactory.getTaskById(id);
	}

	public boolean removeTaskById(String id) {
		if (!cacheFactory.containsTask(id))
			return false;
		cacheFactory.removeTaskById(id);
		cacheGraph.removeVertex(id);
		return true;
	}

	public Set<TaskInterface> getQuickSubtasks(String id) {
		if (cacheFactory.containsTask(id)) {
			Set<TaskInterface> result = new HashSet<>();
			Set<String> tmp = cacheGraph.DFS(id);
			tmp.forEach(v -> result.add(cacheFactory.getTaskByIdNotCheckNull(id)));
			return result;
		}
		return null;
	}

	public List<TaskInterface> getSubtasks(String id) {
		if (cacheFactory.containsTask(id)) {
			List<TaskInterface> result = new ArrayList<>();
			List<String> tmp = cacheGraph.BFS(id);
			tmp.forEach(v -> result.add(cacheFactory.getTaskByIdNotCheckNull(id)));
			return result;
		}
		return null;
	}

	public List<TaskInterface> getAllTasks() {
		return cacheFactory.getTasks();
	}

	public Set<TaskInterface> getAllSetTasks() {
		return cacheFactory.getSetTasks();
	}

	public TaskInterface getTask(String id) {
		return cacheFactory.getTaskById(id);
	}

	public boolean updateTask(String id, String newName, String newDescription) {
		if (isInit) {
			if (cacheFactory.containsTask(id)) {
				cacheFactory.getTaskById(id).setName(newName);
				cacheFactory.getTaskById(id).setDescription(newDescription);
				return true;
			}
			return false;
		}
		return false;
	}

	public List<TaskInterface> getListTasksByStatus(Status status) {
		return cacheFactory.getListTasksByStatus(status);
	}

	public boolean changeStatusSubtask(String id) {
		if (isInit) {
			if (!cacheFactory.containsTask(id))
				return false;
			TaskInterface task = cacheFactory.getTaskByIdNotCheckNull(id);
			if (task instanceof Subtask) {
				cacheFactory.getTaskByIdNotCheckNull(id).changeStatus();
				return true;
			}
		}
		return false;
	}

	public boolean changeStatusTask(String id) {
		if (isInit) {
			if (!cacheFactory.containsTask(id))
				return false;
			TaskInterface task = cacheFactory.getTaskByIdNotCheckNull(id);
			if (task instanceof Task) {
				Set<TaskInterface> subtasks = getQuickSubtasks(id);
				for (TaskInterface subtask : subtasks) {
					if (subtask.getStatus() != Status.DONE)
						return false;
				}
				cacheFactory.getTaskByIdNotCheckNull(id).changeStatus();
				return true;
			}
		}
		return false;
	}

	public boolean changeStatusEpic(String id) {
		if (isInit) {
			if (!cacheFactory.containsTask(id))
				return false;
			TaskInterface epic = cacheFactory.getTaskByIdNotCheckNull(id);
			if (epic instanceof Epic) {
				Set<TaskInterface> tasks = getQuickSubtasks(id);
				for (TaskInterface task : tasks) {
					if (task.getStatus() != Status.DONE)
						return false;
				}
				cacheFactory.getTaskByIdNotCheckNull(id).changeStatus();
				return true;
			}
		}
		return false;
	}

}
