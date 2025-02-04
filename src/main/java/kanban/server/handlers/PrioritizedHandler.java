package kanban.server.handlers;

import java.io.IOException;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;

import kanban.model.TaskInterface;
import kanban.server.BasicHandler;
import kanban.service.TaskManager;

public class PrioritizedHandler extends BasicHandler {

	public PrioritizedHandler(TaskManager manager) {
		super(manager);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Set<TaskInterface> response = manager.getPrioritizedTasks();
		String jsonBody = mapper.writeValueAsString(response);
		action(200, exchange, jsonBody);
	}

}
