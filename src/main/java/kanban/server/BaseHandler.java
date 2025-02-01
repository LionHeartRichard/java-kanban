package kanban.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHandler implements Handler, HttpHandler {

	// 200?!
	protected void sendJsonForResponsBody(HttpExchange exchange, String jsonBody) throws IOException {
		byte[] resp = jsonBody.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
		exchange.sendResponseHeaders(200, resp.length);
		exchange.getResponseBody().write(resp);
		exchange.close();
	}

	@Override
	public boolean action(int statusCode, HttpExchange exchange, String jsonBody) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
