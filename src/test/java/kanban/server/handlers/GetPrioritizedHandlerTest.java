package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Task;
import kanban.util.Status;

public class GetPrioritizedHandlerTest extends SetUpServerForTests {

	@DisplayName("GET /prioritized")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		TaskInterface taskT4 = new Task("tasks", "T-4", "Parent B-2", "description parent B-2", Status.NEW,
				"10:00 20.12.2024", 60);
		TaskInterface taskT5 = new Task("tasks", "T-5", "Children B-2", "description CHILDREN B-2", Status.NEW,
				"15:00 22.12.2024", 120);
		manager.addTask(taskT4);
		manager.addTask(taskT5);

		String url = "prioritized";
		String expectedJson = "[\"tasks,T-4,Parent B-2,description parent B-2,NEW,10:00 20.12.2024,60\",\"tasks,T-5,Children B-2,description CHILDREN B-2,NEW,15:00 22.12.2024,120\"]";
		checkResponse(2, 200, url, "", expectedJson);
	}

}
