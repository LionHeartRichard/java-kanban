package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PostTasksHandlerTest extends SetUpServerForTests {

	@DisplayName("/tasks CREATE-task, T-444")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		String url = "tasks";
		String jsonBodyRequest = "\"tasks,T-444,Parent B-2,description parent B-2,NEW,10:00 20.12.2024,60\"";
		checkResponse(0, 201, url, jsonBodyRequest, "");
	}

	@DisplayName("/epics CREATE-epic, E-88")
	@Test
	public void testCreateEpic() throws IOException, InterruptedException {
		String url = "epics";
		String jsonBodyRequest = "\"epics,E-88,EPIC,description EPIC create,NEW,10:00 25.01.2025,180\"";
		checkResponse(0, 201, url, jsonBodyRequest, "");
	}

	@DisplayName("/subtasks CREATE-subtasks, S-777")
	@Test
	public void testCreateSubtasks() throws IOException, InterruptedException {
		String url = "subtasks";
		String jsonBodyRequest = "\"subtasks,S-777,SUBTASKS,description SUBTASKS create,NEW,10:00 27.01.2025,77\"";
		checkResponse(0, 201, url, jsonBodyRequest, "");
	}

}
