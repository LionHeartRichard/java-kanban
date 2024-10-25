package kanban.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<T> {

	private Map<T, Set<T>> map = new HashMap<>();
	private Set<T> cacheDFS = new HashSet<>();

	public Graph() {
	}

	public Graph(Map<T, Set<T>> adjacent) {
		this.map.clear();
		this.map = adjacent;
	}

	public void addVertex(T vertex) {
		map.put(vertex, new HashSet<T>());
	}

	public void addEdge(T topVertex, T vertex, boolean bidirectional) {
		if (!map.containsKey(topVertex))
			addVertex(topVertex);
		if (!map.containsKey(vertex))
			addVertex(vertex);
		map.get(topVertex).add(vertex);
		if (bidirectional)
			map.get(vertex).add(topVertex);
	}

	public List<T> BFS(T topVertex) {
		if (map.containsKey(topVertex)) {
			List<T> result = new ArrayList<>();
			result.add(topVertex);
			Set<T> cache = map.get(topVertex);
			result.addAll(cache);
			LinkedList<T> swap = new LinkedList<T>(cache);
			while (!swap.isEmpty()) {
				T currentVertex = swap.pop();
				for (T subVertex : map.get(currentVertex)) {
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

	public Set<T> DFS(T topVertex) {
		if (map.containsKey(topVertex)) {
			cacheDFS.clear();
			cacheDFS.add(topVertex);
			for (T vertex : map.get(topVertex)) {
				cacheDFS.add(vertex);
				traversalDFS(vertex);
			}
			return cacheDFS;
		}
		return null;
	}

	private void traversalDFS(T vertex) {
		Set<T> tmp = map.get(vertex);
		for (T subVertex : tmp) {
			cacheDFS.add(subVertex);
			traversalDFS(subVertex);
		}
	}

}