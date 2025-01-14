package test.kanban.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.kanban.model.TaskInterface;
import main.kanban.model.impl.Epic;
import main.kanban.model.impl.Subtask;
import main.kanban.model.impl.Task;
import main.kanban.util.CustomList;

public class CustomListTest {

	private CustomList actual;
	private Epic epic;
	private Task task;
	private Subtask subtask;

	@BeforeEach
	public void setUp() {
		actual = new CustomList();
		epic = new Epic();
		task = new Task();
		subtask = new Subtask();

		actual.add(epic);
		actual.add(task);
		actual.add(subtask);
	}

	@Test
	public void add3TaskWhenEmptyHistoryThenReturnSize3() {

		int expectedSizeMap = 3;

		assertEquals(expectedSizeMap, actual.size());
	}

	@Test
	public void getQueueWhenAddUnique3ElementsThenReturnListSize3() {

		int expectedSizeList = 3;
		List<TaskInterface> actualList = actual.getQueue();

		assertEquals(expectedSizeList, actualList.size());
	}

	@Test
	public void getQueueWhenAddNotUniqueElementsThenReturnListSize3() {
		actual.add(epic);

		int expectedSizeList = 3;
		List<TaskInterface> actualList = actual.getQueue();
		List<TaskInterface> expectedList = new ArrayList<>();
		expectedList.add(task);
		expectedList.add(subtask);
		expectedList.add(epic);

		int idx = 0;
		assertEquals(expectedSizeList, actualList.size());
		for (TaskInterface t1 : actualList) {
			assertEquals(t1, expectedList.get(idx++));
		}
	}

	public void removeWhenAdd3TasksRemove1TaskThenReturnSize2() {
		String id = task.getId();

		actual.remove(id);
		int expectedSize = 2;

		assertEquals(expectedSize, actual.size());
	}

}
