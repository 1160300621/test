

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import P3.FriendshipGraph;
import P3.Person;

public class FriendshipGraphTest {

	@BeforeEach
	void setUp() throws Exception {
	}


	@Test
	void testAddVertex() {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("a");
		Person b = new Person("b");
		Person c = new Person("c");
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		ArrayList<Person> l=new ArrayList<Person>();
		l.add(a);
		l.add(b);
		l.add(c);
        assertEquals(l,graph.getpeople());
        Person d=new Person("d");
		graph.addVertex(d);
        l.add(d);
        assertEquals(l,graph.getpeople());
	}

	@Test
	void testAddEdge() {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("a");
		Person b = new Person("b");
		Person c = new Person("c");
        Person d = new Person("d");
        
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);

		graph.addEdge(a, b);
		graph.addEdge(b, a);
		graph.addEdge(a, c);
		graph.addEdge(c, a);
		
		int[][] G=new int[4][4];
		G[0][1]=1;
		G[1][0]=1;
		G[0][2]=1;
		G[2][0]=1;
		
        extracted(graph, G);
        
        graph.addEdge(d, c);
		graph.addEdge(c, d);
		G[2][3]=1;
		G[3][2]=1;
		
		extracted(graph, G);
	}


	private void extracted(FriendshipGraph graph, int[][] G) {
		int[][] G2=graph.getGraph();
		for (int i=0;i<graph.getn();i++)
			for (int j=0;j<graph.getn();j++)
				assertEquals(G2[i][j],G[i][j]);
	}


	@Test
	void testGetDistance() {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("a");
		Person b = new Person("b");
		Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		
		graph.addEdge(a, b);
		graph.addEdge(b, a);
		graph.addEdge(b, c);
		graph.addEdge(c, b);
		graph.addEdge(c, d);
		graph.addEdge(d, c);
		graph.addEdge(d, e);
		graph.addEdge(e, d);
		
		assertEquals(4, graph.getDistance(a, e));
		assertEquals(1, graph.getDistance(c, d));
		assertEquals(2, graph.getDistance(b, d));

	}

}
