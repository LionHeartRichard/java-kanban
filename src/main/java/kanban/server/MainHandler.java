package kanban.server;

import java.util.HashMap;
import java.util.Map;

import kanban.server.handlers.TasksHandler;
import kanban.server.handlers.HistoryHandler;
import kanban.server.handlers.PrioritizedHandler;
import kanban.server.handlers.TaskByIdHandler;
import kanban.service.TaskManager;
import lombok.Getter;

@Getter
public class MainHandler {

	private Map<String, Handler> handlers;

	public MainHandler(TaskManager manager) {
		handlers = new HashMap<>();

		handlers.put("/tasks", new TasksHandler(manager));
		handlers.put("/epics", new TasksHandler(manager));
		handlers.put("/subtasks", new TasksHandler(manager));

		handlers.put("/tasks/T", new TaskByIdHandler(manager));
		handlers.put("/epics/E", new TaskByIdHandler(manager));
		handlers.put("/subtasks/S", new TaskByIdHandler(manager));

		handlers.put("/prioritized", new PrioritizedHandler(manager));
		handlers.put("/history", new HistoryHandler(manager));
	}

}
