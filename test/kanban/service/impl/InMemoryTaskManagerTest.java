package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.service.HistoryManager;
import kanban.service.Mangers;
import kanban.service.TaskFactory;
import kanban.util.Graph;
import kanban.util.Status;

public class InMemoryTaskManagerTest {

	private InMemoryTaskManager taskManager;

	private TaskInterface root;

	private TaskInterface parentA;
	private TaskInterface parentB;
	private TaskInterface childrenA;
	private TaskInterface childrenA1;
	private TaskInterface childrenA2;
	private TaskInterface childrenB;

	@BeforeEach
	public void setUp() {

		taskManager = new InMemoryTaskManager();

		root = new Epic("ROOT", "description ROOT");

		parentA = new Epic("Parent A", "description parent A");
		childrenA = new Epic("Children A", "description children A");
		childrenA1 = new Task("Children A1", "description children A1");
		childrenA2 = new Task("Children A2", "description children A2");

		parentB = new Task("Parent B", "description parent B");
		childrenB = new Subtask("Children B", "description children B");
	}

	@Test
	public void addTask_whenAddNewTaskInTaskManager_ReturnNewSize() {
		int expectedSize = taskManager.size();

		assertTrue(taskManager.addTask(childrenA));
		expectedSize += 1;

		assertEquals(expectedSize, taskManager.size());
	}

	@Test
	public void addTask_whenAddTopAndSubtask_ReturnNewSize() {
		int expectedSize = taskManager.size();

		taskManager.addTask(root, parentA);
		expectedSize += 2;

		assertEquals(expectedSize, taskManager.size());
	}

	@Test
	public void containsTaskById_whenAddNewTask_ReturnTrue() {
		String actualId = parentA.getId();

		taskManager.addTask(parentA);

		assertTrue(taskManager.containsTaskById(actualId));
	}

	@Test
	public void removeTasks_whenAdd2TopTasksAnd2Subtasks_ReturnEmptyTaskManager() {
		taskManager.addTask(root, parentB);
		taskManager.addTask(root, parentA);

		taskManager.removeTasks();

		assertTrue(taskManager.isEmpty());
	}

	@Test
	public void getTaskById_whenAddAllTasks_ReturnFindTask() {
		taskManager.addTask(root, parentA);
		taskManager.addTask(root, parentB);
		taskManager.addTask(parentA, childrenA);
		taskManager.addTask(parentA, childrenB);
		taskManager.addTask(parentB, childrenB);

		TaskInterface expectedTask = parentB;
		String actualId = parentB.getId();

		assertEquals(expectedTask, taskManager.getTaskById(actualId));
	}

	@Test
	public void removeTaskById_whenAddAllTasks_ReturnFalseCheckIsContainsCurrentTask() {
		taskManager.addTask(root, parentA);
		taskManager.addTask(root, parentB);
		taskManager.addTask(parentA, childrenA);
		taskManager.addTask(parentA, childrenB);
		taskManager.addTask(parentB, childrenB);

		TaskInterface currentTask = childrenA1;
		String actualId = currentTask.getId();
		taskManager.removeTaskById(actualId);

		assertFalse(taskManager.containsTaskById(actualId));
	}

	@Test
	public void getSetSubtasks() {

	}

	@Test
	public void getSubtasks() {

	}

	@Test
	public void updateTask(String id, String newName, String newDescription) {

	}

	@Test
	public void updateTask(TaskInterface task) {

	}

	@Test
	public void changeStatusTask() {

	}

	@Test
	public void getHistory() {
	}

}
