package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Status;

public class InMemoryTaskManagerTest {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

	private Set<TaskInterface> expectedSet;

	private InMemoryTaskManager manager;

	private TaskInterface root;
	private TaskInterface parentA;
	private TaskInterface parentB;
	private TaskInterface childrenA;
	private TaskInterface childrenA1;
	private TaskInterface childrenA2;
	private TaskInterface childrenB;

	private TaskInterface root2;
	private TaskInterface parentB2;
	private TaskInterface childrenB2;

	@BeforeEach
	public void setUp() {
		// инициализирую объекты без полей даты
		root = new Epic("ROOT", "description ROOT");
		parentA = new Epic("Parent A", "description parent A");
		childrenA = new Task("Children A", "description children A");
		childrenA1 = new Subtask("Children A1", "description children A1");
		childrenA2 = new Subtask("Children A2", "description children A2");
		parentB = new Task("Parent B", "description parent B");
		childrenB = new Subtask("Children B", "description children B");
		root2 = new Epic("ROOT2", "Description for ROOT-2");

		// инициализирую объекты с полями даты
		parentB2 = new Task("Parent B-2", "description parent B-2");
		parentB2.setStartTime(LocalDateTime.parse("10:00 20.12.2024", DATE_TIME_FORMATTER));
		parentB2.setDuration(Duration.ofMinutes(60));
		childrenB2 = new Task("children B-2", "description parent B-2");
		childrenB2.setStartTime(LocalDateTime.parse("15:00 22.12.2024", DATE_TIME_FORMATTER));
		childrenB2.setDuration(Duration.ofMinutes(120));

		expectedSet = new HashSet<>();
		manager = new InMemoryTaskManager();

		// вношу данные для тестов
		// наполняю данными структуры класса InMemoryTaskManager без дат
		assertTrue(manager.addTask(root, parentA));
		assertTrue(manager.addTask(root, parentB));
		assertTrue(manager.addTask(parentA, childrenA));
		assertTrue(manager.addTask(childrenA, childrenA1));
		assertTrue(manager.addTask(childrenA, childrenA2));
		assertTrue(manager.addTask(parentB, childrenB));

		// наполняю данными структуры класса InMemoryTaskManager с датами
		assertTrue(manager.addTask(root2, parentB2));
		assertTrue(manager.addTask(parentB2, childrenB2));

		// ожидаемый набор подзадач для parentA
		expectedSet.add(childrenA);
		expectedSet.add(childrenA1);
		expectedSet.add(childrenA2);
	}

	@Test
	public void addTaskWhenAddNewTaskInTaskManagerThenReturnNewSize() {
		int expectedSize = manager.size();
		TaskInterface newTask = new Task("newTask", "descritptionNewTask");

		assertTrue(manager.addTask(newTask));
		expectedSize += 1;

		assertEquals(expectedSize, manager.size());
	}

	@Test
	public void addTaskWhenAddTopAndSubtaskThenReturnNewSize() {
		int expectedSize = manager.size();
		TaskInterface newRoot = new Epic("newROOT", "new ROOT Description");
		TaskInterface newParent = new Task("newParent", "new parent description");

		assertTrue(manager.addTask(newRoot, newParent));
		expectedSize += 2;

		assertEquals(expectedSize, manager.size());
	}

	@Test
	public void containsTaskByIdWhenAddNewTaskThenReturnTrue() {
		TaskInterface containsTask = new Subtask();

		assertTrue(manager.addTask(containsTask));

		assertTrue(manager.containsTaskById(containsTask.getId()));
	}

	@Test
	public void removeTasksWhenAdd2TopTasksAnd2SubtasksThenReturnEmptyTaskManager() {
		manager.removeTasks();

		assertTrue(manager.isEmpty());
	}

	@Test
	public void getTaskByIdWhenAddAllTasksThenReturnFindTask() {
		TaskInterface expectedTask = parentB;

		assertEquals(expectedTask, manager.getTaskById(parentB.getId()));
	}

	@Test
	public void removeTaskByIdWhenAddAllTasksThenReturnFalseCheckIsContainsCurrentTask() {
		manager.removeTaskById(childrenA1.getId());

		assertFalse(manager.containsTaskById(childrenA1.getId()));
	}

	@Test
	public void getSetSubtasksWhenAddAllTasksThenReturnSubtasks() {
		Set<TaskInterface> actual = manager.getSetSubtasks(parentA.getId());

		actual.forEach(v -> assertTrue(expectedSet.contains(v)));
		assertEquals(expectedSet.size(), actual.size());
	}

	@Test
	public void getSubtasksWhenAddAllTasksThenReturnSubtasks() {
		List<TaskInterface> actual = manager.getSubtasks(parentA.getId());

		actual.forEach(v -> assertTrue(expectedSet.contains(v)));
		assertEquals(expectedSet.size(), actual.size());
	}

