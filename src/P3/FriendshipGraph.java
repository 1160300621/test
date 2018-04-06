package P3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FriendshipGraph {
	private ArrayList<Person> people=new ArrayList<Person>();
	private int n=0;
	private int[][] G;
	private ArrayList<String> Name=new ArrayList<String>();
	private boolean flag=false;
	public FriendshipGraph() {
	}
	
	public int getn(){
		return n;
	}
	
	private void apply() {
		if (!flag) {
			G=new int[n][n];
			flag=true;
		}
	}
	
	public void addVertex(Person person) {
		people.add(person);
		person.setnum(n);
		for (int i=0;i<n;i++) {
			if (person.getname()==Name.get(i)) {
				System.err.println("存在相同的名字");
				System.exit(0);
			}
		}
		n++;
		Name.add(person.getname());
	}
	
	public int[][] getGraph(){
		return G;
	}
	
	public void addEdge(Person person1,Person person2) {
		apply();
		G[person1.getnum()][person2.getnum()]=1;
	}
	
	public int getDistance(Person person1,Person person2) {
		int x=person1.getnum();
		int y=person2.getnum();
		return BFS(x,y);
	}
	
	public ArrayList<Person> getpeople(){
		return people;
	}
	
	private int BFS(int x,int y) {
		Queue<Integer> queue = new LinkedList<Integer>();
		boolean[] visited=new boolean[n];
		int dis[]=new int[n];
		visited[x]=true;
		queue.add(x);
		int t;
		while (!queue.isEmpty())
		{
			t=queue.poll();
			for (int i=0;i<n;i++) {
				if (G[t][i]!=0&&!visited[i]) {
					queue.add(i);
					visited[i]=true;
					dis[i]=dis[t]+1;
				}
			}
			if (t==y)
				return dis[y];
			
		}
		return -1;
	}
	public static void main(String[] args) {
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
		graph.addEdge(c, d);
		graph.addEdge(d, e);
		graph.addEdge(c, e);
		graph.addEdge(b, c);
		System.out.println(graph.getDistance(a, c));
		System.out.println(graph.getDistance(a, d));
		System.out.println(graph.getDistance(c, d));
		System.out.println(graph.getDistance(c, e));
		
//		FriendshipGraph graph = new FriendshipGraph();
//		Person rachel = new Person("Rachel");
//		Person ross = new Person("Ross");
//		Person ben = new Person("Ben");
//		Person kramer = new Person("Kramer");
//		graph.addVertex(rachel);
//		graph.addVertex(ross);
//		graph.addVertex(ben);
//		graph.addVertex(kramer);
//		graph.addEdge(rachel, ross);
//		graph.addEdge(ross, rachel);
//		graph.addEdge(ross, ben);
//		graph.addEdge(ben, ross);
//		System.out.println(graph.getDistance(rachel, ross));
//		//should print 1
//		System.out.println(graph.getDistance(rachel, ben));
//		//should print 2
//		System.out.println(graph.getDistance(rachel, rachel));
//		//should print 0
//		System.out.println(graph.getDistance(rachel, kramer));
//		//should print -1
	}

}
