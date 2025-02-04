package kanban.server.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;

import kanban.model.TaskInterface;
import kanban.server.BasicHandler;
import kanban.service.TaskManager;

public class HistoryHandler extends BasicHandler {

	public HistoryHandler(TaskManager manager) {
		super(manager);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		List<TaskInterface> response = manager.getHistory();
		String jsonBody = mapper.writeValueAsString(response);
		action(200, exchange, jsonBody);
	}

}
