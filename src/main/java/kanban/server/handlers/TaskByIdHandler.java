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
			String jsonBody = "";
			TaskInterface response;
			if (method.equals("GET")) {
				response = manager.getTaskById(id);
				jsonBody = mapper.writeValueAsString(response);
				action(200, exchange, jsonBody);
			} else if (method.equals("POST")) {
				jsonBody = exchange.getRequestBody().toString();
				SimpleModule module = new SimpleModule();
				module.addDeserializer(TaskInterface.class, new TaskInterfaceDeserializer());
				mapper.registerModule(module);
				response = mapper.readValue(jsonBody, TaskInterface.class);
				manager.updateTask(response);
				action(201, exchange, "");
			} else if (method.equals("DELETE")) {
				manager.removeTaskById(id);
				action(200, exchange, "");
			}
		} else {
			action(404, exchange, "Not found " + uri[2] + " by: " + id);
		}

	}

}
