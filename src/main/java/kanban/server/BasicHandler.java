package kanban.server;

import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import kanban.model.TaskInterface;
import kanban.parsing.TaskInterfaceDeserializer;
import kanban.service.TaskManager;

public abstract class BasicHandler implements Handler, HttpHandler {

	protected static ObjectMapper mapper = new ObjectMapper();
	protected TaskManager manager;

	public BasicHandler(TaskManager manager) {
		this.manager = manager;
	}

	@Override
	public void action(int statusCode, HttpExchange exchange, String jsonBody) throws IOException {
		byte[] respons = jsonBody.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
		exchange.sendResponseHeaders(statusCode, respons.length);
		exchange.getResponseBody().write(respons);
		exchange.close();
	}

	@Override
	public abstract void handle(HttpExchange exchange) throws IOException;

	protected void methodPostUpdate(HttpExchange exchange) throws IOException {
		String jsonBody = exchange.getRequestBody().toString();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(TaskInterface.class, new TaskInterfaceDeserializer());
		mapper.registerModule(module);
		TaskInterface response = mapper.readValue(jsonBody, TaskInterface.class);
		manager.updateTask(response);
		action(201, exchange, "");
	}
}
