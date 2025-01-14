package test.kanban.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.kanban.model.TaskInterface;
import main.kanban.model.impl.Task;
import main.kanban.service.TaskFactory;

public class TaskFactoryTest {

	private TaskFactory factory;
	private TaskInterface task;

	@BeforeEach
	public void setUp() {
		factory = new TaskFactory();
		task = new Task("name for TASK", "description for TASK");
	}

	@Test
	public void registerWhenTaskFactoryInitThenReturnSize1() {
		assertTrue(factory.isEmpty());
		task.registerMyself(factory);

		assertEquals(1, factory.size());
	}

	@Test
	public void containsTaskWhenTaskFactoryNotEmptyThenReturnTrue() {
		task.registerMyself(factory);

		assertTrue(factory.containsTask(task.getId()));
	}

	@Test
	public void removeTasksWhenRemoveAllTasksThenReturnTrue() {
		task.registerMyself(factory);

		factory.removeTasks();

		assertTrue(factory.isEmpty());
	}

	@Test
	public void getTaskByIdWhenAddTaskInTaskFactoryThenReturnTask() {
		task.registerMyself(factory);

		TaskInterface actual = factory.getTaskById(task.getId());

		assertEquals(task, actual);
	}

	@Test
	public void removeTaskByIdWhenTaskFactoryNotEmptyThenReturnFactory() {
		task.registerMyself(factory);
		assertFalse(factory.isEmpty());

		TaskInterface actual = factory.getTaskById(task.getId());

		assertEquals(task, actual);
	}
}
