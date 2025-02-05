package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTaskByIdHandlerTest extends SetUpServerForTests {

	@DisplayName("DELETE /tasks/T-1")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		String url = "tasks/T-1";
		checkResponse(1, 200, url, "", "");
	}

	@DisplayName("DELETE /epics/E-1")
	@Test
	public void deleteEpic() throws IOException, InterruptedException {
		String url = "epics/E-1";
		checkResponse(1, 200, url, "", "");
	}

	@DisplayName("DELETE /subtasks/S-1")
	@Test
	public void deleteSubtasks() throws IOException, InterruptedException {
		String url = "subtasks/S-1";
		checkResponse(1, 200, url, "", "");
	}

}
