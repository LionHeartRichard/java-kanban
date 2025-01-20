package kanban.model.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import kanban.comparatorscustom.StartTimeTaskComparator;
import kanban.model.TaskInterface;
import kanban.util.Graph;
import kanban.util.Status;

public class Epic extends Task {

	private LocalDateTime endTime;

	@Override
	@JsonIgnore
	public LocalDateTime getEndTime() {
		return endTime;
	}

	private static final String PREFIX = "E-";
	private static long count;

	public Epic() {
		type = "EPIC";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}

	public Epic(String name, String description) {
		type = "EPIC";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
		this.name = name;
		this.description = description;
	}

	public Epic(Graph<TaskInterface> graph, Epic epic) {
		this.type = epic.type;
		this.id = epic.id;
		this.status = epic.status;
		this.name = epic.name;
		this.description = epic.description;
		Set<TaskInterface> priorityTasks = new TreeSet<TaskInterface>(new StartTimeTaskComparator());

		priorityTasks.addAll(graph.depthFirstSearch(epic).stream().filter(t -> t.getStartTime() != null).toList());

		this.duration = Duration.ofMinutes(0);

		Optional.ofNullable(priorityTasks).ifPresent(tasks -> tasks.stream().peek(t -> {
			if (this.startTime == null)
				this.startTime = t.getStartTime();
			this.duration = Duration.ofMinutes(this.duration.toMinutes() + t.getDuration().toMinutes());
			this.endTime = t.getEndTime();
		}).toList());
	}

	@JsonCreator
	public Epic(String type, String id, String name, String description, Status status) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	@JsonCreator
	public Epic(String type, String id, String name, String description, Status status, String startTime,
			int durationMinutes) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.startTime = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
		this.duration = Duration.ofMinutes(durationMinutes);
	}

	@Override
	@JsonValue
	public String toString() {
		if (startTime != null && duration != null)
			return type + "," + id + "," + name + "," + description + "," + status + "," + startTime + "," + duration;
		return type + "," + id + "," + name + "," + description + "," + status;
	}

	@Override
	public boolean validDuration(TaskInterface other) {
		if (startTime != null && duration != null && endTime != null)
			return true;
		return false;
	}
}
