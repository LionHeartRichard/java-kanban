package kanban.server;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public interface Handler {
	void action(int statusCode, HttpExchange exchange, String jsonBody) throws IOException;
}
