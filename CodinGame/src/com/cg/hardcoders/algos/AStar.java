package com.cg.hardcoders.algos;

import java.util.ArrayList;
import java.util.Stack;

public class AStar {
	public static class Pair {
		public int x;
		public int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static class Pair2 {
		public double f;
		public Pair pair;
		
		public Pair2(double f, Pair pair) {
			this.f = f;
			this.pair = pair;
		}
	}
	
	public static class Cell {
		public int parent_i;
		public int parent_j;
		public double f;
		public double g;
		public double h;
		
		public Cell(int par_i, int par_j, double g, double h) {
			this.parent_i = par_i;
			this.parent_j = par_j;
			this.g = g;
			this.h = h;
			this.f = g + h;
		}
	}
	
	public static boolean isValid(int row, int col, int width, int height) {
		return row >= 0 && row < height && col >= 0 && col < width; 
	}
	
	public static boolean isUnBlocked(char grid[][], int row, int col) { 
	    return grid[row][col] == '1'; 
	}
	
	public static boolean isDestination(int row, int col, Pair dest) { 
	    return row == dest.x && col == dest.y;
	} 
	
	public static double calculateHValue(int row, int col, Pair dest) { 
	   // Return using Manhattan heuristic
	   return Math.abs(row - dest.x) + Math.abs(col - dest.y);
	} 
	
	// A Utility Function to trace the path from the source 
	// to destination 
	public static void tracePath(Cell cellDetails[][], Pair dest) { 
	    System.out.println("\nThe Path is "); 
	    int row = dest.x; 
	    int col = dest.y; 
	  
	    Stack<Pair> path = new Stack<Pair>(); 
	  
	    while (!(cellDetails[row][col].parent_i == row && cellDetails[row][col].parent_j == col )) { 
	        path.add(new Pair(row, col));
	        int temp_row = cellDetails[row][col].parent_i; 
	        int temp_col = cellDetails[row][col].parent_j; 
	        row = temp_row; 
	        col = temp_col; 
	    } 
	    
	    path.add(new Pair(row, col));
	    
	    while (!path.empty()) { 
	        Pair p = path.lastElement(); 
	        path.pop(); 
	        System.out.printf("-> (%d, %d) ", p.x, p.y);
	    } 
	    return; 
	}
	
