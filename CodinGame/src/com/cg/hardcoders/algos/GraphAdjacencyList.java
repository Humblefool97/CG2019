package com.cg.hardcoders.algos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


class Graph2 { 
	private int V; // No. of vertices 
	private LinkedList<Integer> adj[]; //Adjacency Lists 

	// Constructor 
	Graph2(int v) { 
		V = v; 
		adj = new LinkedList[v]; 
		for (int i=0; i<v; ++i) 
			adj[i] = new LinkedList(); 
	} 

	// Function to add an edge into the graph 
	void addEdge(int v,int w) { 
		adj[v].add(w); 
		
//		adj[w].add(v);
	} 

	// BFS traversal from a given source s 
	public ArrayList<Integer> BFS(int s) { 
		// Mark all the vertices as not visited(By default 
		// set as false) 
		boolean visited[] = new boolean[V]; 

		// Create a queue for BFS 
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		ArrayList<Integer> path = new ArrayList<Integer>();

		// Mark the current node as visited and enqueue it 
		visited[s]=true; 
		queue.add(s); 

		while (queue.size() != 0) { 
			// Dequeue a vertex from queue and print it 
			s = queue.poll();
			path.add(s);

			// Get all adjacent vertices of the dequeued vertex s 
			// If a adjacent has not been visited, then mark it 
			// visited and enqueue it 
			Iterator<Integer> i = adj[s].listIterator(); 
			while (i.hasNext()) { 
				int n = i.next(); 
				if (!visited[n]) { 
					visited[n] = true; 
					queue.add(n); 
				} 
			} 
		} 
		return path;
	}
	
	// A function used by DFS 
    public ArrayList<Integer> DFSUtil(int v,boolean visited[], ArrayList<Integer> path) { 
        // Mark the current node as visited and print it 
        visited[v] = true; 
        path.add(v);
  
        // Recur for all the vertices adjacent to this vertex 
        Iterator<Integer> i = adj[v].listIterator(); 
        while (i.hasNext()) { 
            int n = i.next(); 
            if (!visited[n]) 
                path = DFSUtil(n, visited, path); 
        } 
        return path;
    } 
  
    // The function to do DFS traversal. It uses recursive DFSUtil() 
    public ArrayList<Integer> DFS(int v) { 
        // Mark all the vertices as not visited(set as 
        // false by default in java) 
        boolean visited[] = new boolean[V]; 
  
        // Call the recursive helper function to print DFS traversal 
        return DFSUtil(v, visited, new ArrayList<Integer>()); 
    } 
}

public class GraphAdjacencyList {
	public static void printList(ArrayList<Integer> list) {
		for(int i = 0; i < list.size(); i++) {
			System.out.printf(" %d ", list.get(i));
		}
		System.out.println();
	}
	
	public static void main(String args[]) {
		Graph2 g = new Graph2(4);
		
		//Data for zero node
//		g.addEdge(0, 1);
//		g.addEdge(0, 2);
//		g.addEdge(1, 3);
//		g.addEdge(2, 3);
//		g.addEdge(3, 4);
//		g.addEdge(3, 5);
//		g.addEdge(4, 6);
//		g.addEdge(5, 6);
		
		g.addEdge(0, 1); 
		g.addEdge(0, 2); 
		g.addEdge(1, 2); 
		g.addEdge(2, 0); 
		g.addEdge(2, 3); 
		g.addEdge(3, 3);
		
		//Data for testing Dijkstra
//		g.addEdge(0, 3);
		
		printList(g.BFS(2));
		printList(g.DFS(2));
	}
}
