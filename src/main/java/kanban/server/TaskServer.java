package kanban.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import kanban.service.TaskManager;
import kanban.service.impl.FileBackedTaskManager;
import kanban.service.impl.InMemoryTaskManager;

public class TaskServer {

	private static HttpServer server;
	private static final int PORT = 8080;
	private static final String PATH = "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/case0.json";

	private TaskServer() {
	}

	public static void main(String[] args) throws IOException {
		FileBackedTaskManager manager = new FileBackedTaskManager(PATH);
		startServer(manager);
	}

	public static void startServer(TaskManager manager) throws IOException {
		server = HttpServer.create(new InetSocketAddress(PORT), 0);
		MainHandler mainHandler = new MainHandler(manager);
		Map<String, Handler> mappingHandlers = mainHandler.getHandlers();
		mappingHandlers.forEach((k, v) -> server.createContext(k, (HttpHandler) v));
		server.start();
		System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
	}

	public static void stopServer() {
		server.stop(1);
		System.out.println("HTTP-сервер остановлен");
	}

}
