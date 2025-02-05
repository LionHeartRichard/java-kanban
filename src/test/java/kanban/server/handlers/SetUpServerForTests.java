package kanban.server.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.server.TaskServer;
import kanban.service.TaskManager;
import kanban.service.impl.InMemoryTaskManager;
import kanban.util.Status;

public abstract class SetUpServerForTests {

	protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

	private static final String HOST = "http://localhost:8080/";
	protected TaskManager manager;
	protected HttpClient client;

	@BeforeEach
	public void setUp() throws IOException {
		manager = new InMemoryTaskManager();
		TaskServer.startServer(manager);
		client = HttpClient.newHttpClient();

		TaskInterface epic = new Epic("epics", "E-1", "EPIC", "description EPIC", Status.NEW);
		TaskInterface task = new Task("tasks", "T-1", "TASK", "description TASK", Status.NEW);
		assertTrue(manager.addTask(epic, task));

		TaskInterface subtask = new Subtask("subtasks", "S-1", "SUBTASKS", "description SUBTASKS", Status.NEW);
		assertTrue(manager.addTask(task, subtask));
	}

	@AfterEach
	public void shutDown() {
		TaskServer.stopServer();
	}

	protected void checkResponse(int typeMethod, int expectedStatusCode, String urlPath, String jsonRequest,
			String expectedJson) throws IOException, InterruptedException {
		URI url = URI.create(HOST + urlPath);
		HttpRequest request;
		if (typeMethod == 0) {
			request = HttpRequest.newBuilder().uri(url).header("Accept", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(jsonRequest)).build();
		} else if (typeMethod == 1) {
			request = HttpRequest.newBuilder().uri(url).DELETE().build();
		} else {
			request = HttpRequest.newBuilder().uri(url).GET().build();
		}
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		assertEquals(expectedStatusCode, response.statusCode());
		assertEquals(expectedJson, response.body());
	}

	public abstract void test() throws IOException, InterruptedException;
}
