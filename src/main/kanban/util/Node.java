package main.kanban.util;

import java.util.Objects;
import main.kanban.model.TaskInterface;

class Node {
	Node previos;
	TaskInterface data;
	Node next;

	public Node(Node previusNode, TaskInterface data, Node nextNode) {
		this.previos = previusNode;
		this.data = data;
		this.next = nextNode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
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
		return Objects.equals(data, other.data);
	}

}
