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

	private Graph<String> cacheGraph;
	// использую для хранения всех взаимоотношений классов имплементирующих
	// TaskInterface
	// храню в виде topId -> midleId -> subId -> .. и тд. нет ограничений по
	// декомпозиции
	// например Epic -> Task -> Subtask

	private TaskFactory cacheFactory;
	// содержит все объекты классов имплементирующих TaskIntarface

	public TaskManager() {
		cacheFactory = new TaskFactory();
		cacheGraph = new Graph<String>();
	}

	public TaskManager(List<TaskInterface> tasks) {
		if (tasks == null || tasks.isEmpty()) {
			cacheFactory = new TaskFactory();
			cacheGraph = new Graph<String>();
			return;
		}
		cacheFactory = getTaskFactory(tasks);
		cacheGraph = getGraph();
	}

	private TaskFactory getTaskFactory(List<TaskInterface> tasks) {
		TaskFactory factory = new TaskFactory();
		tasks.forEach(v -> v.registerMyself(factory));
		return factory;
	}
	// метод использует дизайн паттерн "Registry"
	// реализованный в TaskFactory,
	// и который по соглашению должны реализовывать все имплементации TaskIntarface
	// по сути заношу все объекты в мапу (поле у класса TaskFactory)

	private Graph<String> getGraph() {
		Graph<String> graph = new Graph<>();
		cacheFactory.getTasks().forEach(v -> graph.addVertex(v.getId()));
		return graph;
	}
	// добавляю все ИД в качестве ключей мапы
	// напротив них в качестве значений будет сформирован Set<Strung>
	// Set - будет потенциально но необязательно содержать идентификаторы подзадач в
	// будущем
	// когда будем использовать перегруженный метод addTask
	// который принимает в качестве параметров TaskInterface topTask, TaskInterface
	// task
	// первым агрументом будет задача верхнего уровня
	// далее передаем задачу ниже по уровню

	public boolean addTask(TaskInterface task) {
		if (task == null || cacheFactory.containsTask(task.getId()))
			return false;
		task.registerMyself(cacheFactory);
		cacheGraph.addVertex(task.getId());
		return true;
	}

	public boolean addTask(TaskInterface topTask, TaskInterface task) {
		if (topTask != null && task != null) {
			if (!cacheFactory.containsTask(topTask.getId())) {
				addTaskNotCheckNull(topTask);
			}
			if (!cacheFactory.containsTask(task.getId())) {
				addTaskNotCheckNull(task);
			}
			cacheGraph.addEdgeWithoutCheckNullByKeyMap(topTask.getId(), task.getId());
			return true;
		}
		return false;
	}
	// метод который позволяет сразу добавлять задачу и подзадачу
	// или если они уже добавлены позволяет определить их взаимоотношение

	public boolean addTask(TaskInterface topTask, Set<TaskInterface> tasks) {
		if (topTask != null && tasks != null && !tasks.isEmpty()) {
			addTaskNotCheckNull(topTask);
			String idTopTask = topTask.getId();
			for (TaskInterface task : tasks) {
				addTaskNotCheckNull(task);
				cacheGraph.addEdgeWithoutCheckNullByKeyMap(idTopTask, task.getId());
			}
			return true;
		}
		return false;
	}

	public boolean addTask(TaskInterface topTask, List<TaskInterface> tasks) {
		if (topTask != null && tasks != null && !tasks.isEmpty()) {
			addTaskNotCheckNull(topTask);
			String idTopTask = topTask.getId();
			for (TaskInterface task : tasks) {
				addTaskNotCheckNull(task);
				cacheGraph.addEdgeWithoutCheckNullByKeyMap(idTopTask, task.getId());
			}
			return true;
		}
		return false;
	}
	// еще один вариант
	// метод кпозволяет сразу добавлять задачу и подзадачу, но уже принимает лист
	// подзадач

	private void addTaskNotCheckNull(TaskInterface task) { // check - repeat add!!!!!!!!!!
		task.registerMyself(cacheFactory);
		cacheGraph.addVertex(task.getId());
	}
	// для внутреннего использования добавлен для быстродействия - применяется когда
	// не нужны проверки на null

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
			Set<String> tmp = cacheGraph.DFS(id); // получаем все идентификаторы
			tmp.forEach(v -> result.add(cacheFactory.getTaskByIdNotCheckNull(id))); // получаем обекты из фабрики
			return result;
		}
		return null;
	}
	// позволяет получить набор всех подзадач, используя обход в глубину

	public List<TaskInterface> getSubtasks(String id) {
		if (cacheFactory.containsTask(id)) {
			List<TaskInterface> result = new ArrayList<>();
			List<String> tmp = cacheGraph.BFS(id);
			tmp.forEach(v -> result.add(cacheFactory.getTaskByIdNotCheckNull(id)));
			return result;
		}
		return null;
	}
	// позволяет получить набор всех подзадач, используя обход в ширину

	public List<TaskInterface> getAllTasks() {
		return cacheFactory.getTasks();
	}

	public Set<TaskInterface> getAllSetTasks() {
		return cacheFactory.getSetTasks();
	}

	public boolean updateTask(String id, String newName, String newDescription) {
		if (cacheFactory.containsTask(id)) {
			cacheFactory.getTaskById(id).setName(newName);
			cacheFactory.getTaskById(id).setDescription(newDescription);
			return true;
		}
		return false;
	}

	public boolean updateTask(TaskInterface task) {
		if (cacheFactory.containsTask(task.getId())) {
			cacheFactory.getTaskById(task.getId()).setName(task.getName());
			cacheFactory.getTaskById(task.getId()).setDescription(task.getDescription());
			return true;
		}
		return false;
	}

	public List<TaskInterface> getListTasksByStatus(Status status) {
		return cacheFactory.getListTasksByStatus(status);
	}

	public boolean changeStatusSubtask(String id) {
		if (!cacheFactory.containsTask(id))
			return false;
		TaskInterface task = cacheFactory.getTaskByIdNotCheckNull(id);
		if (task instanceof Subtask) {
			cacheFactory.getTaskByIdNotCheckNull(id).changeStatus();
			return true;
		}
		return false;
	}

	public boolean changeStatusTask(String id) {
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
		return false;
	}

	// реализовал изменение статуса задач таким образом - что задача должна пройти
	// все стадии
	// сначала NEW, потом IN_PROGRESS, затем DONE
	// поэтому они расчитываются в базовом классе Task
	// в данном классе также проверяется что все подзадачи имеют соответствующий
	// статус и только в таком случаее задача верхнего уровня может перейти в
	// следующий статус

	public boolean changeStatusEpic(String id) {
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
		return false;
	}

}
