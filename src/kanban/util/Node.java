package kanban.util;

import java.util.Objects;

import kanban.model.TaskInterface;

class Node {
	TaskInterface data;
	Node next;
	Node previos;
	private String id;

	Node(Node previous, TaskInterface data, Node next) {
		this.data = data;
		this.next = next;
		this.previos = previous;
		this.id = data.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return Objects.equals(id, other.id);
	}

}
