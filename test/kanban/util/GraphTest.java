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

	@BeforeEach
	public void setUp() {
		graph = new Graph<String>();

		topVertexA = "A";
		vertexA1 = "A1";
		vertexA2 = "A2";

		topVertexB = "B";
		vertexB1 = "B1";
		vertexB2 = "B2";
	}

	@Test
	public void addVertex_whenEmptyGraphAddNewVertex_ReturnNotEmpty() {
		assertTrue(graph.isEmpty());

		graph.addVertex(topVertexA);

		assertFalse(graph.isEmpty());
	}

	@Test
	public void addEdge_whenEmptyGraphAddTopVertexAndVertex_ReturnSizeForGraph2() {
		graph.addEdge(topVertexA, vertexA1);
		Graph<String> newGraph = new Graph<>();
		newGraph.addEdge(topVertexB, vertexB1, true);

		assertEquals(2, graph.size());
		assertEquals(2, newGraph.size());
	}

	@Test
	public void BFS_whenNotEmptyGraph_ReturnListSubverticesInAmount2() {
		graph.addEdge(topVertexA, vertexA1);
		graph.addEdge(topVertexA, vertexA2);

		List<String> actual = graph.BFS(topVertexA);

		assertEquals(vertexA1, actual.get(0));
		assertEquals(vertexA2, actual.get(1));
		assertEquals(2, actual.size());
	}

	@Test
	public void DFS_whenNotEmptyGraph_ReturnSetSubverticesInAmount2() {
		graph.addEdge(topVertexA, vertexA1);
		graph.addEdge(topVertexA, vertexA2);

		Set<String> actual = graph.DFS(topVertexA);

		assertTrue(actual.contains(vertexA1));
		assertTrue(actual.contains(vertexA2));
		assertEquals(2, actual.size());
	}

	@Test
	public void removeVertices_whenGraphNotEmpty_RetunrSize0() {
		graph.addEdge(topVertexA, vertexA1);
		graph.addEdge(topVertexB, vertexB1);
		graph.addEdge(topVertexB, vertexB2);
		assertFalse(graph.isEmpty());

		graph.removeVertices();

		assertEquals(0, graph.size());
		assertTrue(graph.isEmpty());
	}

	@Test
	public void removeVertex_whenAddTopAndSubvertex_ReturnSize1() {
		graph.addEdge(topVertexA, vertexA1);
		assertEquals(2, graph.size());

		graph.removeVertex(vertexA1);

		assertEquals(1, graph.size());
	}

}
