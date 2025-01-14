package test.kanban.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import main.kanban.service.HistoryManager;
import main.kanban.service.Managers;
import main.kanban.service.TaskManager;

public class ManagersTest {

	@Test
	public void getDefaultWhenCallMethodThenReturnTaskManager() {
		TaskManager manager = Managers.getDefault();

		assertNotNull(manager);
	}

	@Test
	public void getDefaultHistoryWhenCallMethodThenReturnHistoryManager() {
		HistoryManager history = Managers.getDefaultHistory();

		assertNotNull(history);
	}
}
