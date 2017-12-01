import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anthony Panisales
 */

public class GraphAdjMatrix implements Graph {
	
	int[][] edges;
	
	public GraphAdjMatrix(int vertices) {
		edges = new int[vertices][vertices];
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				edges[i][j] = -1;
			}
		}
	}

	//For PA 8
	public void addEdge(int v1, int v2) {
		edges[v1][v2] = v2;
	}
	
	public void addEdge(int v1, int v2, int weight) {
		edges[v1][v2] = weight;
		edges[v2][v1] = weight;
	}
	
	public int getEdge(int v1, int v2) {
		return edges[v1][v2];
	}
	
	public int createSpanningTree() {
		Boolean[] known = new Boolean[edges.length];
		int[] dist = new int[edges.length];
		int[] path = new int[edges.length];
		Arrays.fill(known, false);
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(path, Integer.MAX_VALUE);
		
		int v = 0;
		List<Boolean> knownList = new ArrayList<Boolean>(Arrays.asList(known));
		while (knownList.contains(false)) {
			known[v] = true;
			ArrayList<Integer> arr = neighbors(v);
			if (arr.size() == 0) {
				v = (v + 1) % dist.length;
			} else {
				Integer neighbor;
				for (int i = 0; i < arr.size(); i++) {
					neighbor = (Integer) arr.get(i);
					if (dist[neighbor] > edges[v][neighbor]) {
						dist[neighbor] = edges[v][neighbor];
						path[neighbor] = v;
					}
					
				}
				v = lowestDistV(dist, known); //Find neighbor with lowest distance
			}
			knownList = new ArrayList<Boolean>(Arrays.asList(known));
		}
		int weight = 0;
		for (int i = 0; i < dist.length; i++) {
			if (path[i] != Integer.MAX_VALUE) {
				if (i == path[path[i]] ) {
					if (i < path[i])
						weight += dist[i];
				} else {
					weight += dist[i];
				}
			}
		}
		return weight;
	}
	
	public int lowestDistV(int[] dist, Boolean[] known) {
		int min = Integer.MAX_VALUE;
		int minIndex = 0;
		for (int i = 0; i < dist.length; i++) {
			if (dist[i] < min && known[i] == false) {
				min = dist[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public ArrayList<Integer> neighbors(int vertex) {
		ArrayList<Integer> nbors = new ArrayList<Integer>();
		for (int i = 0; i < edges.length; i++) {
			if (edges[vertex][i] != -1) {
				nbors.add(i);
			}
		}
		return nbors;
	}
	
	//From PA 8
	public int indegree(int vertex) {
		int indegrees = 0;
		for (int i = 0; i < edges.length; i++) {
			if (edges[i][vertex] != -1)
				indegrees++;
		}
		return indegrees;
	}
	
	//From PA 8
	public int findZero(int[] incident) {
		for (int i = 0; i < incident.length; i++) {
			if (incident[i] == 0)
				return i;
		}
		return -1;
	}
	
	//From PA 8
	public void topologicalSort() {
		System.out.print("Topological Sort: ");
		int[] incident = new int[edges.length];
		for (int i = 0; i < edges.length; i++)
			incident[i] = indegree(i);
		ArrayQueue sequence = new ArrayQueue();
		for (int i = 0; i < incident.length; i++) {
			int vertex = findZero(incident);
			if (vertex != -1) {
				sequence.enqueue(vertex);
				for (int j = 0; j < edges.length; j++) {
					if (edges[vertex][j] != -1)
						incident[j]--;
				}
				incident[vertex] = -1;
			} else {
				System.out.println("Topological sort not possible because of a cycle");
				System.out.print("Partial solution: ");
				break;
			}
		}
		while(!sequence.empty())
			System.out.print(sequence.dequeue() + " ");
		System.out.println("");
	}
	
}