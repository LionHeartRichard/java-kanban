package kanban.service;

import kanban.model.TaskInterface;
import kanban.util.Graph;

public class TaskManager {

	private Graph<TaskInterface> graph = new Graph<>();

	public Graph<TaskInterface> getGraph() {
		return graph;
	}

	public void addTasks() {

	}
}
