package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Graph;

public class FileBackedTaskManagerTest {

	private FileBackedTaskManager actual;
	private InMemoryTaskManager expected = new InMemoryTaskManager();

	private String path = "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/case0.json";

	@BeforeEach
	public void setUpAndSaveDataContainsInObjectFileBackedTaskManager() throws IOException {

		actual = new FileBackedTaskManager(path);

		TaskInterface root = new Epic("ROOT", "description ROOT");
		TaskInterface parentA = new Epic("Parent A", "description parent A");
		expected.addTask(root, parentA);
		actual.addTask(root, parentA);

		TaskInterface parentB = new Task("Parent B", "description parent B");
		expected.addTask(root, parentB);
		actual.addTask(root, parentA);

		TaskInterface childrenA = new Subtask("Children A", "description children A");
		expected.addTask(parentA, childrenA);
		actual.addTask(root, parentA);

		TaskInterface childrenA1 = new Subtask("Children A1", "description children A1");
		expected.addTask(parentA, childrenA1);
		actual.addTask(root, parentA);
	}

	@Test
	public void loadFromFileWhenInputGraphReturnNewGraphTasks() {

	}

}
