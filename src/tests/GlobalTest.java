package tests;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import kanban.model.TaskInterface;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Graph;
import kanban.util.Status;

public class GlobalTest {

	@Test
	public void testDFS_BFS() {
		Graph<String> adjacent = new Graph<String>();
		adjacent.addEdge("A", "A-11", false);
		adjacent.addEdge("A", "A-12", false);
		adjacent.addEdge("A", "A-13", false);

		adjacent.addEdge("A-11", "A-21", false);
		adjacent.addEdge("A-12", "A-22", false);
		adjacent.addEdge("A-13", "A-23", false);

		adjacent.addEdge("A-21", "A-31", false);
		adjacent.addEdge("A-22", "A-32", false);
		adjacent.addEdge("A-23", "A-33", false);

		adjacent.addEdge("A-31", "A-41", false);
		adjacent.addEdge("A-32", "A-42", false);
		adjacent.addEdge("A-33", "A-43", false);

		Set<String> setDFS = adjacent.DFS("A");
		setDFS.forEach(v -> System.out.print(v + " | "));
		System.out.println();
		System.out.println("-".repeat(100));

		List<String> listBFS = adjacent.BFS("A");
		listBFS.forEach(v -> System.out.print(v + " | "));
		System.out.println();
		System.out.println("-".repeat(100));
		assertEquals(listBFS.size(), setDFS.size());
	}

	@Test
	public void testGenerateIdForTask() {
		TaskInterface task1 = new Task();
		TaskInterface task2 = new Task();
		TaskInterface task3 = new Task();

		assertEquals(task1.getId(), "Task-1");
		assertEquals(task2.getId(), "Task-2");
		assertEquals(task3.getId(), "Task-3");

		task1.setName("task");
		task1.setDescription("task");
		task2.setName("task");
		task2.setDescription("task");

		assertNotEquals(task1, task2);
		assertEquals(task1.getStatus(), Status.NEW);
		assertEquals(task2.getStatus(), Status.NEW);
		assertEquals(task3.getStatus(), Status.NEW);
	}

	@Test
	public void testGenerateIdForSubtask() {
		TaskInterface subtask1 = new Subtask();
		TaskInterface subtask2 = new Subtask();
		TaskInterface subtask3 = new Subtask();

		assertEquals(subtask1.getId(), "Subtask-1");
		assertEquals(subtask2.getId(), "Subtask-2");
		assertEquals(subtask3.getId(), "Subtask-3");

		assertNotEquals(subtask1, subtask2);
		assertEquals(subtask1.getStatus(), Status.NEW);
	}

	@Test
	public void testGenerateIdForEpic() {

	}

	@Test
	public void testChangeStatusForTask() {

	}

	@Test
	public void testChangeStatusForEpic() {

	}

	/*
	 * 
	 * c. Получение по идентификатору. d. Создание. Сам объект должен передаваться в
	 * качестве параметра. e. Обновление. Новая версия объекта с верным
	 * идентификатором передаётся в виде параметра. f. Удаление по идентификатору.
	 * Дополнительные методы: a. Получение списка всех подзадач определённого эпика.
	 */
}
