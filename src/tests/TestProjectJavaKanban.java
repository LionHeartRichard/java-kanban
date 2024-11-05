package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.service.TaskManager;
import kanban.util.Graph;
import kanban.util.Status;

public class TestProjectJavaKanban {

	private static final int COUNT_TASKS = 8;

	@Test
	public void testConstructorManagerTasks() {
		System.out.println("testConstructorManagerTasks");

		List<TaskInterface> tasks = new ArrayList<>();
		tasks.add(new Task("task1", "description-task-1"));
		tasks.add(new Task("task2", "description-task-2"));
		tasks.add(new Epic("epic1", "description-epic-1"));
		tasks.add(new Epic("epic2", "description-epic-2"));
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		TaskManager manager = new TaskManager(tasks);

		List<TaskInterface> myTasks = manager.getAllTasks();
		// myTasks.forEach(v -> System.out.println(v));

		System.out.println("_".repeat(100));

		assertEquals(COUNT_TASKS, myTasks.size());
	}

	@Test
	public void testAddTask() {
		System.out.println("testAddTask");

		List<TaskInterface> tasks = new ArrayList<>();
		tasks.add(new Task("task1", "description-task-1"));
		tasks.add(new Task("task2", "description-task-2"));
		tasks.add(new Epic("epic1", "description-epic-1"));
		tasks.add(new Epic("epic2", "description-epic-2"));
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		TaskManager manager = new TaskManager(tasks);

		TaskInterface task = new Task("ADD TASK", "ADD TASK");
		manager.addTask(task);
		List<TaskInterface> actual = manager.getAllTasks();
		// actual.forEach(v -> System.out.println(v));
		System.out.println("_".repeat(100));
		assertEquals(actual.size(), COUNT_TASKS + 1);
	}

	@Test
	public void testRemoveTask() {
		System.out.println("testRemoveTask");

		List<TaskInterface> tasks = new ArrayList<>();
		tasks.add(new Task("task1", "description-task-1"));
		tasks.add(new Task("task2", "description-task-2"));
		tasks.add(new Epic("epic1", "description-epic-1"));
		tasks.add(new Epic("epic2", "description-epic-2"));
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		TaskManager manager = new TaskManager(tasks);

		Set<TaskInterface> actual = manager.getAllSetTasks();
		// actual.forEach(v -> System.out.println(v));
		System.out.println("-*-".repeat(100));
		manager.removeTaskById("Task-31");
		actual = manager.getAllSetTasks();
		// actual.forEach(v -> System.out.println(v));
		System.out.println("_".repeat(100));
		assertEquals(COUNT_TASKS - 1, actual.size());
	}

	@Test
	public void testGetTaskById() {
		System.out.println("testGetTaskById");

		List<TaskInterface> tasks = new ArrayList<>();
		tasks.add(new Task("task1", "description-task-1"));
		tasks.add(new Task("task2", "description-task-2"));
		tasks.add(new Epic("epic1", "description-epic-1"));
		tasks.add(new Epic("epic2", "description-epic-2"));
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		TaskManager manager = new TaskManager(tasks);

		String id = "Epic-9";
		TaskInterface epic = manager.getTaskById(id);
		System.out.println(epic);
		System.out.println("_".repeat(100));
		assertNotEquals(epic, null);
	}

	@Test
	public void testUpdateTask() {
		System.out.println("testUpdateTask");

		List<TaskInterface> tasks = new ArrayList<>();
		tasks.add(new Task("task1", "description-task-1"));
		tasks.add(new Task("task2", "description-task-2"));
		tasks.add(new Epic("epic1", "description-epic-1"));
		tasks.add(new Epic("epic2", "description-epic-2"));
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		TaskManager manager = new TaskManager(tasks);

		String id = "Task-11";
		String newName = "UPDATE";
		String newDescription = "UPDATE";

		manager.updateTask(id, newName, newDescription);
		// Set<TaskInterface> myTasks = manager.getAllSetTasks();
		// myTasks.forEach(v -> System.out.println(v));

		TaskInterface updateTask = manager.getTaskById(id);
		assertEquals("UPDATE", updateTask.getName());
		assertEquals("UPDATE", updateTask.getDescription());
		System.out.println("_".repeat(100));

	}

