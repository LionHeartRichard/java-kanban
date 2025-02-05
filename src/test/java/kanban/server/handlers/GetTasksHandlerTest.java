package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetTasksHandlerTest extends SetUpServerForTests {

	@DisplayName("GET /epics")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		String url = "epics";
		String expectedJson = "[\"epics,E-1,EPIC,description EPIC,NEW\"]";
		checkResponse(2, 200, url, "", expectedJson);
	}

	@DisplayName("GET /tasks")
	@Test
	public void testTasks() throws IOException, InterruptedException {
		String url = "tasks";
		String expectedJson = "[\"tasks,T-1,TASK,description TASK,NEW\"]";
		checkResponse(2, 200, url, "", expectedJson);
	}

	@DisplayName("GET /subtasks")
	@Test
	public void testSubtasks() throws IOException, InterruptedException {
		String url = "subtasks";
		String expectedJson = "[\"subtasks,S-1,SUBTASKS,description SUBTASKS,NEW\"]";
		checkResponse(2, 200, url, "", expectedJson);
	}

}
