package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Status;

public class InMemoryTaskManagerTest {

	private Set<TaskInterface> expectedSet;

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
		expectedSet = new HashSet<>();

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
		taskManager.addTask(childrenA, childrenA1);
		taskManager.addTask(childrenA, childrenA2);
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
		taskManager.addTask(childrenA, childrenA1);
		taskManager.addTask(childrenA, childrenA2);
		taskManager.addTask(parentB, childrenB);

		TaskInterface currentTask = childrenA1;
		String actualId = currentTask.getId();
		taskManager.removeTaskById(actualId);

		assertFalse(taskManager.containsTaskById(actualId));
	}

	@Test
	public void getSetSubtasks_whenAddAllTasks_ReturnSubtasks() {
		taskManager.addTask(root, parentA);
		taskManager.addTask(root, parentB);
		taskManager.addTask(parentA, childrenA);
		expectedSet.add(childrenA);
		taskManager.addTask(childrenA, childrenA1);
		expectedSet.add(childrenA1);
		taskManager.addTask(childrenA, childrenA2);
		expectedSet.add(childrenA2);
		taskManager.addTask(parentB, childrenB);

		TaskInterface topTask = parentA;
		Set<TaskInterface> actual = taskManager.getSetSubtasks(topTask.getId());

		actual.forEach(v -> assertTrue(expectedSet.contains(v)));
		assertEquals(expectedSet.size(), actual.size());
	}

	@Test
	public void getSubtasks_whenAddAllTasks_ReturnSubtasks() {
		taskManager.addTask(root, parentA);
		taskManager.addTask(root, parentB);
		taskManager.addTask(parentA, childrenA);
		expectedSet.add(childrenA);
		taskManager.addTask(childrenA, childrenA1);
		expectedSet.add(childrenA1);
		taskManager.addTask(childrenA, childrenA2);
		expectedSet.add(childrenA2);
		taskManager.addTask(parentB, childrenB);

		TaskInterface topTask = parentA;
		List<TaskInterface> actual = taskManager.getSubtasks(topTask.getId());

		actual.forEach(v -> assertTrue(expectedSet.contains(v)));
		assertEquals(expectedSet.size(), actual.size());
	}

	@Test
	public void updateTask_whenUpdateNameAndDescription_ReturnUpdateTask() {
		taskManager.addTask(root);
		String id = root.getId();
		String newName = "new name = ROOT or super task";
		String newDescription = "new Description!!!";

		taskManager.updateTask(id, newName, newDescription);
		TaskInterface actual = taskManager.getTaskById(id);

		assertEquals(newName, actual.getName());
		assertEquals(newDescription, actual.getDescription());
	}

	@Test
	public void updateTask_whenUpdateTAsk_ReturnUpdateTask() {
		taskManager.addTask(root);
		String id = root.getId();
		String newName = "new name = ROOT or super task";
		String newDescription = "new Description!!!";
		root.setName(newName);
		root.setDescription(newDescription);

		taskManager.updateTask(root);
		TaskInterface actual = taskManager.getTaskById(id);

		assertEquals(root, actual);
	}

	@Test
	public void changeStatusTask_whenStatusNotChange_ReturnStatusNEW() {
		taskManager.addTask(root, childrenA);
		String id = root.getId();

		taskManager.changeStatusTask(id);
		root = taskManager.getTaskById(id);

		assertEquals(Status.NEW, root.getStatus());
	}

	@Test
	public void changeStatusTask_whenChangedStatus_ReturnStatusIN_PROGRESS() {
		taskManager.addTask(root, parentA);
		String idTop = root.getId();
		String idSub = parentA.getId();

		taskManager.changeStatusTask(idSub);
		taskManager.changeStatusTask(idTop);
		root = taskManager.getTaskById(idTop);

		assertEquals(Status.IN_PROGRESS, root.getStatus());
	}

	@Test
	public void getHistory_whenNotGetTask_ReturnEmpryList() {
		List<TaskInterface> actual = taskManager.getHistory();

		assertTrue(actual.isEmpty());
	}

	@Test
	public void getHistory_whenGetTask_ReturnListHistory() {
		expectedSet.add(root);
		expectedSet.add(parentA);
		expectedSet.add(parentB);
		taskManager.addTask(root, parentA);
		taskManager.addTask(root, parentB);

		taskManager.getTaskById(root.getId());
		taskManager.getTaskById(parentA.getId());
		taskManager.getTaskById(parentB.getId());
		taskManager.getTaskById(root.getId());
		List<TaskInterface> actual = taskManager.getHistory();

		actual.forEach(v -> assertTrue(expectedSet.contains(v)));
		assertEquals(expectedSet.size(), actual.size());
	}

}
