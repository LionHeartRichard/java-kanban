package kanban.server.handlers;

import java.io.IOException;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sun.net.httpserver.HttpExchange;

import kanban.model.TaskInterface;
import kanban.parsing.TaskInterfaceDeserializer;
import kanban.server.BasicHandler;
import kanban.service.TaskManager;

public class TaskByIdHandler extends BasicHandler {

	public TaskByIdHandler(TaskManager manager) {
		super(manager);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String[] uri = exchange.getRequestURI().toString().split("/");
		String id = uri[2];
		if (manager.containsTaskById(id)) {
			String method = exchange.getRequestMethod();
			if (method.equals("GET")) {
				methodGet(id, exchange);
			} else if (method.equals("POST")) {
				methodPostUpdate(exchange);
			} else if (method.equals("DELETE")) {
				manager.removeTaskById(id);
				action(200, exchange, "");
			}
		} else {
			action(404, exchange, "Not found " + uri[2] + " by: " + id);
		}
	}

	private void methodGet(String id, HttpExchange exchange) throws IOException {
		TaskInterface response = manager.getTaskById(id);
		String jsonBody = mapper.writeValueAsString(response);
		action(200, exchange, jsonBody);
	}
}