	@Test
	public void testGetSubtasks() { // в этом методе демонстрируется принцип взаимодействия задач и подзадач
		// их регистрации и получения

		System.out.println("*".repeat(100));
		System.out.println("testGetSubtasks");

		TaskInterface epic1 = new Epic("epic1", "description-epic-1");
		TaskInterface t1 = new Task("task1", "description task 1");
		TaskInterface t2 = new Task("task2", "description task 2");
		TaskInterface sub1 = new Subtask("subtask1", "description subtask1");
		TaskInterface sub2 = new Subtask("subtask2", "description subtask2");
		TaskInterface sub3 = new Subtask("subtask3", "desc - subtask3");

		TaskManager manager = new TaskManager();

		// структурирую задачи по иерархии
		manager.addTask(epic1, t1);
		manager.addTask(epic1, t1);
		manager.addTask(epic1, t2);
		manager.addTask(t1, sub1);
		manager.addTask(t1, sub2);
		manager.addTask(t2, sub3);

		List<TaskInterface> tasks = manager.getSubtasks(t1.getId());
		assertEquals(sub1, tasks.get(0));
		assertEquals(sub2, tasks.get(1));
		assertEquals(2, tasks.size());

		tasks = manager.getSubtasks(t2.getId());
		assertEquals(sub3, tasks.get(0));
		assertEquals(1, tasks.size());

		tasks = manager.getSubtasks(epic1.getId());
		assertEquals(t2, tasks.get(0));
		assertEquals(t1, tasks.get(1));
		assertEquals(sub3, tasks.get(2));
		assertEquals(sub1, tasks.get(3));
		assertEquals(sub2, tasks.get(4));
		assertEquals(5, tasks.size());
		System.out.println("*".repeat(100));
	}

	@Test
	public void testChangeStatusTask() {
		System.out.println("|".repeat(100));
		System.out.println("testChangeStatusTask");

		TaskInterface task1 = new Task("task1-CHANGE-STATUS", "description-task-1");
		TaskInterface epic1 = new Epic("epic1-CHANGE-STATUS", "description-epic-1");
		TaskInterface subtask1 = new Subtask("CHANGE-STATUS", "-CHANGE-STATUS");
		TaskInterface t1 = new Task("task-epic1", ".......");
		TaskInterface t2 = new Task("task-epic1", "*********");
		TaskInterface sub1 = new Subtask("subTak", "description - for - change status");
		TaskInterface sub2 = new Subtask("subtask", "description subtask");

		TaskManager manager = new TaskManager();

		// структурирую задачи по иерархии
		manager.addTask(epic1, task1);
		manager.addTask(epic1, t1);
		manager.addTask(epic1, t1);
		manager.addTask(epic1, t2);
		manager.addTask(task1, sub1);
		manager.addTask(task1, sub2);
		manager.addTask(task1, subtask1);
		manager.addTask(task1, sub2);

		List<TaskInterface> expected = manager.getAllTasks();

		List<TaskInterface> actual = manager.getSubtasks(epic1.getId());
		actual.forEach(v -> System.out.println(v));

		assertEquals(expected.size() - 1, actual.size());
		System.out.println("-*-".repeat(100));

		// изменяю подзадачи - принадлежащие к задаче 1 (task1)
		manager.changeStatusTask(subtask1.getId());
		manager.changeStatusTask(sub1.getId());
		manager.changeStatusTask(sub2.getId());
		// только после изменения статуса подзадач можно изменить задачу 1 (task1)
		manager.changeStatusTask(task1.getId());

		actual = manager.getSubtasks(task1.getId());
		actual.forEach(v -> System.out.println(v));
		System.out.println("|".repeat(100));
		Status actualStatus = manager.getTaskById(task1.getId()).getStatus();
		Status changeStatusTask = task1.getStatus();
		assertEquals(Status.IN_PROGRESS, actualStatus);
		assertEquals(Status.IN_PROGRESS, changeStatusTask);

		manager.changeStatusTask(task1.getId()); // пытаюсь изменить статус не меняя статус подзадач
		actualStatus = manager.getTaskById(task1.getId()).getStatus();
		assertEquals(Status.IN_PROGRESS, actualStatus);

		// изменяю статус подзадач на выполненно и саму задачу
		manager.changeStatusTask(subtask1.getId());
		manager.changeStatusTask(sub1.getId());
		manager.changeStatusTask(sub2.getId());
		manager.changeStatusTask(task1.getId());
		assertEquals(Status.DONE, subtask1.getStatus());
		assertEquals(Status.DONE, sub1.getStatus());
		assertEquals(Status.DONE, sub2.getStatus());
		assertEquals(Status.DONE, task1.getStatus());
	}

