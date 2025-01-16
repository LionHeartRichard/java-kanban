package kanban.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {

	private Task actual;

	@BeforeEach
	public void setUp() {
		actual = new Task();
	}

	@Test
	public void getIdWhenCreateNewTaskThenReturnNewId() {
		Task expected = new Task();
		assertNotEquals(expected, actual);
		assertNotEquals(expected.getId(), actual.getId());
	}

	@Test
	public void getStatusWhenCreateNewTaskThenReturnStatusNEW() {
		Task expected = new Task();

		assertEquals(expected.getStatus(), actual.getStatus());
	}

	@Test
	public void changeStatusWhenCreateNewTaskThenReturnStatusInProgress() {
		Task expected = new Task();

		actual.changeStatus();

		assertNotEquals(expected.getStatus(), actual.getStatus());
	}

}
