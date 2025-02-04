package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetTasksHandlerTest extends SetUpServerForTests {

	@DisplayName("GET /epics")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		String url = "http://localhost:8080/epics";
		String expectedJson = "[\"epics,E-1,EPIC,description EPIC,NEW\"]";
		checkRequest(2, 200, url, "", expectedJson);
	}

	@DisplayName("GET /tasks")
	@Test
	public void testTasks() throws IOException, InterruptedException {
		String url = "http://localhost:8080/tasks";
		String expectedJson = "[\"tasks,T-1,TASK,description TASK,NEW\"]";
		checkRequest(2, 200, url, "", expectedJson);
	}

	@DisplayName("GET /subtasks")
	@Test
	public void testSubtasks() throws IOException, InterruptedException {
		String url = "http://localhost:8080/subtasks";
		String expectedJson = "[\"subtasks,S-1,SUBTASKS,description SUBTASKS,NEW\"]";
		checkRequest(2, 200, url, "", expectedJson);
	}

}
