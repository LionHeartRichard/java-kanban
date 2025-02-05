package kanban.server.handlers;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Status;

public class PostTaskByIdHandlerTest extends SetUpServerForTests {

	@DisplayName("/tasks/{id} Post update task")
	@Test
	@Override
	public void test() throws IOException, InterruptedException {
		String url = "tasks/T-1";
		String jsonBodyRequest = "\"tasks,T-1,UPDATE,UPDATE,NEW\"";
		checkResponse(0, 201, url, jsonBodyRequest, "");
	}

	@DisplayName("/epics/{id} Post update epics")
	@Test
	public void testCreateEpic() throws IOException, InterruptedException {
		String url = "epics/E-1";
		String jsonBodyRequest = "\"epics,E-1,UPDATE,UPDATE,NEW\"";
		checkResponse(0, 201, url, jsonBodyRequest, "");
	}

	@DisplayName("/subtasks/{id} Post update subtasks")
	@Test
	public void testCreateSubtasks() throws IOException, InterruptedException {
		String url = "subtasks/S-1";
		String jsonBodyRequest = "\"subtasks,S-1,UPDATE,UPDATE,NEW\"";
		checkResponse(0, 201, url, jsonBodyRequest, "");
	}

	@DisplayName("/tasks/{id} No Update/406")
	@Test
	public void testNoUpdateTask() throws IOException, InterruptedException {
		TaskInterface taskT4 = new Task("tasks", "T-4", "Parent B-2", "description parent B-2", Status.NEW,
				"10:00 20.12.2024", 60);
		manager.addTask(taskT4);

		String url = "tasks/T-1";
		String jsonBodyRequest = "\"tasks,T-1,UPDATE,UPDATE,NEW,10:30 20.12.2024,60\"";
		checkResponse(0, 406, url, jsonBodyRequest, "");
	}

	@DisplayName("/epics/{id} No Update/406")
	@Test
	public void testNoUpdateEpic() throws IOException, InterruptedException {
		TaskInterface taskT4 = new Task("tasks", "T-4", "Parent B-2", "description parent B-2", Status.NEW,
				"10:00 20.12.2024", 60);
		manager.addTask(taskT4);

		String url = "epics/E-1";
		String jsonBodyRequest = "\"epics,E-1,UPDATE,UPDATE,NEW,10:30 20.12.2024,60\"";
		checkResponse(0, 406, url, jsonBodyRequest, "");
	}

	@DisplayName("/subtasks/{id} No Update/406")
	@Test
	public void testNoUpdateSubtasks() throws IOException, InterruptedException {
		TaskInterface taskT4 = new Task("tasks", "T-4", "Parent B-2", "description parent B-2", Status.NEW,
				"10:00 20.12.2024", 60);
		manager.addTask(taskT4);

		String url = "subtasks/S-1";
		String jsonBodyRequest = "\"subtasks,S-1,UPDATE,UPDATE,NEW,10:30 20.12.2024,60\"";
		checkResponse(0, 406, url, jsonBodyRequest, "");
	}

}
