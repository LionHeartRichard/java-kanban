package kanban.service;

import java.util.List;
import java.util.Set;

import kanban.model.TaskInterface;
import kanban.util.Status;

public interface TaskManager {

	boolean isEmpty();

	int size();

	boolean addTask(TaskInterface task);

	boolean addTask(TaskInterface topTask, TaskInterface task);

	boolean containsTaskById(String id);

	void removeTasks();

	TaskInterface getTaskById(String id);

	boolean removeTaskById(String id);

	Set<TaskInterface> getSetSubtasks(String id);

	List<TaskInterface> getSubtasks(String id);

	List<TaskInterface> getAllTasks();

	Set<TaskInterface> getAllSetTasks();

	boolean updateTask(String id, String newName, String newDescription);

	boolean updateTask(TaskInterface task);

	List<TaskInterface> getListTasksByStatus(Status status);

	boolean changeStatusTask(String id);

	List<TaskInterface> getHistory();

	Set<TaskInterface> getPrioritizedTasks();

}
