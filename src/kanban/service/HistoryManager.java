package kanban.service;

import java.util.List;

import kanban.model.TaskInterface;

public interface HistoryManager {

	void add(TaskInterface task);

	List<TaskInterface> getHistory();

	void remove(String id);
}
