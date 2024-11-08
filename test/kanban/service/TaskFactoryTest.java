package kanban.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Task;

public class TaskFactoryTest {

	private TaskFactory factory;
	private TaskInterface task;

	@BeforeEach
	public void setUp() {
		factory = new TaskFactory();
		task = new Task("name for TASK", "description for TASK");
	}

	@Test
	public void register_whenTaskFactoryInit_ReturnSize1() {
		assertTrue(factory.isEmpty());
		task.registerMyself(factory);

		assertEquals(1, factory.size());
	}

	@Test
	public void containsTask_whenTaskFactoryNotEmpty_ReturnTrue() {
		task.registerMyself(factory);

		assertTrue(factory.containsTask(task.getId()));
	}

	@Test
	public void removeTasks_whenRemoveAllTasks_ReturnTrue() {
		task.registerMyself(factory);

		factory.removeTasks();

		assertTrue(factory.isEmpty());
	}

	@Test
	public void getTaskById_whenAddTaskInTaskFactory_ReturnTask() {
		task.registerMyself(factory);

		TaskInterface actual = factory.getTaskById(task.getId());

		assertEquals(task, actual);
	}

	@Test
	public void removeTaskById_whenTaskFActoryNotEmpty() {
		task.registerMyself(factory);
		assertFalse(factory.isEmpty());

		TaskInterface actual = factory.getTaskById(task.getId());

		assertEquals(task, actual);
	}
}
