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
	public void getId_whenCreateNewTask_thenReturnNewId() {
		Task expected = new Task();

		assertNotEquals(expected, actual);
		assertNotEquals(expected.getId(), actual.getId());
	}

	@Test
	public void getStatus_whenCreateNewTask_thenReturnStatusNEW() {
		Task expected = new Task();

		assertEquals(expected.getStatus(), actual.getStatus());
	}

	@Test
	public void changeStatus_whenCreateNewTask_thenReturnStatusIN_PROGRESS() {
		Task expected = new Task();

		actual.changeStatus();

		assertNotEquals(expected.getStatus(), actual.getStatus());
	}

}
