package kanban.service;

import java.util.Deque;
import java.util.List;
import java.util.Set;

import kanban.model.TaskInterface;
import kanban.util.Status;

public interface TaskManager {

	boolean addTask(TaskInterface task);

	boolean addTask(TaskInterface topTask, TaskInterface task);

	boolean containsTaskById(String id);

	void removeTasks();

	TaskInterface getTaskById(String id);

	boolean removeTaskById(String id);

	Set<TaskInterface> getQuickSubtasks(String id);

	List<TaskInterface> getSubtasks(String id);

	List<TaskInterface> getAllTasks();

	Set<TaskInterface> getAllSetTasks();

	boolean updateTask(String id, String newName, String newDescription);

	boolean updateTask(TaskInterface task);

	List<TaskInterface> getListTasksByStatus(Status status);

	boolean changeStatusTask(String id);

	Deque<TaskInterface> getHistory();

}
