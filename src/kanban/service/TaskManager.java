package kanban.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kanban.model.TaskInterface;
import kanban.util.Graph;

public class TaskManager {
	/*
	 * Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую
	 * коллекцию. Методы для каждого из типа задач(Задача/Эпик/Подзадача): a.
	 * Получение списка всех задач. b. Удаление всех задач. c. Получение по
	 * идентификатору. d. Создание. Сам объект должен передаваться в качестве
	 * параметра. e. Обновление. Новая версия объекта с верным идентификатором
	 * передаётся в виде параметра. f. Удаление по идентификатору. Дополнительные
	 * методы: a. Получение списка всех подзадач определённого эпика. Управление
	 * статусами осуществляется по следующему правилу: a. Менеджер сам не выбирает
	 * статус для задачи. Информация о нём приходит менеджеру вместе с информацией о
	 * самой задаче. По этим данным в одних случаях он будет сохранять статус, в
	 * других будет рассчитывать. b. Для эпиков: если у эпика нет подзадач или все
	 * они имеют статус NEW, то статус должен быть NEW. если все подзадачи имеют
	 * статус DONE, то и эпик считается завершённым — со статусом DONE. во всех
	 * остальных случаях статус должен быть IN_PROGRESS.
	 */

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
			addTaskNotCheckNull(topTask); // check - repeat add!!!!!!!!!!
			String idByTopTask = topTask.getId();
			for (TaskInterface task : tasks) {
				addTaskNotCheckNull(task);
				cacheGraph.addEdgeWithoutCheckNullByKeyMap(idByTopTask, task.getId());
			}
		}
		return isInit;
	}

	private void addTaskNotCheckNull(TaskInterface task) { // check - repeat add!!!!!!!!!!
		task.registerMyself(cacheFactory);
		cacheGraph.addVertex(task.getId());
	}

}
