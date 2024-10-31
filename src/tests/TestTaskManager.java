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
import kanban.util.Status;

public class TestTaskManager {

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

		manager.addTask(epic1, t1);
		manager.addTask(epic1, t1);
		manager.addTask(epic1, t2);
		manager.addTask(t1, sub1);
		manager.addTask(t1, sub2);
		manager.addTask(t2, sub3);

		List<TaskInterface> tasks = manager.getSubtasks(t1.getId());
		assertEquals(2, tasks.size());

		tasks = manager.getSubtasks(t2.getId());
		assertEquals(1, tasks.size());

		tasks = manager.getSubtasks(epic1.getId());
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

		manager.changeStatusTask(subtask1.getId());
		manager.changeStatusTask(sub1.getId());
		manager.changeStatusTask(sub2.getId());

		manager.changeStatusTask(task1.getId());

		actual = manager.getSubtasks(task1.getId());
		actual.forEach(v -> System.out.println(v));
		System.out.println("|".repeat(100));
		Status actualStatus = manager.getTaskById(task1.getId()).getStatus();
		Status changeStatusTask = task1.getStatus();
		assertEquals(Status.IN_PROGRESS, actualStatus);
		assertEquals(Status.IN_PROGRESS, changeStatusTask);
	}
}
