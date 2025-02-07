package kanban.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kanban.comparatorscustom.StartTimeTaskComparator;
import kanban.model.TaskInterface;
import kanban.service.HistoryManager;
import kanban.service.Managers;
import kanban.service.TaskFactory;
import kanban.service.TaskManager;
import kanban.util.Graph;
import kanban.util.Status;
import lombok.Getter;
import lombok.Setter;

public class InMemoryTaskManager implements TaskManager {

	@JsonIgnore
	@Getter
	protected Set<TaskInterface> prioritizedTasks;

	public boolean containsPrioritizedTasks(TaskInterface task) {
		return prioritizedTasks.contains(task);
	}

	@Getter
	@Setter
	protected Graph<TaskInterface> graph;
	// использую для хранения всех взаимоотношений классов имплементирующих
	// TaskInterface
	// храню в виде topId -> midleId -> subId -> .. и тд. нет ограничений по
	// декомпозиции
	// например Epic -> Task -> Subtask

	@JsonIgnore
	@Getter
	@Setter
	protected TaskFactory factory;
	// содержит все объекты классов имплементирующих TaskIntarface
	// реализует паттерн register

	@JsonIgnore
	private HistoryManager history = Managers.getDefaultHistory();

	public InMemoryTaskManager() {
		factory = new TaskFactory();
		graph = new Graph<>();
		prioritizedTasks = new TreeSet<>(new StartTimeTaskComparator());
	}

	public InMemoryTaskManager(Graph<TaskInterface> graph) {
		this.graph = graph;
	}
	// этот конструктор используется в кастомной десериализации JSON

	@JsonIgnore
	@Override
	public boolean isEmpty() {
		return factory.isEmpty();
	}

	@Override
	public int size() {
		return factory.size();
	}

	@Override
	public boolean addTask(TaskInterface task) {
		if (task == null || factory.containsTask(task.getId()))
			return false;
		if (isValidDateForTask(task))
			prioritizedTasks.add(task);
		addTaskNotCheckNull(task);
		return true;
	}

	private boolean isValidDateForTask(TaskInterface task) {
		if (task.getStartTime() == null || task.getDuration() == null)
			return false;
		if (!prioritizedTasks.isEmpty() && prioritizedTasks.stream().noneMatch(t -> t.validDuration(task)))
			return false;
		return true;
	}

	@Override
	public boolean addTask(TaskInterface topTask, TaskInterface task) {
		if (topTask == null || task == null)
			return false;
		if (isValidDateForTask(topTask))
			prioritizedTasks.add(topTask);
		if (isValidDateForTask(task))
			prioritizedTasks.add(task);
		if (!factory.containsTask(topTask.getId()))
			addTaskNotCheckNull(topTask);
		if (!factory.containsTask(task.getId()))
			addTaskNotCheckNull(task);
		graph.addEdgeWithoutCheckNullByKeyMap(topTask, task);
		return true;
	}
	// метод который позволяет сразу добавлять задачу и подзадачу
	// или если они уже добавлены позволяет определить их взаимоотношение

	private void addTaskNotCheckNull(TaskInterface task) {
		task.registerMyself(factory);
		graph.addVertex(task);
	}
	// для внутреннего использования добавлен,
	// для быстродействия - применяется когда
	// не нужны проверки на null

	@Override
	public boolean containsTaskById(String id) {
		return factory.containsTask(id);
	}

	@Override
	public void removeTasks() {
		factory.removeTasks();
		graph.removeVertices();
		prioritizedTasks.clear();
	}

	@JsonIgnore
	@Override
	public TaskInterface getTaskById(String id) {
		TaskInterface currentTask = factory.getTaskById(id);
		history.add(currentTask);
		return currentTask;
	}

	@Override
	public boolean removeTaskById(String id) {
		if (!factory.containsTask(id))
			return false;
		TaskInterface tmp = factory.getTaskById(id);
		if (isValidDateForTask(tmp) && prioritizedTasks.contains(tmp)) {
			prioritizedTasks.remove(tmp);
		}
		factory.removeTaskById(id);
		graph.removeVertex(tmp);
		return true;
	}

	@JsonIgnore
	@Override
	public Set<TaskInterface> getSetSubtasks(String id) {
		if (factory.containsTask(id)) {
			TaskInterface tmp = factory.getTaskById(id);
			return graph.depthFirstSearch(tmp);
		}
		return null;
	}
	// позволяет получить набор всех подзадач, используя обход в глубину

	@JsonIgnore
	@Override
	public List<TaskInterface> getSubtasks(String id) {
		if (factory.containsTask(id)) {
			TaskInterface tmp = factory.getTaskById(id);
			return graph.breadthFirstSearch(tmp);
		}
		return null;
	}
	// позволяет получить набор всех подзадач, используя обход в ширину

	@JsonIgnore
	@Override
	public List<TaskInterface> getAllTasks() {
		return factory.getTasks();
	}

	@JsonIgnore
	@Override
	public Set<TaskInterface> getAllSetTasks() {
		return factory.getSetTasks();
	}

	@Override
	public boolean updateTask(TaskInterface task) {
		if (task.getStartTime() != null && !isValidDateForTask(task))
			return false;
		if (factory.update(task) && graph.update(task)) {
			if (isValidDateForTask(task)) {
				prioritizedTasks.remove(task);
				prioritizedTasks.add(task);
			}
			return true;
		}
		return false;
	}

	@JsonIgnore
	@Override
	public List<TaskInterface> getListTasksByStatus(Status status) {
		return factory.getListTasksByStatus(status);
	}

	@Override
	public boolean changeStatusTask(String id) {
		if (!factory.containsTask(id))
			return false;
		TaskInterface tmp = factory.getTaskById(id);
		Set<TaskInterface> subtasks = graph.depthFirstSearch(tmp);
		// если у задачи нет подзадач вызваем метод изменяющий
		// статус текущей задачи
		if (subtasks == null || subtasks.isEmpty()) {
			factory.getTaskByIdNotCheckNull(id).changeStatus();
			return true;
		}
		// проверяем подзадачи на их
		// исполнение если все задачи выполнены или находятся в стадии отличной от
		// задачи верхнего
		// уровня то мы можем поменять задачу верхнего уровня
		Status topStatus = factory.getTaskByIdNotCheckNull(id).getStatus();
		for (TaskInterface subtask : subtasks) {
			Status subStatus = factory.getTaskByIdNotCheckNull(subtask.getId()).getStatus();

			if (subStatus == Status.NEW || subStatus == topStatus)
				return false;
		}
		factory.getTaskByIdNotCheckNull(id).changeStatus();
		return true;
	}

	@JsonIgnore
	@Override
	public List<TaskInterface> getHistory() {
		return history.getHistory();
	}
}
