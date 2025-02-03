package kanban.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import kanban.service.impl.FileBackedTaskManager;
import kanban.service.impl.InMemoryTaskManager;

public class TaskServer {

	private static final int PORT = 8080;
	private static final String PATH = "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/case0.json";

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
		MainHandler mainHandler = new MainHandler(new FileBackedTaskManager(PATH));
		Map<String, Handler> mapperHandlers = mainHandler.getHandlers();
		mapperHandlers.forEach((k, v) -> server.createContext(k, (HttpHandler) v));
		server.start();
		System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
	}

}