	@Test
	public void testGraphSearchDFS_BFS() {
		Graph<String> adjacent = new Graph<String>();
		adjacent.addEdge("A", "A-11");
		adjacent.addEdge("A", "A-12");
		adjacent.addEdge("A", "A-13");

		adjacent.addEdge("A-11", "A-21");
		adjacent.addEdge("A-12", "A-22");
		adjacent.addEdge("A-13", "A-23");

		adjacent.addEdge("A-21", "A-31");
		adjacent.addEdge("A-22", "A-32");
		adjacent.addEdge("A-23", "A-33");

		adjacent.addEdge("A-31", "A-41");
		adjacent.addEdge("A-32", "A-42");
		adjacent.addEdge("A-33", "A-43");

		Set<String> setDFS = adjacent.DFS("A");
		// тут можно поменять значения чтобы посмотреть как функционирует обход в
		// глубину
		setDFS.forEach(v -> System.out.print(v + " | "));
		System.out.println();
		System.out.println("-".repeat(100));

		List<String> listBFS = adjacent.BFS("A");
		// тут можно поменять значения чтобы посмотреть как функционирует обход в ширину
		listBFS.forEach(v -> System.out.print(v + " | "));
		System.out.println();
		System.out.println("-".repeat(100));
		assertEquals(listBFS.size(), setDFS.size());
	}

	@Test
	public void testGenerateIdForTask() {
		TaskInterface task1 = new Task();
		TaskInterface task2 = new Task();
		TaskInterface task3 = new Task();

		System.out.println(task1.getId());
		System.out.println(task2.getId());
		System.out.println(task3.getId());

		task1.setName("task");
		task1.setDescription("task");
		task2.setName("task");
		task2.setDescription("task");

		assertNotEquals(task1, task2);
	}

	@Test
	public void testGenerateIdForSubtask() {
		TaskInterface subtask1 = new Subtask("Sub", "Sub");
		TaskInterface subtask2 = new Subtask("Sub", "Sub");
		TaskInterface subtask3 = new Subtask("Sub", "Sub");

		System.out.println(subtask1.getId());
		System.out.println(subtask2.getId());
		System.out.println(subtask3.getId());

		assertNotEquals(subtask1, subtask2);
	}

	@Test
	public void testGenerateIdForEpic() {
		TaskInterface epic1 = new Epic("Epic", "Epic");
		TaskInterface epic2 = new Epic("Epic", "Epic");
		TaskInterface epic3 = new Epic("Epic", "Epic");

		System.out.println(epic1.getId());
		System.out.println(epic2.getId());
		System.out.println(epic3.getId());

		assertNotEquals(epic1, epic3);
		assertNotEquals(epic2, epic3);
		assertNotEquals(epic2, epic1);
	}

	@Test
	public void testGraphForTask() {
		Graph<TaskInterface> manager = new Graph<>();
		Epic epic1 = new Epic("Глобальная задача", "описание глобальной задачи");
		Epic epic2 = new Epic("Global TASK", "description - for GLOBAL TASK");
		Task task11 = new Task("Задача - 1", "Описание задачи - 1");
		Task task12 = new Task("Задача - 2", "Описание задачи - 2");
		Task task21 = new Task("Task - 1", "Description for task - 1");
		Task task22 = new Task("Task - 2", "Description for task - 2");
		Subtask sub1 = new Subtask("Sub1", "Desc-1");
		Subtask sub2 = new Subtask("Sub1", "Desc-1");
		Subtask sub3 = new Subtask("Sub1", "Desc-1");
		Subtask sub4 = new Subtask("Sub1", "Desc-1");
		Subtask sub5 = new Subtask("Sub1", "Desc-1");
		Subtask sub6 = new Subtask("Sub1", "Desc-1");
		Subtask sub7 = new Subtask("Sub1", "Desc-1");
		Subtask sub8 = new Subtask("Sub1", "Desc-1");
		Subtask sub9 = new Subtask("Sub1", "Desc-1");
		Subtask sub10 = new Subtask("Sub1", "Desc-1");

		manager.addEdge(epic1, task11);
		manager.addEdge(epic1, task12);

		manager.addEdge(epic2, task21);
		manager.addEdge(epic2, task22);

		manager.addEdge(task11, sub1);
		manager.addEdge(task11, sub2);
		manager.addEdge(task11, sub3);

		manager.addEdge(task12, sub4);
		manager.addEdge(task12, sub5);

		manager.addEdge(task21, sub6);
		manager.addEdge(task21, sub7);

		manager.addEdge(task22, sub8);
		manager.addEdge(task22, sub9);
		manager.addEdge(task22, sub10);

		System.out.println("*".repeat(100));
		List<TaskInterface> actualBFS = manager.BFS(epic1);
		actualBFS.forEach(v -> System.out.println(v));
		System.out.println();
		Set<TaskInterface> actualDFS = manager.DFS(epic1);
		actualDFS.forEach(v -> System.out.println(v));
		assertEquals(actualBFS.size(), actualDFS.size());

		System.out.println("|".repeat(100));

		actualBFS = manager.BFS(epic2);
		actualBFS.forEach(v -> System.out.println(v));
		System.out.println();
		actualDFS = manager.DFS(epic2);
		actualDFS.forEach(v -> System.out.println(v));
	}
}
