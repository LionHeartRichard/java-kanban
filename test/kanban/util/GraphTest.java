package kanban.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;

public class GraphTest {

	private Graph<String> graph;
	private String topVertexA;
	private String topVertexB;
	private String vertexA1;
	private String vertexA2;
	private String vertexB1;
	private String vertexB2;
	private Set<String> subVerticesA;
	private List<String> subVerticesB;

	@BeforeEach
	public void setUp() {
		graph = new Graph<String>();

		topVertexA = "A";
		vertexA1 = "A1";
		vertexA2 = "A2";
		subVerticesA = new HashSet<>();
		subVerticesA.add(vertexA1);
		subVerticesA.add(vertexA2);

		topVertexB = "B";
		vertexB1 = "B1";
		vertexB2 = "B2";
		subVerticesB = new ArrayList<>();
		subVerticesB.add(vertexB1);
		subVerticesB.add(vertexB2);
	}

	@Test
	public void addVertex_whenEmptyGraphAddNewVertex_ReturnNotEmpty() {
		assertTrue(graph.isEmpty());

		graph.addVertex(topVertexA);

		assertFalse(graph.isEmpty());
	}

	@Test
	public void addVertices_whenAddSetSubverticesInGraph() {
		graph.addVertices(topVertexA, subVerticesA);
	}

	@Test
	public void addVetices() {

	}

	@Test
	public void addEdge_whenEmptyGraphAddTopVertexAndVertex_ReturnSizeForGraph2() {
		graph.addEdge(topVertexA, vertexA1);

		assertEquals(2, graph.size());
	}
//	
//
//	public void addEdge(T topVertex, T vertex, boolean bidirectional) {
//	
//
//	public List<T> BFS(T topVertex) {
//		
//
//	public Set<T> DFS(T topVertex) {

//
//	public void removeVertices() {
//
//
//	public void removeVertex(T vertex) {

}