	public static boolean aStarSearch(char grid[][], int width, int height, Pair source, Pair dest, boolean printPath) {
		// If the source is out of range 
	    if (!isValid(source.x, source.y, width, height)) { 
	        return false; 
	    } 
	  
	    // If the destination is out of range 
	    if (!isValid(dest.x, dest.y, width, height)) { 
	        return false; 
	    } 
	  
	    // Either the source or the destination is blocked 
	    if (!isUnBlocked(grid, source.x, source.y) || !isUnBlocked(grid, dest.x, dest.y)) {
	        return false; 
	    } 
	  
	    // If the destination cell is the same as source cell 
	    if (isDestination(source.x, source.y, dest) == true) {
	        return false; 
	    } 
	    
	    // Create a closed list and initialize it to false which means 
	    // that no cell has been included yet 
	    // This closed list is implemented as a boolean 2D array 
	    boolean closedList[][] = new boolean[height][width]; 
	    
	    // Declare a 2D array of structure to hold the details 
	    //of that cell 
	    Cell cellDetails [][] = new Cell[height][width];
	    
	    int i,j;
	    
	    for (i = 0; i < height; i++) { 
	        for (j = 0; j < width; j++) {
	        	cellDetails[i][j] = new Cell(-1,-1, 0.0, 0.0);
	            cellDetails[i][j].f = Double.MAX_VALUE; 
	            cellDetails[i][j].g = Double.MAX_VALUE; 
	            cellDetails[i][j].h = Double.MAX_VALUE; 
	            cellDetails[i][j].parent_i = -1; 
	            cellDetails[i][j].parent_j = -1; 
	        } 
	    } 
	  
	    // Initializing the parameters of the starting node 
	    i = source.x;
	    j = source.y; 
	    cellDetails[i][j].f = 0.0; 
	    cellDetails[i][j].g = 0.0; 
	    cellDetails[i][j].h = 0.0; 
	    cellDetails[i][j].parent_i = i; 
	    cellDetails[i][j].parent_j = j; 
	    
	    /* 
	     Create an open list having information as- 
	     <f, <i, j>> 
	     where f = g + h, 
	     and i, j are the row and column index of that cell 
	     Note that 0 <= i <= ROW-1 & 0 <= j <= COL-1 
	     This open list is implemented as a set of pair of pair.
	     */
	    ArrayList<Pair2> openList = new ArrayList<Pair2>();
	  
	    // Put the starting cell on the open list and set its 
	    // 'f' as 0 
	    openList.add(new Pair2(0.0, new Pair(i,j)));
	  
	    // We set this boolean value as false as initially 
	    // the destination is not reached. 
	    boolean foundDest = false; 
	    
	    
	    while(!openList.isEmpty()) {
	    	Pair2 p = openList.get(0);
	    	
	    	openList.remove(0);
	    	
	    	 // Add this vertex to the closed list 
	        i = p.pair.x; 
	        j = p.pair.y; 
	        closedList[i][j] = true;
	        
	        /* 
	        Generating all the 4 successor of this cell 
	  
	               	  N   
	                  |    
	                  |   
	            W----Cell----E 
	                  |  
	                  |   
	                  S    
	  
	        Cell-->Popped Cell (i, j) 
	        N -->  North       (i-1, j) 
	        S -->  South       (i+1, j) 
	        E -->  East        (i, j+1) 
	        W -->  West           (i, j-1) 
	        */
	  
	        // To store the 'g', 'h' and 'f' of the 4 successors 
	        double gNew, hNew, fNew; 
	        
	        //----------- 1st Successor (North) ------------ 
	        
	        // Only process this cell if this is a valid one 
	        if (isValid(i-1, j, width, height)) {
	        	
	        	// If the destination cell is the same as the 
	            // current successor 
	            if (isDestination(i-1, j, dest)) { 
	                // Set the Parent of the destination cell 
	                cellDetails[i-1][j].parent_i = i; 
	                cellDetails[i-1][j].parent_j = j; 
	                System.out.println("The destination cell is found\n"); 
	                foundDest = true;
	                if(printPath) {
	                	tracePath(cellDetails, dest);
	                }
	                return foundDest; 
	            } 
	            // If the successor is already on the closed 
	            // list or if it is blocked, then ignore it. 
	            // Else do the following 
	            else if (!closedList[i-1][j] && isUnBlocked(grid, i-1, j)) { 
	                gNew = cellDetails[i][j].g + 1.0; 
	                hNew = calculateHValue (i-1, j, dest); 
	                fNew = gNew + hNew; 
	  
	                // If it isn’t on the open list, add it to 
	                // the open list. Make the current square 
	                // the parent of this square. Record the 
	                // f, g, and h costs of the square cell 
	                //                OR 
	                // If it is on the open list already, check 
	                // to see if this path to that square is better, 
	                // using 'f' cost as the measure. 
	                if (cellDetails[i-1][j].f == Double.MAX_VALUE || cellDetails[i-1][j].f > fNew) { 
	                    
	                    openList.add(new Pair2(fNew, new Pair(i-1,j)));
	  
	                    // Update the details of this cell 
	                    cellDetails[i-1][j].f = fNew; 
	                    cellDetails[i-1][j].g = gNew; 
	                    cellDetails[i-1][j].h = hNew; 
	                    cellDetails[i-1][j].parent_i = i; 
	                    cellDetails[i-1][j].parent_j = j; 
	                } 
	            } 
	        }
	        
	        //----------- 2nd Successor (South) ------------ 
	        
	        // Only process this cell if this is a valid one 
	        if (isValid(i+1, j, width, height)) { 
	            // If the destination cell is the same as the 
	            // current successor 
	            if (isDestination(i+1, j, dest)) { 
	                // Set the Parent of the destination cell 
	                cellDetails[i+1][j].parent_i = i; 
	                cellDetails[i+1][j].parent_j = j; 
	                System.out.println("The destination cell is found\n");
	                foundDest = true; 
	                if(printPath) {
	                	tracePath(cellDetails, dest);
	                }
	                return foundDest; 
	            } 
	            // If the successor is already on the closed 
	            // list or if it is blocked, then ignore it. 
	            // Else do the following 
	            else if (!closedList[i+1][j] && isUnBlocked(grid, i+1, j)) { 
	                gNew = cellDetails[i][j].g + 1.0; 
	                hNew = calculateHValue(i+1, j, dest); 
	                fNew = gNew + hNew; 
	  
	                // If it isn’t on the open list, add it to 
	                // the open list. Make the current square 
	                // the parent of this square. Record the 
	                // f, g, and h costs of the square cell 
	                //                OR 
	                // If it is on the open list already, check 
	                // to see if this path to that square is better, 
	                // using 'f' cost as the measure. 
	                if (cellDetails[i+1][j].f == Double.MAX_VALUE || cellDetails[i+1][j].f > fNew) { 
	              
	                    openList.add(new Pair2(fNew, new Pair(i+1,j)));
	                    
	                    // Update the details of this cell 
	                    cellDetails[i+1][j].f = fNew; 
	                    cellDetails[i+1][j].g = gNew; 
	                    cellDetails[i+1][j].h = hNew; 
	                    cellDetails[i+1][j].parent_i = i; 
	                    cellDetails[i+1][j].parent_j = j; 
	                } 
	            } 
	        } 
	        
	        //----------- 3rd Successor (East) ------------ 
	        
	        // Only process this cell if this is a valid one 
	        if (isValid (i, j+1, width, height)) { 
	            // If the destination cell is the same as the 
	            // current successor 
	            if (isDestination(i, j+1, dest)) { 
	                // Set the Parent of the destination cell 
	                cellDetails[i][j+1].parent_i = i; 
	                cellDetails[i][j+1].parent_j = j; 
	                System.out.println("The destination cell is found\n"); 
	                foundDest = true; 
	                if(printPath) {
	                	tracePath(cellDetails, dest);
	                }
	                return foundDest; 
	            } 
	  
	            // If the successor is already on the closed 
	            // list or if it is blocked, then ignore it. 
	            // Else do the following 
	            else if (!closedList[i][j+1] && isUnBlocked (grid, i, j+1)) { 
	                gNew = cellDetails[i][j].g + 1.0; 
	                hNew = calculateHValue (i, j+1, dest); 
	                fNew = gNew + hNew; 
	  
	                // If it isn’t on the open list, add it to 
	                // the open list. Make the current square 
	                // the parent of this square. Record the 
	                // f, g, and h costs of the square cell 
	                //                OR 
	                // If it is on the open list already, check 
	                // to see if this path to that square is better, 
	                // using 'f' cost as the measure. 
	                if (cellDetails[i][j+1].f == Double.MAX_VALUE || cellDetails[i][j+1].f > fNew) {  
	                    
	                    openList.add(new Pair2(fNew, new Pair(i,j+1)));
	                    
	                    // Update the details of this cell 
	                    cellDetails[i][j+1].f = fNew; 
	                    cellDetails[i][j+1].g = gNew; 
	                    cellDetails[i][j+1].h = hNew; 
	                    cellDetails[i][j+1].parent_i = i; 
	                    cellDetails[i][j+1].parent_j = j; 
	                } 
	            } 
	        } 
	        
	        //----------- 4th Successor (West) ------------ 
	        
	        // Only process this cell if this is a valid one 
	        if (isValid(i, j-1, width, height)) { 
	            // If the destination cell is the same as the 
	            // current successor 
	            if (isDestination(i, j-1, dest)) { 
	                // Set the Parent of the destination cell 
	                cellDetails[i][j-1].parent_i = i; 
	                cellDetails[i][j-1].parent_j = j; 
	                System.out.println("The destination cell is found\n"); 
	                foundDest = true; 
	                if(printPath) {
	                	tracePath(cellDetails, dest);
	                }
	                return foundDest; 
	            } 
	  
	            // If the successor is already on the closed 
	            // list or if it is blocked, then ignore it. 
	            // Else do the following 
	            else if (!closedList[i][j-1] && isUnBlocked(grid, i, j-1)) { 
	                gNew = cellDetails[i][j].g + 1.0; 
	                hNew = calculateHValue(i, j-1, dest); 
	                fNew = gNew + hNew; 
	  
	                // If it isn’t on the open list, add it to 
	                // the open list. Make the current square 
	                // the parent of this square. Record the 
	                // f, g, and h costs of the square cell 
	                //                OR 
	                // If it is on the open list already, check 
	                // to see if this path to that square is better, 
	                // using 'f' cost as the measure. 
	                if (cellDetails[i][j-1].f == Double.MAX_VALUE || cellDetails[i][j-1].f > fNew) { 
	                    
	                    openList.add(new Pair2(fNew, new Pair(i,j-1)));
	                    
	                    // Update the details of this cell 
	                    cellDetails[i][j-1].f = fNew; 
	                    cellDetails[i][j-1].g = gNew; 
	                    cellDetails[i][j-1].h = hNew; 
	                    cellDetails[i][j-1].parent_i = i; 
	                    cellDetails[i][j-1].parent_j = j; 
	                } 
	            } 
	        }
	    }
	    return foundDest;
	}
	
