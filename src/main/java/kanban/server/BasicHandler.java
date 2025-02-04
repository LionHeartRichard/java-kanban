package kanban.server;

import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import kanban.model.TaskInterface;
import kanban.parsing.TaskInterfaceDeserializer;
import kanban.service.TaskManager;

public abstract class BasicHandler implements Handler, HttpHandler {

	protected static ObjectMapper mapper = new ObjectMapper();
	protected TaskManager manager;

	public BasicHandler(TaskManager manager) {
		this.manager = manager;
		SimpleModule module = new SimpleModule();
		module.addDeserializer(TaskInterface.class, new TaskInterfaceDeserializer());
		mapper.registerModule(module);
	}

	@Override
	public void action(int statusCode, HttpExchange exchange, String jsonBody) throws IOException {
		byte[] response = jsonBody.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
		exchange.sendResponseHeaders(statusCode, response.length);
		exchange.getResponseBody().write(response);
		exchange.close();
	}

	@Override
	public abstract void handle(HttpExchange exchange) throws IOException;

	protected void methodPost(HttpExchange exchange, boolean isUpdate) throws IOException {
		InputStream inputStream = exchange.getRequestBody();
		String jsonBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		TaskInterface response = mapper.readValue(jsonBody, TaskInterface.class);
		if (isUpdate) {
			if (manager.updateTask(response)) {
				action(201, exchange, "");
			} else {
				action(406, exchange, "");
			}
		} else {
			if (manager.addTask(response)) {
				action(201, exchange, "");
			} else {
				action(406, exchange, "");
			}
		}
	}
}
