package modele;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jdom2.JDOMException;

class Vertex implements Comparable<Vertex> {
	
	Noeud node;
	List<Edge> adjacencies;
	double minDistance;
	Vertex previous;
	
	//Constructor
	public Vertex(Noeud node) {
		this.node = node;
    	adjacencies = new ArrayList<Edge>();
    	minDistance = Double.POSITIVE_INFINITY;
    	previous = this;
	}
	// End of constructor
	
	public String toString() {
    	return "<Vertex " + node.getId() + ">";
    }
	
	public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
	
}

class Edge {
	
	Troncon arc;
	Vertex target;
	
	//Constructor
	public Edge(Troncon arc, Vertex target) {
		this.arc = arc;
		this.target = target;
	}
	// End of constructor
	
	public String toString() {
    	return "<Edge to " + target.toString() + ">";
    }
	
}

public class Dijkstra {
	
	private ZoneGeographique zone;
	private Tournee tour;
	private List<Vertex> vertices;
	private int[][] cost;
	//private int[][] deliveries;
	private int maxArcCost = Integer.MIN_VALUE;
	private int minArcCost = Integer.MAX_VALUE;
	
	public Dijkstra(ZoneGeographique zone) {
		this.zone = zone;
		this.tour = zone.getTournee();
		
		// Initialisation of vertices
		vertices = new ArrayList<Vertex>();
		List<Noeud> allNodes = zone.getNoeuds();
		for(Noeud node : allNodes) {
			vertices.add(new Vertex(node));
		}
		for(Vertex v : this.vertices) {
			List<Troncon> vArcs = v.node.getTronconsSortant();
			for(Troncon t : vArcs) {
				v.adjacencies.add(new Edge(t, getVertexByNodeId(t.getCible().getId())));
			}
		}
		//
		
		this.cost = new int[this.vertices.size()][this.vertices.size()];
		
		// Initialisation of cost
		for(Vertex vI : this.vertices) {
			for(int j = 0; j < cost[this.vertices.indexOf(vI)].length; j++) {
				cost[this.vertices.indexOf(vI)][j] = -1;
			}
			for(Edge e : vI.adjacencies) {
				cost[this.vertices.indexOf(vI)][this.vertices.indexOf(e.target)] = (int)e.arc.getCout();
			}
		}
		//
		
		this.computeCosts();
		
		// Initialisation of maxArcCost and minArcCost
		for(int i = 0; i < cost.length; i++) {
			for(int j = 0; j < cost[i].length; j++) {
				if(cost[i][j] > this.maxArcCost) {
					this.maxArcCost = cost[i][j];
				} else if (cost[i][j] > 0 && cost[i][j] < this.minArcCost) {
					this.minArcCost = cost[i][j];
				}
			}
		}
		
		
		
		
	}
	
	
	private Vertex getVertexByNodeId(int id) {
		for(Vertex v : vertices){
			if(id == v.node.getId())
				return v;
		}
		return null;
	}
	
	private int[][] computeCosts() {
		for(int i = 0; i < this.vertices.size(); i++) {
			for(Vertex v : this.vertices) {
				v.minDistance = Double.POSITIVE_INFINITY;
			}
			this.computePaths(this.vertices.get(i));
			for(int j = 0; j < this.vertices.size(); j++) {
				if(i == j) {
					this.cost[i][j] = 0;
				} else {
					int c = (int) this.vertices.get(j).minDistance;
					this.cost[i][j] = (c == Integer.MAX_VALUE ? -1 : c);
				}
			}
		}
		
		return this.cost;
	}
	
	// +computePaths :
	// No return value -> all the vertices connected to <source> vertex are visited and updated
	// At the end of this method, all the vertices point to their previous one in the minimal path from <source>
	// If v.previous == v, the vertex is an isolated vertex of the graph (at least, not reachable by <source>)
	// -> To get the minimal path from <source> to a vertex <target>, use getShortestPath(target), which returns a List<Vertex>
    public void computePaths(Vertex source) {
        // Minimum distance from <source> to <source> equals 0
    	source.minDistance = 0.0;
    	
    	// List of vertices which are not yet the ending of their path(s)
    	// i.e. they remain to be tested for the obtention of the minimal paths
        Queue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);
      	
		while (!vertexQueue.isEmpty()) {
	    	
	    	Vertex u = vertexQueue.poll();
	
	        // Visit each edge exiting u
	        for (Edge e : u.adjacencies) {
	        	Vertex v = e.target;
	            double weight = e.arc.getCout();
	            double distanceThroughUToV = u.minDistance + weight;
	            // IF passing through u gets a lower weighted path :
	            if (distanceThroughUToV < v.minDistance) {
				    // Removes the non-optimal state for vertex v..
	            	vertexQueue.remove(v);
				    // ..updates the minimal path to it..
				    v.minDistance = distanceThroughUToV ;
				    // .. and now, indicates that u is visited just before v.
				    v.previous = u;
				    // Finally, adds back the updated vertex into the queue
				    vertexQueue.add(v);
				}
	            // ELSE : none of the vertices reachable by u get a shorter path by passing through it
	            // -> end of the paths which have reached u grom the source : all the other adjacent vertices are reachable by a shorter way than through u
	        }
	        
	    }
    }
    
    private List<Vertex> getShortestPath(Vertex target)
    {
    	List<Vertex> path = new ArrayList<Vertex>();
    	
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
            if(vertex.minDistance == 0.0) {
            	break;
            }
            if(vertex.previous == vertex) {
            	return null;
            }
        }
        
        Collections.reverse(path);
        
        return path;
    }
	
    public int[][] getCost() {
		return cost;
	}

	public int getMaxArcCost() {
		return maxArcCost;
	}

	public int getMinArcCost() {
		return minArcCost;
	}
	
	public int getNbVertices() {
		return this.vertices.size();
	}

	public int getMinCost(int idNoeudSource, int idNoeudCible) {
		Vertex vSource = getVertexByNodeId(idNoeudSource);
		Vertex vCible = getVertexByNodeId(idNoeudCible);
		return this.cost[this.vertices.indexOf(vSource)][this.vertices.indexOf(vCible)];
	}

	public List<Troncon> getPath(Noeud nSource, Noeud nTarget) {
		List<Troncon> res = new ArrayList<Troncon>();
		
		Vertex source = new Vertex(nSource);
		this.computePaths(source);
		List<Vertex> path = this.getShortestPath(this.getVertexByNodeId(nTarget.getId()));
		List<Troncon> troncons = this.zone.getTroncons();
		
		if(path == null || path.size() == 1) {
			return null;
		}
		
		for(int i = 0; i < path.size()-1; i++) {
			for(Troncon t : troncons) {
				if(t.getSource() == path.get(i).node && t.getCible() == path.get(i+1).node) {
					res.add(t);
					break;
				}
			}
		}
		
		return res;
	}
	
	
}