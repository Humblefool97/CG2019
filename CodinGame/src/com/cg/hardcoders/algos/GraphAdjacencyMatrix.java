package com.cg.hardcoders.algos;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


class DijkstraResult {
	public ArrayList<Integer> path;
	public int distance;
	public int destination;
	
	public DijkstraResult() {}
	public DijkstraResult(ArrayList<Integer> path, int distance, int dest) {
		this.path = path;
		this.distance = distance;
		this.destination = dest;
	}
}

class Graph {
	int [][] matrix;
	public boolean usesZeroNodes;
	public int noOfVertices;
	private static final int NO_PARENT = -1; 
	
	public Graph(int numberOfVertices, boolean usesZeroNodes) {
		this.noOfVertices = numberOfVertices;
		this.usesZeroNodes = usesZeroNodes;
		matrix = new int[numberOfVertices][numberOfVertices];
	}
	
	public void addEdge(int edge1, int edge2, int weight) {
		int normalizedEdge1 = usesZeroNodes ? edge1 : edge1 - 1;
		int normalizedEdge2 = usesZeroNodes ? edge2 : edge2 - 1;
		matrix[normalizedEdge1][normalizedEdge2] = weight;
		matrix[normalizedEdge2][normalizedEdge1] = weight;
	}
	
	public void printAdjacencyMatrix() {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j]);
			} 
			System.out.println();
		}
	}
	
	// BFS traversal
	public ArrayList<Integer> breadthFirstSearch(int source){
        boolean[] visited = new boolean[matrix.length];
        int selectedSource = usesZeroNodes ? source : source - 1;
        visited[selectedSource] = true;
        
        ArrayList<Integer> path = new ArrayList<Integer>();
        
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        System.out.println("The breadth first order is");
        while(!queue.isEmpty()){
            path.add(queue.peek());
            int x = queue.poll();
            int normalizedX = usesZeroNodes ? x : x - 1;
            int i;
            for(i = 0; i < matrix.length; i++){
                if(matrix[normalizedX][i] == 1 && visited[i] == false){
                    queue.add(usesZeroNodes ? i : i + 1);
                    visited[i] = true;
                }
            }
        }
        return path;
    }
	
	// DFS traversal
	public ArrayList<Integer> depthFirstSearch(int source) {
        boolean[] visited = new boolean[matrix.length];
        int selectedSource = usesZeroNodes ? source : source - 1;
        visited[selectedSource] = true;
        
        ArrayList<Integer> path = new ArrayList<Integer>();
        
        Stack<Integer> stack = new Stack<>();
        stack.push(source);
        path.add(source);
        int i,x;
        
        System.out.println("The depth first order is");
        
        while(!stack.isEmpty()){
            x = stack.pop();
            for(i = 0; i <matrix.length; i++){
            	int normalizedX = usesZeroNodes ? x : x - 1;
                if(matrix[normalizedX][i] == 1 && visited[i] == false){
                    stack.push(x);
                    visited[i] = true;
                    int normalizedI = usesZeroNodes ? i : i + 1;
                    path.add(normalizedI);
                    x = normalizedI;
                    i = -1;
                }
            }
        }
        return path;
    }
	
		// Dijkstra's Algorithm for shortest path
		// Function that implements Dijkstra's 
		// single source shortest path 
		// algorithm for a graph represented 
		// using adjacency matrix 
		// representation 
		public DijkstraResult dijkstra(int startVertex, int destVertex) { 
			int nVertices = matrix[0].length; 

			// shortestDistances[i] will hold the 
			// shortest distance from src to i 
			int[] shortestDistances = new int[nVertices]; 

			// added[i] will true if vertex i is 
			// included / in shortest path tree 
			// or shortest distance from src to 
			// i is finalized 
			boolean[] added = new boolean[nVertices]; 

			// Initialize all distances as 
			// INFINITE and added[] as false 
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) { 
				shortestDistances[vertexIndex] = Integer.MAX_VALUE; 
				added[vertexIndex] = false; 
			} 
			
			// Distance of source vertex from 
			// itself is always 0 
			shortestDistances[startVertex] = 0; 

			// Parent array to store shortest 
			// path tree 
			int[] parents = new int[nVertices]; 

			// The starting vertex does not 
			// have a parent 
			parents[startVertex] = NO_PARENT; 

			// Find shortest path for all 
			// vertices 
			for (int i = 1; i < nVertices; i++) { 

				// Pick the minimum distance vertex 
				// from the set of vertices not yet 
				// processed. nearestVertex is 
				// always equal to startNode in 
				// first iteration. 
				int nearestVertex = -1; 
				int shortestDistance = Integer.MAX_VALUE; 
				for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) { 
					if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) { 
						nearestVertex = vertexIndex; 
						shortestDistance = shortestDistances[vertexIndex]; 
					} 
				} 

				// Mark the picked vertex as 
				// processed 
				added[nearestVertex] = true; 

				// Update dist value of the 
				// adjacent vertices of the 
				// picked vertex. 
				for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) { 
					int edgeDistance = matrix[nearestVertex][vertexIndex]; 
					
					if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) { 
						parents[vertexIndex] = nearestVertex; 
						shortestDistances[vertexIndex] = shortestDistance + edgeDistance; 
					} 
				} 
			} 
			
			ArrayList<DijkstraResult> result = getSolution(startVertex, shortestDistances, parents); 
			DijkstraResult sol = new DijkstraResult();
			
			for(int i = 0; i < result.size(); i++) {
				DijkstraResult res = result.get(i);
				if(res.destination == destVertex) {
					sol = res;
				}
			}
			return sol;
		} 

		// A utility function to print 
		// the constructed distances 
		// array and shortest paths 
		private ArrayList<DijkstraResult> getSolution(int startVertex, int[] distances, int[] parents) { 
			int nVertices = distances.length; 
			ArrayList<DijkstraResult> result = new ArrayList<DijkstraResult>();
			System.out.print("Vertex\t Distance\tPath"); 
			
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) { 
				if (vertexIndex != startVertex) { 
					System.out.print("\n" + startVertex + " -> "); 
					System.out.print(vertexIndex + " \t\t "); 
					System.out.print(distances[vertexIndex] + "\t\t");
					DijkstraResult res = new DijkstraResult();
					res.distance = distances[vertexIndex];
					res.destination = vertexIndex;
					res.path = getPath(vertexIndex, parents, new ArrayList<Integer>());
					result.add(res);
				} 
			} 
			return result;
		} 

		// Function to get shortest path 
		// from source to currentVertex 
		// using parents array 
		private ArrayList<Integer> getPath(int currentVertex, int[] parents, ArrayList<Integer> path) { 
			
			// Base case : Source node has 
			// been processed 
			if (currentVertex == NO_PARENT) { 
				return path; 
			} 
			path = getPath(parents[currentVertex], parents, path); 
			System.out.print(currentVertex + " ");
			path.add(currentVertex);
			return path;
		} 
}

