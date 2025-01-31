package kanban.server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {

	// 200?!
	protected void sendJsonForResponsBody(HttpExchange exchange, String jsonBody) throws IOException {
		byte[] resp = jsonBody.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
		exchange.sendResponseHeaders(200, resp.length);
		exchange.getResponseBody().write(resp);
		exchange.close();
	}
}
