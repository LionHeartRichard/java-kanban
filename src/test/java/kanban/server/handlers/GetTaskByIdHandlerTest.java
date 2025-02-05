package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetTaskByIdHandlerTest extends SetUpServerForTests {

	@DisplayName("GET /epics/E-1")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		String url = "epics/E-1";
		String expectedJson = "\"epics,E-1,EPIC,description EPIC,NEW\"";
		checkResponse(2, 200, url, "", expectedJson);
	}

	@DisplayName("GET /tasks/T-1")
	@Test
	public void testTasks() throws IOException, InterruptedException {
		String url = "tasks/T-1";
		String expectedJson = "\"tasks,T-1,TASK,description TASK,NEW\"";
		checkResponse(2, 200, url, "", expectedJson);
	}

	@DisplayName("GET /subtasks/S-1")
	@Test
	public void testSubtasks() throws IOException, InterruptedException {
		String url = "subtasks/S-1";
		String expectedJson = "\"subtasks,S-1,SUBTASKS,description SUBTASKS,NEW\"";
		checkResponse(2, 200, url, "", expectedJson);
	}
}
