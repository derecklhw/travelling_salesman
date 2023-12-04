package travelling_salesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Represent a node in a graph
 */
class Node implements Comparable<Node> {
    int vertex;
    double weight;

    /**
     * Constructs a Node with a specified vertex index and weight.
     *
     * @param vertex The index of the vertex in the graph.
     * @param weight The weight (cost) associated with the vertex.
     */
    Node(int vertex, double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    /**
     * Compares the weight of this node with another node.
     *
     * @param other The other node to compare with.
     * @return A negative integer, zero, or a positive integer as this node is less
     *         than, equal to, or greater than the specified node.
     */
    @Override
    public int compareTo(Node other) {
        return Double.compare(this.weight, other.weight);
    }
}

public class SolverDijkstra {

    /**
     * Solves the Travelling Salesman Problem using Dijkstra's algorithm.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveDijkstra(ArrayList<City> cities) {
        int n = cities.size();
        double[][] shortestPaths = new double[n][n];

        // Calculate shortest path between all pairs of cities
        for (int i = 0; i < n; i++) {
            shortestPaths[i] = findShortestPathDijkstra(cities, i);
        }

        // Construct a TSP tour using the shortest paths
        return createTSPTourFromDijkstra(shortestPaths, cities);
    }

    /**
     * Implements Dijkstra's algorithm to find the shortest paths from a source city
     * to all other cities.
     * 
     * @param cities The list of cities representing the graph.
     * @param src    The index of the source city.
     * @return An array of distances from the source to each city.
     */
    private static double[] findShortestPathDijkstra(ArrayList<City> cities, int src) {
        int n = cities.size();
        double[] dist = new double[n];
        boolean[] visited = new boolean[n];

        // Initialize distances to all cities as infinity, except for the source city
        Arrays.fill(dist, Double.MAX_VALUE);
        dist[src] = 0;

        // Priority queue to select the city with the shortest distance next
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(src, 0));

        while (!pq.isEmpty()) {
            // Poll the city with the shortest distance from the priority queue
            Node node = pq.poll();
            int u = node.vertex;

            if (visited[u])
                continue;
            visited[u] = true;

            // Update the distance to each neighboring city
            for (int v = 0; v < n; v++) {
                if (!visited[v] && dist[u] + cities.get(u).distanceTo(cities.get(v)) < dist[v]) {
                    dist[v] = dist[u] + cities.get(u).distanceTo(cities.get(v));
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }

        return dist;
    }

    /**
     * Creates a TSP tour from the shortest paths.
     * 
     * @param shortestPaths The array of shortest paths.
     * @param cities        The list of cities.
     * @return The TSP tour.
     */
    private static ArrayList<City> createTSPTourFromDijkstra(double[][] shortestPaths, ArrayList<City> cities) {
        ArrayList<City> tour = new ArrayList<>();
        boolean[] visited = new boolean[cities.size()];
        int currentCity = 0; // Starting city index

        tour.add(cities.get(currentCity));
        visited[currentCity] = true;

        // Iteratively find the nearest unvisited city and add it to the tour
        for (int i = 1; i < cities.size(); i++) {
            double minDist = Double.MAX_VALUE;
            int nearestCityIndex = -1;

            // Search for the nearest unvisited city
            for (int j = 0; j < cities.size(); j++) {
                if (!visited[j] && shortestPaths[currentCity][j] < minDist) {
                    minDist = shortestPaths[currentCity][j];
                    nearestCityIndex = j;
                }
            }

            tour.add(cities.get(nearestCityIndex));
            visited[nearestCityIndex] = true;
            currentCity = nearestCityIndex;
        }

        // Return to the starting city to complete the tour
        tour.add(tour.get(0));
        return tour;
    }
}
