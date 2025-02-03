package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

	private FileBackedTaskManager actual;

	private String path = "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/case0.json";

	@BeforeEach
	public void setUpAndSaveDataContainsInObjectFileBackedTaskManager() throws IOException {

		actual = new FileBackedTaskManager(path);

		TaskInterface root = new Epic("epics", "E-1", "ROOT", "description ROOT", Status.NEW);
		TaskInterface parentA = new Epic("epics", "E-2", "Parent A", "description parent A", Status.NEW);
		assertTrue(actual.addTask(root, parentA));

		TaskInterface parentB = new Task("tasks", "T-3", "Parent B", "description parent B", Status.NEW);
		assertTrue(actual.addTask(root, parentB));

		TaskInterface childrenA = new Subtask("subtasks", "S-1", "Children A", "description children A", Status.NEW);
		assertTrue(actual.addTask(parentA, childrenA));

		TaskInterface childrenA1 = new Subtask("subtasks", "S-2", "Children A1", "description children A1", Status.NEW);
		assertTrue(actual.addTask(parentA, childrenA1));

		TaskInterface parentB2 = new Task("tasks", "T-4", "Parent B-2", "description parent B-2", Status.NEW);
		parentB2.setStartTime(LocalDateTime.parse("10:00 20.12.2024", DATE_TIME_FORMATTER));
		parentB2.setDuration(Duration.ofMinutes(60));
		TaskInterface childrenB2 = new Task("tasks", "T-5", "children B-2", "description parent B-2", Status.NEW);
		childrenB2.setStartTime(LocalDateTime.parse("15:00 22.12.2024", DATE_TIME_FORMATTER));
		childrenB2.setDuration(Duration.ofMinutes(120));
		TaskInterface root2 = new Epic("epics", "E-1", "ROOT2", "Description for ROOT-2", Status.NEW);

		assertTrue(actual.addTask(root2, parentB2));
		assertTrue(actual.addTask(parentB2, childrenB2));

		root2 = new Epic(actual.getGraph(), (Epic) root2);
		assertTrue(actual.updateTask(root2));
		root2.getClass();
	}

	@Test
	public void loadFromFileWhenInputGraphReturnNewGraphTasks() throws IOException {

		actual = new FileBackedTaskManager();

		Graph<TaskInterface> expectedGraph = new Graph<>();
		expectedGraph.addEdge(new Epic("epics", "E-1", "ROOT", "description ROOT", Status.NEW),
				new Epic("epics", "E-2", "Parent A", "description parent A", Status.NEW));
		expectedGraph.addEdge(new Epic("epics", "E-1", "ROOT", "description ROOT", Status.NEW),
				new Task("tasks", "T-3", "Parent B", "description parent B", Status.NEW));

		expectedGraph.addEdge(new Epic("epics", "E-2", "Parent A", "description parent A", Status.NEW),
				new Subtask("subtasks", "S-1", "Children A", "description children A", Status.NEW));
		expectedGraph.addEdge(new Epic("epics", "E-2", "Parent A", "description parent A", Status.NEW),
				new Subtask("subtasks", "S-2", "Children A1", "description children A1", Status.NEW));

		TaskInterface parentB2 = new Task("tasks", "T-4", "Parent B-2", "description parent B-2", Status.NEW);
		parentB2.setStartTime(LocalDateTime.parse("10:00 20.12.2024", DATE_TIME_FORMATTER));
		parentB2.setDuration(Duration.ofMinutes(60));
		TaskInterface childrenB2 = new Task("tasks", "T-5", "children B-2", "description parent B-2", Status.NEW);
		childrenB2.setStartTime(LocalDateTime.parse("15:00 22.12.2024", DATE_TIME_FORMATTER));
		childrenB2.setDuration(Duration.ofMinutes(120));
		TaskInterface root2 = new Epic("epics", "E-1", "ROOT2", "Description for ROOT-2", Status.NEW);

		expectedGraph.addEdge(root2, parentB2);
		expectedGraph.addEdge(parentB2, childrenB2);
		root2 = new Epic(expectedGraph, (Epic) root2);
		assertTrue(expectedGraph.update(root2));

		Set<TaskInterface> expectedSetAllTasks = expectedGraph.getSetAllVertices();
		actual = FileBackedTaskManager.loadFromFile(path);

		actual.getAllSetTasks().stream().peek(t -> {
			assertTrue(expectedSetAllTasks.contains(t));
		}).toList();
	}

}
