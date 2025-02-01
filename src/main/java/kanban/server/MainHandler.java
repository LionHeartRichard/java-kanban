package kanban.server;

import java.util.Map;

public class MainHandler {

	private static Map<String, Handler> handlers;

	static {
		handlers.put(, new BaseHandler());
	}

}
