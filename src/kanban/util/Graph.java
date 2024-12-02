package kanban.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<T> {

	private Map<T, Set<T>> graph = new HashMap<>();
	private Set<T> cacheDFS = new HashSet<>();

	public Graph() {
	}

	public Graph(Map<T, Set<T>> adjacent) {
		this.graph.clear();
		this.graph = adjacent;
	}

	public int size() {
		return graph.size();
	}

	public boolean isEmpty() {
		return graph.isEmpty();
	}

	public void addVertex(T vertex) {
		graph.put(vertex, new HashSet<T>());
	}

	public void addEdgeWithoutCheckNullByKeyMap(T topVertex, T vertex) {
		graph.get(topVertex).add(vertex);
	}

	public void addEdge(T topVertex, T vertex) {
		if (!graph.containsKey(topVertex))
			addVertex(topVertex);
		if (!graph.containsKey(vertex))
			addVertex(vertex);
		graph.get(topVertex).add(vertex);
	}

	public void addEdge(T topVertex, T vertex, boolean bidirectional) {
		if (!graph.containsKey(topVertex))
			addVertex(topVertex);
		if (!graph.containsKey(vertex))
			addVertex(vertex);
		graph.get(topVertex).add(vertex);
		if (bidirectional)
			graph.get(vertex).add(topVertex);
	}

	public List<T> breadthFirstSearch(T topVertex) {
		if (graph.containsKey(topVertex)) {
			List<T> result = new ArrayList<>();
			Set<T> cache = graph.get(topVertex);
			result.addAll(cache);
			LinkedList<T> swap = new LinkedList<T>(cache);
			while (!swap.isEmpty()) {
				T currentVertex = swap.pop();
				for (T subVertex : graph.get(currentVertex)) {
					if (!cache.contains(subVertex)) {
						cache.add(subVertex);
						swap.add(subVertex);
						result.add(subVertex);
					}
				}
			}
			return result;
		}
		return null;
	}

	public Set<T> depthFirstSearch(T topVertex) {
		if (graph.containsKey(topVertex)) {
			cacheDFS.clear();
			for (T vertex : graph.get(topVertex)) {
				cacheDFS.add(vertex);
				traversalDFS(vertex);
			}
			return cacheDFS;
		}
		return null;
	}

	private void traversalDFS(T vertex) {
		Set<T> tmp = graph.get(vertex);
		for (T subVertex : tmp) {
			cacheDFS.add(subVertex);
			traversalDFS(subVertex);
		}
	}

	public List<T> getListAllVertices() {
		List<T> vertices = new ArrayList<T>(graph.keySet());
		return vertices;
	}

	public Set<T> getSetAllVertices() {
		Set<T> vertices = new HashSet<>(graph.keySet());
		return vertices;
	}

	public void removeVertices() {
		graph.clear();
	}

	public void removeVertex(T vertex) {
		graph.remove(vertex);
	}

}
