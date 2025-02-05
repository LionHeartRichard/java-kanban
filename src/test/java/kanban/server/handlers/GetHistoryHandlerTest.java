package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetHistoryHandlerTest extends SetUpServerForTests {

	@DisplayName("GET /history")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		manager.getTaskById("T-1");
		manager.getTaskById("S-1");

		String url = "history";
		String expectedJson = "[\"tasks,T-1,TASK,description TASK,NEW\",\"subtasks,S-1,SUBTASKS,description SUBTASKS,NEW\"]";
		checkResponse(2, 200, url, "", expectedJson);
	}

}
