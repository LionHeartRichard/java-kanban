package kanban.comparatorscustom;

import java.util.Comparator;

import kanban.model.TaskInterface;

public class StartTimeTaskComparator implements Comparator<TaskInterface> {

	@Override
	public int compare(TaskInterface t1, TaskInterface t2) {
		return t1.getStartTime().compareTo(t2.getStartTime());
	}
}
