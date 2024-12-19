package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;

public class FileBackedTaskManagerTest {

	private Set<TaskInterface> expectedSet;

	private FileBackedTaskManager manager;

	private TaskInterface root;

	private TaskInterface parentA;
	private TaskInterface parentB;
	private TaskInterface childrenA;
	private TaskInterface childrenA1;
	private TaskInterface childrenA2;
	private TaskInterface childrenB;

	private final String mainFile = "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/Save.json";
	private final String historyFile = "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/History.json";

	@BeforeEach
	public void setUp() {
		expectedSet = new HashSet<>();
		manager = new FileBackedTaskManager(mainFile, historyFile);

		root = new Epic("ROOT", "description ROOT");

		parentA = new Epic("Parent A", "description parent A");
		childrenA = new Epic("Children A", "description children A");
		childrenA1 = new Task("Children A1", "description children A1");
		childrenA2 = new Task("Children A2", "description children A2");

		parentB = new Task("Parent B", "description parent B");
		childrenB = new Subtask("Children B", "description children B");
	}

	@Test
	public void addTaskWhenAddNewTaskInTaskManagerThenReturnNewSize() {
		int expectedSize = manager.size();

		assertTrue(manager.addTask(root));
		expectedSize += 1;

		assertEquals(expectedSize, manager.size());
	}

	@Test
	public void addTaskWhenAddTopAndSubtaskThenReturnNewSize() {
		int expectedSize = manager.size();

		manager.addTask(root, parentA);
		expectedSize += 2;

		assertEquals(expectedSize, manager.size());
	}

	@Test
	public void removeTasksWhenAdd2TopTasksAnd2SubtasksThenReturnEmptyTaskManager() {
		manager.addTask(root, parentB);
		manager.addTask(root, parentA);

		manager.removeTasks();

		assertTrue(manager.isEmpty());
	}
}
