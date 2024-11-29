package kanban.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;

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
	}

	@Test
	public void add3TaskWhenEmptyHistoryThenReturnSize3() {
		actual.add(epic);
		actual.add(task);
		actual.add(subtask);

		int expectedSizeMap = 3;

		assertEquals(expectedSizeMap, actual.size());
	}

	@Test
	public void getQueueWhenAddUnique3ElementsThenReturnListSize3() {
		actual.add(epic);
		actual.add(task);
		actual.add(subtask);

		int expectedSizeList = 3;
		List<TaskInterface> actualList = actual.getQueue();

		assertEquals(expectedSizeList, actualList.size());
	}

//	public void remove(String id) {
//
//	}

}
