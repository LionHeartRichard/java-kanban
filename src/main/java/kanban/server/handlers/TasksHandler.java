package kanban.server.handlers;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;

import kanban.model.TaskInterface;
import kanban.server.BasicHandler;
import kanban.service.TaskManager;

public class TasksHandler extends BasicHandler {

	public TasksHandler(TaskManager manager) {
		super(manager);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Set<TaskInterface> allTasks = manager.getAllSetTasks();
		if (allTasks != null && !allTasks.isEmpty()) {
			if (exchange.getRequestMethod().equals("GET")) {
				methodGet(exchange, allTasks);
			}
		} else if (exchange.getRequestMethod().equals("POST")) {
			methodPost(exchange, false);
		} else {
			action(404, exchange, "Not Found");
		}
	}

	private void methodGet(HttpExchange exchange, Set<TaskInterface> allTasks) throws IOException {
		String type = exchange.getRequestURI().toString().split("/")[1];
		Set<TaskInterface> response = allTasks.stream().filter(t -> t.getType().equals(type))
				.collect(Collectors.toSet());
		String jsonBody = mapper.writeValueAsString(response);
		action(200, exchange, jsonBody);
	}
}
