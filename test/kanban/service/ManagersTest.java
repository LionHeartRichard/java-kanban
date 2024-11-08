package kanban.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ManagersTest {

	@Test
	public void getDefault_whenCallMethod_ReturnTaskManager() {
		TaskManager manager = Managers.getDefault();

		assertNotNull(manager);
	}

	@Test
	public void getDefaultHistory_whenCallMethod_ReturnHistoryManager() {
		HistoryManager history = Managers.getDefaultHistory();

		assertNotNull(history);
	}
}
