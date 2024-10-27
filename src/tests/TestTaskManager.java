package tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.service.TaskFactory;
import kanban.service.TaskManager;
import kanban.util.Graph;
import kanban.util.Status;

public class TestTaskManager {

	private static final int COUNT_TASKS = 8;

	@Test
	public void testInitManagerTasks() {
		System.out.println("testInitManagerTasks");

		List<TaskInterface> tasks = new ArrayList<>();
		tasks.add(new Task("task1", "description-task-1"));
		tasks.add(new Task("task2", "description-task-2"));
		tasks.add(new Epic("epic1", "description-epic-1"));
		tasks.add(new Epic("epic2", "description-epic-2"));
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		tasks.add(new Subtask());
		TaskManager manager = new TaskManager();
		manager.initManagerTasks(tasks);

		List<TaskInterface> myTasks = manager.getAllTasks();
		myTasks.forEach(v -> System.out.println(v));

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
		TaskManager manager = new TaskManager();
		manager.initManagerTasks(tasks);

		TaskInterface task = new Task("ADD TASK", "ADD TASK");
		manager.addTask(task);
		List<TaskInterface> actual = manager.getAllTasks();
		actual.forEach(v -> System.out.println(v));
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
		TaskManager manager = new TaskManager();
		manager.initManagerTasks(tasks);

		Set<TaskInterface> actual = manager.getAllSetTasks();
		actual.forEach(v -> System.out.println(v));
		System.out.println("-*-".repeat(100));
		int expextedSize = actual.size() - 1;
		manager.removeTaskById("Epic-8");
		actual = manager.getAllSetTasks();
		actual.forEach(v -> System.out.println(v));
		System.out.println("_".repeat(100));
		assertEquals(expextedSize, actual.size());
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
		TaskManager manager = new TaskManager();
		manager.initManagerTasks(tasks);

		String id = "Epic-10";
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
		TaskManager manager = new TaskManager();
		manager.initManagerTasks(tasks);

		String id = "Task-11";
		String newName = "UPDATE";
		String newDescription = "UPDATE";

		manager.updateTask(id, newName, newDescription);
		Set<TaskInterface> myTasks = manager.getAllSetTasks();
		myTasks.forEach(v -> System.out.println(v));

		System.out.println("_".repeat(100));

	}

	@Test
	public void testChangeStatusSubtask() {
		System.out.println("|".repeat(100));
		System.out.println("testChangeStatusSubtask");

		TaskInterface task1 = new Task("task1-CHANGE-STATUS", "description-task-1");
		TaskInterface epic1 = new Epic("epic1-CHANGE-STATUS", "description-epic-1");
		TaskInterface subtask1 = new Subtask("CHANGE-STATUS", "-CHANGE-STATUS");
		TaskInterface t1 = new Task("task-epic1", ".......");
		TaskInterface t2 = new Task("task-epic1", "*********");
		TaskInterface sub1 = new Subtask("subTak", "description - for - change status");
		TaskInterface sub2 = new Subtask("subtask", "description subtask");
		List<TaskInterface> tasks = new ArrayList<>();
		tasks.add(task1);
		tasks.add(epic1);
		tasks.add(subtask1);
		tasks.add(sub1);
		tasks.add(sub2);
		tasks.add(task1);
		tasks.add(sub1);
		tasks.add(task1);
		tasks.add(sub1);
		tasks.add(t1);
		tasks.add(t2);
		TaskManager manager = new TaskManager();
		manager.initManagerTasks(tasks);

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

		assertEquals(expected.size(), actual.size());
		System.out.println("-*-".repeat(100));

		manager.changeStatusSubtask(subtask1.getId());
		manager.changeStatusSubtask(sub1.getId());
		manager.changeStatusSubtask(sub2.getId());

		manager.changeStatusSubtask(subtask1.getId());
		manager.changeStatusSubtask(sub1.getId());
		manager.changeStatusSubtask(sub2.getId());

		manager.changeStatusTask(task1.getId());
		manager.changeStatusTask(t1.getId());
		manager.changeStatusTask(t2.getId());

		manager.changeStatusTask(task1.getId());
		manager.changeStatusTask(t1.getId());
		manager.changeStatusTask(t2.getId());

		manager.changeStatusEpic(epic1.getId());

		manager.changeStatusEpic(epic1.getId());

		actual = manager.getAllTasks();
		actual.forEach(v -> System.out.println(v));
		System.out.println("|".repeat(100));
	}
}
