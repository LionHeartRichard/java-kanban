package kanban.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Graph<T> {

	private Map<T, Set<T>> adjacencyList = new HashMap<>();
	private Set<T> cacheDFS = new HashSet<>();

	public Graph() {
	}

	@JsonCreator
	public Graph(Map<T, Set<T>> adjacent) {
		this.adjacencyList.clear();
		this.adjacencyList = adjacent;
	}

	public int size() {
		return adjacencyList.size();
	}

	@JsonIgnore
	public boolean isEmpty() {
		return adjacencyList.isEmpty();
	}

	public void addVertex(T vertex) {
		adjacencyList.put(vertex, new HashSet<T>());
	}

	public void addEdgeWithoutCheckNullByKeyMap(T topVertex, T vertex) {
		adjacencyList.get(topVertex).add(vertex);
	}

	public void addEdge(T topVertex, T vertex) {
		if (!adjacencyList.containsKey(topVertex))
			addVertex(topVertex);
		if (!adjacencyList.containsKey(vertex))
			addVertex(vertex);
		adjacencyList.get(topVertex).add(vertex);
	}

	public void addEdge(T topVertex, T vertex, boolean bidirectional) {
		if (!adjacencyList.containsKey(topVertex))
			addVertex(topVertex);
		if (!adjacencyList.containsKey(vertex))
			addVertex(vertex);
		adjacencyList.get(topVertex).add(vertex);
		if (bidirectional)
			adjacencyList.get(vertex).add(topVertex);
	}

	public List<T> breadthFirstSearch(T topVertex) {
		if (adjacencyList.containsKey(topVertex)) {
			List<T> result = new ArrayList<>();
			Set<T> cache = adjacencyList.get(topVertex);
			result.addAll(cache);
			LinkedList<T> swap = new LinkedList<T>(cache);
			while (!swap.isEmpty()) {
				T currentVertex = swap.pop();
				for (T subVertex : adjacencyList.get(currentVertex)) {
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
		if (adjacencyList.containsKey(topVertex)) {
			cacheDFS.clear();
			for (T vertex : adjacencyList.get(topVertex)) {
				cacheDFS.add(vertex);
				traversalDFS(vertex);
			}
			return cacheDFS;
		}
		return null;
	}

	private void traversalDFS(T vertex) {
		Set<T> tmp = adjacencyList.get(vertex);
		for (T subVertex : tmp) {
			cacheDFS.add(subVertex);
			traversalDFS(subVertex);
		}
	}

	@JsonIgnore
	public List<T> getListAllVertices() {
		List<T> vertices = new ArrayList<T>(adjacencyList.keySet());
		return vertices;
	}

	@JsonIgnore
	public Set<T> getSetAllVertices() {
		Set<T> vertices = new HashSet<>(adjacencyList.keySet());
		return vertices;
	}

	public void removeVertices() {
		adjacencyList.clear();
	}

	public void removeVertex(T vertex) {
		adjacencyList.remove(vertex);
	}

	public boolean containsKey(T vertex) {
		return adjacencyList.containsKey(vertex);
	}

	@JsonAnyGetter
	public Map<T, Set<T>> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(Map<T, Set<T>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	public boolean update(T vetex) {
		if (adjacencyList.containsKey(vetex)) {
			Set<T> swap = adjacencyList.get(vetex);
			adjacencyList.put(vetex, swap);
		}
		return false;
	}
}
