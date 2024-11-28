package kanban.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kanban.model.TaskInterface;

public class CuctomList {

	private Node head;
	private Node tail;
	private Map<String, Node> map;

	public CuctomList() {
		map = new HashMap<>();
	}

	public void add(TaskInterface task) {
		if (map.isEmpty()) {
			addFirstNode(task);
		} else {
			if (map.containsKey(task.getId())) {
				remove(task.getId());
			}
			addNode(task);
		}
		map.put(task.getId(), tail);
	}

	public List<TaskInterface> getQueue() {
		List<TaskInterface> result = new ArrayList<>(map.size());
		Node node = head;
		while (node != null) {
			result.add(node.data);
			node = node.next;
		}
		return result;
	}

	public void remove(String id) {
		Node node = map.get(id);
		if (node == null)
			return;// проверь удаление несуществующего элемента
		removeNode(node);
		map.remove(id);
	}

	private void removeNode(Node node) {
		// проверить когда предыдущего элемента нет
		// проверить когда следующий элемент отсутствует
		// а также обычный случай
		final Node previousNode = node.previos;
		final Node nextNode = node.next;
		if (previousNode == null && nextNode == null) {
			head = null;/// ?????!!!!!!!!!
			tail = null;
			return;
		}
		if (previousNode != null) {
			previousNode.next = nextNode;
			map.put(previousNode.data.getId(), previousNode);
		} // ---------------------!!!!!!!!!!!!!!!!!!!!!!!
	}

	private void addFirstNode(TaskInterface task) {
		head = new Node(null, task, null);
		tail = head;
	}

	private void addNode(TaskInterface task) {
		final Node previusNode = tail;
		final Node nextNode = new Node(previusNode, task, null);
		tail = nextNode;
		if (head.next == null)
			head.next = tail;
	}

	public int size() {
		return map.size();
	}

	public boolean contains(String id) {
		return map.containsKey(id);
	}

}