	@Test
	public void updateTaskWhenUpdateNameAndDescriptionThenUpdatePrioritizedGraphFactory() {
		// обновляю данные для теста
		childrenB2.setName("NewName - ChildrenB");
		childrenB2.setDescription("NewDescription - cheldrenB");
		childrenB2.setStartTime(LocalDateTime.parse("12:00 01.01.2025", DATE_TIME_FORMATTER));
		childrenB2.setDuration(Duration.ofMinutes(20));

		parentB2.setName("newParentB2");
		parentB2.setDescription("------New----Description-------newParentB2");
		parentB2.setStartTime(LocalDateTime.parse("13:00 01.01.2025", DATE_TIME_FORMATTER));
		parentB2.setDuration(Duration.ofMinutes(30));

		root2 = new Epic(manager.getGraph(), (Epic) root2);
		// тестирую расчет времени выполнения для эпика
		int expectedDurationEpic = 50;
		assertEquals(expectedDurationEpic, root2.getDuration().toMinutes());
		// проверяю поле startTime
		LocalDateTime expectedStartEpicTime = LocalDateTime.parse("12:00 01.01.2025", DATE_TIME_FORMATTER);
		assertEquals(expectedStartEpicTime, root2.getStartTime());
		// проверяю поле endTime
		LocalDateTime expectedEndEpicTime = LocalDateTime.parse("13:30 01.01.2025", DATE_TIME_FORMATTER);
		assertEquals(expectedEndEpicTime, root2.getEndTime());

		// вызываю метод обновления для подготовленных данных
		assertTrue(manager.updateTask(childrenB2));
		assertTrue(manager.updateTask(parentB2));
		assertTrue(manager.updateTask(root2));

		// проверяю корректность метода для Factory
		TaskInterface factoryActualChildrenB2 = manager.getFactory().getTaskById(childrenB2.getId());
		assertEquals(childrenB2, factoryActualChildrenB2);
		assertEquals(childrenB2.getName(), factoryActualChildrenB2.getName());
		assertEquals(childrenB2.getDescription(), factoryActualChildrenB2.getDescription());
		assertEquals(childrenB2.getStatus(), factoryActualChildrenB2.getStatus());
		assertEquals(childrenB2.getStartTime(), factoryActualChildrenB2.getStartTime());
		assertEquals(childrenB2.getDuration(), factoryActualChildrenB2.getDuration());
		assertEquals(childrenB2.getEndTime(), factoryActualChildrenB2.getEndTime());

		// проверяю корректность метода для PrioritizedTasks
		assertTrue(manager.containsPrioritizedTasks(childrenB2));
		assertTrue(manager.containsPrioritizedTasks(parentB2));
		assertTrue(manager.containsPrioritizedTasks(root2));

		// проверяю корректность метода для Graph
		assertTrue(manager.getGraph().getAdjacencyList().containsKey(parentB2));
		Map<TaskInterface, Set<TaskInterface>> adjacent = manager.getGraph().getAdjacencyList();
		adjacent.forEach((k, v) -> {
			if (k.getId() == parentB2.getId()) {
				assertEquals(parentB2.getName(), k.getName());
				assertEquals(parentB2.getDescription(), k.getDescription());
				assertEquals(parentB2.getStatus(), k.getStatus());
				assertEquals(parentB2.getStartTime(), k.getStartTime());
				assertEquals(parentB2.getDuration(), k.getDuration());
				assertEquals(parentB2.getEndTime(), k.getEndTime());
			}
		});
	}

	@Test
	public void updateTaskWhenUpdateTaskThenUpdateFactory() {
		manager.addTask(root);
		String id = root.getId();
		String newName = "new name = ROOT or super task";
		String newDescription = "new Description!!!";
		root.setName(newName);
		root.setDescription(newDescription);

		manager.updateTask(root);
		TaskInterface actual = manager.getTaskById(id);

		assertEquals(root, actual);
	}

	@Test
	public void changeStatusTaskWhenStatusNotChangeThenReturnStatusNew() {
		manager.changeStatusTask(root.getId());

		assertEquals(Status.NEW, manager.getTaskById(root.getId()).getStatus());
	}

	@Test
	public void changeStatusTaskWhenChangedStatusThenReturnStatusInProgress() {
		assertTrue(manager.changeStatusTask(childrenA2.getId()));
		assertTrue(manager.changeStatusTask(childrenA1.getId()));
		assertTrue(manager.changeStatusTask(childrenA.getId()));
		assertTrue(manager.changeStatusTask(parentA.getId()));

		assertEquals(Status.IN_PROGRESS, manager.getTaskById(parentA.getId()).getStatus());
	}

	@Test
	public void getHistoryWhenNotGetTaskThenReturnEmptyList() {
		List<TaskInterface> actual = manager.getHistory();

		assertTrue(actual.isEmpty());
	}

	@Test
	public void getHistoryWhenGetTaskThenReturnListHistory() {
		manager.getTaskById(childrenA.getId());
		manager.getTaskById(childrenA1.getId());
		manager.getTaskById(childrenA2.getId());

		List<TaskInterface> actual = manager.getHistory();

		actual.forEach(v -> assertTrue(expectedSet.contains(v)));
		assertEquals(expectedSet.size(), actual.size());
	}

}