	public static void main(String[] args) {
		char matrix[][] = {	
							{ '1', '0', '1', '1', '1', '1', '0', '1', '1', '1' }, 
							{ '1', '1', '1', '0', '1', '1', '1', '0', '1', '1' }, 
							{ '1', '1', '1', '0', '1', '1', '0', '1', '0', '1' }, 
							{ '0', '0', '1', '0', '1', '0', '0', '0', '0', '1' }, 
							{ '1', '0', '1', '0', '1', '1', '1', '0', '1', '0' }, 
							{ '1', '0', '1', '1', '1', '1', '0', '1', '0', '0' }, 
							{ '1', '0', '0', '0', '0', '1', '0', '0', '0', '1' },
							{ '1', '0', '1', '1', '1', '1', '0', '1', '1', '1' }, 
							{ '1', '1', '1', '0', '0', '0', '1', '0', '0', '1' }
			  			  };
		
		Pair source = new Pair(8, 9);
		
		int dist = Integer.MAX_VALUE;
		Pair shortestNode = new Pair(-1,-1);
		for(int i = 0; i < 9; i ++) {
			for(int j = 0; j < 10; j++) {
				int dis = Math.min(dist, Math.abs(source.x - i) + Math.abs(source.y - j));
				if(dis < dist && matrix[i][j] == '1' && (i !=source.x || j != source.y) && aStarSearch(matrix, 10, 9, source, new Pair(i,j), false)) {
					dist = dis;
					shortestNode.x=i;
					shortestNode.y=j;
				}
			}
		}
		System.out.printf("Nearest node: (%d, %d)", shortestNode.x, shortestNode.y);
		//boolean isFound = aStarSearch(matrix, 10, 9, source, dest, false);
		//System.out.println("Is Path available: " +isFound);
	}
}
