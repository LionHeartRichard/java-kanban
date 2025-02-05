package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTaskByIdHandlerTest extends SetUpServerForTests {

	@DisplayName("DELETE /tasks/T-1")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		String url = "http://localhost:8080/tasks/T-1";
		checkRequest(1, 200, url, "", "");
	}

}
