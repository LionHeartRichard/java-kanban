package tests;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Graph;

public class GlobalTest {

	@Test
	public void testGraphSearchDFS_BFS() {
		Graph<String> adjacent = new Graph<String>();
		adjacent.addEdge("A", "A-11");
		adjacent.addEdge("A", "A-12");
		adjacent.addEdge("A", "A-13");

		adjacent.addEdge("A-11", "A-21");
		adjacent.addEdge("A-12", "A-22");
		adjacent.addEdge("A-13", "A-23");

		adjacent.addEdge("A-21", "A-31");
		adjacent.addEdge("A-22", "A-32");
		adjacent.addEdge("A-23", "A-33");

		adjacent.addEdge("A-31", "A-41");
		adjacent.addEdge("A-32", "A-42");
		adjacent.addEdge("A-33", "A-43");

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

		System.out.println(task1.getId());
		System.out.println(task2.getId());
		System.out.println(task3.getId());

		task1.setName("task");
		task1.setDescription("task");
		task2.setName("task");
		task2.setDescription("task");

		assertNotEquals(task1, task2);
	}

	@Test
	public void testGenerateIdForSubtask() {
		TaskInterface subtask1 = new Subtask("Sub", "Sub");
		TaskInterface subtask2 = new Subtask("Sub", "Sub");
		TaskInterface subtask3 = new Subtask("Sub", "Sub");

		System.out.println(subtask1.getId());
		System.out.println(subtask2.getId());
		System.out.println(subtask3.getId());

		assertNotEquals(subtask1, subtask2);
	}

	@Test
	public void testGenerateIdForEpic() {
		TaskInterface epic1 = new Epic("Epic", "Epic");
		TaskInterface epic2 = new Epic("Epic", "Epic");
		TaskInterface epic3 = new Epic("Epic", "Epic");

		System.out.println(epic1.getId());
		System.out.println(epic2.getId());
		System.out.println(epic3.getId());

		assertNotEquals(epic1, epic3);
		assertNotEquals(epic2, epic3);
		assertNotEquals(epic2, epic1);
	}

	@Test
	public void testGraphForTask() {
		Graph<TaskInterface> manager = new Graph<>();
		Epic epic1 = new Epic("Глобальная задача", "описание глобальной задачи");
		Epic epic2 = new Epic("Global TASK", "description - for GLOBAL TASK");
		Task task11 = new Task("Задача - 1", "Описание задачи - 1");
		Task task12 = new Task("Задача - 2", "Описание задачи - 2");
		Task task21 = new Task("Task - 1", "Description for task - 1");
		Task task22 = new Task("Task - 2", "Description for task - 2");
		Subtask sub1 = new Subtask("Sub1", "Desc-1");
		Subtask sub2 = new Subtask("Sub1", "Desc-1");
		Subtask sub3 = new Subtask("Sub1", "Desc-1");
		Subtask sub4 = new Subtask("Sub1", "Desc-1");
		Subtask sub5 = new Subtask("Sub1", "Desc-1");
		Subtask sub6 = new Subtask("Sub1", "Desc-1");
		Subtask sub7 = new Subtask("Sub1", "Desc-1");
		Subtask sub8 = new Subtask("Sub1", "Desc-1");
		Subtask sub9 = new Subtask("Sub1", "Desc-1");
		Subtask sub10 = new Subtask("Sub1", "Desc-1");

		manager.addEdge(epic1, task11);
		manager.addEdge(epic1, task12);

		manager.addEdge(epic2, task21);
		manager.addEdge(epic2, task22);

		manager.addEdge(task11, sub1);
		manager.addEdge(task11, sub2);
		manager.addEdge(task11, sub3);

		manager.addEdge(task12, sub4);
		manager.addEdge(task12, sub5);

		manager.addEdge(task21, sub6);
		manager.addEdge(task21, sub7);

		manager.addEdge(task22, sub8);
		manager.addEdge(task22, sub9);
		manager.addEdge(task22, sub10);

		System.out.println("*".repeat(100));
		List<TaskInterface> actualBFS = manager.BFS(epic1);
		actualBFS.forEach(v -> System.out.println(v));
		System.out.println();
		Set<TaskInterface> actualDFS = manager.DFS(epic1);
		actualDFS.forEach(v -> System.out.println(v));
		assertEquals(actualBFS.size(), actualDFS.size());

		System.out.println("|".repeat(100));

		actualBFS = manager.BFS(epic2);
		actualBFS.forEach(v -> System.out.println(v));
		System.out.println();
		actualDFS = manager.DFS(epic2);
		actualDFS.forEach(v -> System.out.println(v));
	}

}
