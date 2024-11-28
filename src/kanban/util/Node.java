package kanban.util;

import kanban.model.TaskInterface;

class Node {
	TaskInterface data;
	Node next;
	Node previos;

	Node(Node previous, TaskInterface data, Node next) {
		this.data = data;
		this.next = next;
		this.previos = previous;
	}
}
