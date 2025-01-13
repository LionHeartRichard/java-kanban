package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Graph;
import kanban.util.Status;

public class FileBackedTaskManagerTest {

	private FileBackedTaskManager actual;

	private String path = "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/case0.json";

	@BeforeEach
	public void setUpAndSaveDataContainsInObjectFileBackedTaskManager() throws IOException {

		actual = new FileBackedTaskManager(path);

		TaskInterface root = new Epic("EPIC", "E-1", "ROOT", "description ROOT", Status.NEW);
		TaskInterface parentA = new Epic("EPIC", "E-2", "Parent A", "description parent A", Status.NEW);
		actual.addTask(root, parentA);

		TaskInterface parentB = new Task("TASK", "T-3", "Parent B", "description parent B", Status.NEW);
		actual.addTask(root, parentB);

		TaskInterface childrenA = new Subtask("SUBTASK", "S-1", "Children A", "description children A", Status.NEW);
		actual.addTask(parentA, childrenA);

		TaskInterface childrenA1 = new Subtask("SUBTASK", "S-2", "Children A1", "description children A1", Status.NEW);
		actual.addTask(parentA, childrenA1);
	}

	@Test
	public void loadFromFileWhenInputGraphReturnNewGraphTasks() throws IOException {
		actual = new FileBackedTaskManager();
		Graph<TaskInterface> expectedGraph = new Graph<>();
		expectedGraph.addEdge(new Epic("EPIC", "E-1", "ROOT", "description ROOT", Status.NEW),
				new Epic("EPIC", "E-2", "Parent A", "description parent A", Status.NEW));
		expectedGraph.addEdge(new Epic("EPIC", "E-1", "ROOT", "description ROOT", Status.NEW),
				new Task("TASK", "T-3", "Parent B", "description parent B", Status.NEW));

		expectedGraph.addEdge(new Epic("EPIC", "E-2", "Parent A", "description parent A", Status.NEW),
				new Subtask("SUBTASK", "S-1", "Children A", "description children A", Status.NEW));
		expectedGraph.addEdge(new Epic("EPIC", "E-2", "Parent A", "description parent A", Status.NEW),
				new Subtask("SUBTASK", "S-2", "Children A1", "description children A1", Status.NEW));

		actual = FileBackedTaskManager.loadFromFile(path);

		for (TaskInterface keyExpected : expectedGraph.getAdjacencyList().keySet()) {
			assertTrue(actual.graph.getAdjacencyList().containsKey(keyExpected));
			Set<TaskInterface> setActual = actual.graph.getAdjacencyList().get(keyExpected);
			for (TaskInterface valueExpected : expectedGraph.getAdjacencyList().get(keyExpected)) {
				assertTrue(setActual.contains(valueExpected));
			}
		}

	}

}