public class GraphAdjacencyMatrix {
	public static void printList(ArrayList<Integer> list) {
		for(int i = 0; i < list.size(); i++) {
			System.out.printf(" %d ", list.get(i));
		}
		System.out.println();
	}
	
	public static void main(String args []) {
		Graph g = new Graph(7, true);
		
		//Data for zero node
		g.addEdge(0, 1, 1);
		g.addEdge(0, 2, 1);
		g.addEdge(1, 3, 1);
		g.addEdge(2, 3, 1);
		g.addEdge(3, 4, 1);
		g.addEdge(3, 5, 1);
		g.addEdge(4, 6, 1);
		g.addEdge(5, 6, 1);
		
		//Data for testing Dijkstra
		g.addEdge(0, 3, 1);
		
		//Data for non-zero node
//		g.addEdge(1, 2, 1);
//		g.addEdge(1, 3, 1);
//		g.addEdge(2, 4, 1);
//		g.addEdge(3, 4, 1);
//		g.addEdge(4, 5, 1);
//		g.addEdge(4, 6, 1);
//		g.addEdge(5, 7, 1);
//		g.addEdge(6, 7, 1);
		
		g.printAdjacencyMatrix();
		
		printList(g.breadthFirstSearch(0));
		printList(g.depthFirstSearch(0));
		
		
		DijkstraResult res = g.dijkstra(0, 6);
		System.out.println("\nDistance from 0 to 6: " + res.distance);
		System.out.print("Path from 0 to 1 : ");
		for(int i=0; i < res.path.size(); i ++) {
			System.out.printf("%d ", res.path.get(i));
		}
	}
}
