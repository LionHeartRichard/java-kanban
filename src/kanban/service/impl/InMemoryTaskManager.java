package kanban.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kanban.service.Mangers;
import kanban.model.TaskInterface;
import kanban.service.HistoryManager;
import kanban.service.TaskFactory;
import kanban.service.TaskManager;
import kanban.util.Graph;
import kanban.util.Status;

public class InMemoryTaskManager implements TaskManager {
	private Graph<String> cacheGraph;
	// использую для хранения всех взаимоотношений классов имплементирующих
	// TaskInterface
	// храню в виде topId -> midleId -> subId -> .. и тд. нет ограничений по
	// декомпозиции
	// например Epic -> Task -> Subtask

	private TaskFactory cacheFactory;
	// содержит все объекты классов имплементирующих TaskIntarface

	private HistoryManager history = Mangers.getDefaultHistory();

	public InMemoryTaskManager() {
		cacheFactory = new TaskFactory();
		cacheGraph = new Graph<String>();
	}

	public InMemoryTaskManager(List<TaskInterface> tasks) {
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

	@Override
	public boolean isEmpty() {
		return cacheFactory.isEmpty();
	}

	@Override
	public int size() {
		return cacheFactory.size();
	}

	@Override
	public boolean addTask(TaskInterface task) {
		if (task == null || cacheFactory.containsTask(task.getId()))
			return false;
		task.registerMyself(cacheFactory);
		cacheGraph.addVertex(task.getId());
		return true;
	}

	@Override
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

	private void addTaskNotCheckNull(TaskInterface task) {
		task.registerMyself(cacheFactory);
		cacheGraph.addVertex(task.getId());
	}
	// для внутреннего использования добавлен для быстродействия - применяется когда
	// не нужны проверки на null

	@Override
	public boolean containsTaskById(String id) {
		return cacheFactory.containsTask(id);
	}

	@Override
	public void removeTasks() {
		cacheFactory.removeTasks();
		cacheGraph.removeVertices();
	}

	@Override
	public TaskInterface getTaskById(String id) {
		TaskInterface currentTask = cacheFactory.getTaskById(id);
		history.add(currentTask);
		return currentTask;
	}

	@Override
	public boolean removeTaskById(String id) {
		if (!cacheFactory.containsTask(id))
			return false;
		cacheFactory.removeTaskById(id);
		cacheGraph.removeVertex(id);
		return true;
	}

	@Override
	public Set<TaskInterface> getSetSubtasks(String id) {
		if (cacheFactory.containsTask(id)) {
			Set<TaskInterface> result = new HashSet<>();
			Set<String> tmp = cacheGraph.DFS(id); // получаем все идентификаторы
			tmp.forEach(v -> result.add(cacheFactory.getTaskByIdNotCheckNull(v))); // получаем обекты из фабрики
			return result;
		}
		return null;
	}
	// позволяет получить набор всех подзадач, используя обход в глубину

	@Override
	public List<TaskInterface> getSubtasks(String id) {
		if (cacheFactory.containsTask(id)) {
			List<TaskInterface> result = new ArrayList<>();
			List<String> tmp = cacheGraph.BFS(id);
			tmp.forEach(v -> result.add(cacheFactory.getTaskByIdNotCheckNull(v)));
			return result;
		}
		return null;
	}
	// позволяет получить набор всех подзадач, используя обход в ширину

	@Override
	public List<TaskInterface> getAllTasks() {
		return cacheFactory.getTasks();
	}

	@Override
	public Set<TaskInterface> getAllSetTasks() {
		return cacheFactory.getSetTasks();
	}

	@Override
	public boolean updateTask(String id, String newName, String newDescription) {
		if (cacheFactory.containsTask(id)) {
			cacheFactory.getTaskById(id).setName(newName);
			cacheFactory.getTaskById(id).setDescription(newDescription);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateTask(TaskInterface task) {
		if (cacheFactory.containsTask(task.getId())) {
			cacheFactory.getTaskById(task.getId()).setName(task.getName());
			cacheFactory.getTaskById(task.getId()).setDescription(task.getDescription());
			return true;
		}
		return false;
	}

	@Override
	public List<TaskInterface> getListTasksByStatus(Status status) {
		return cacheFactory.getListTasksByStatus(status);
	}

	@Override
	public boolean changeStatusTask(String id) {
		if (!cacheFactory.containsTask(id))
			return false;
		Set<String> subtasks = cacheGraph.DFS(id); // если у задачи нет подзадач вызваем метод изменяющий статус
													// текущей задачи
		if (subtasks == null || subtasks.isEmpty()) {
			cacheFactory.getTaskByIdNotCheckNull(id).changeStatus();
			return true;
		}
		// проверяем подзадачи на их
		// исполнение если все задачи выполнены или находятся в стадии отличной от
		// задачи верхнего
		// уровня то мы можем поменять задачу верхнего уровня
		Status topStatus = cacheFactory.getTaskByIdNotCheckNull(id).getStatus();
		for (String idSubtask : subtasks) {
			Status subStatus = cacheFactory.getTaskByIdNotCheckNull(idSubtask).getStatus();

			if (subStatus == Status.NEW || subStatus == topStatus)
				return false;
		}
		cacheFactory.getTaskByIdNotCheckNull(id).changeStatus();
		return true;
	}

	@Override
	public List<TaskInterface> getHistory() {
		return history.getHistory();
	}
}
