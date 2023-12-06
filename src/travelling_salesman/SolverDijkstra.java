package travelling_salesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Utility class for solving the TSP using dijkstra algorithm.
 */
public class SolverDijkstra {

    /**
     * Solves the TSP using Dijkstra's algorithm.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveDijkstra(ArrayList<City> cities) {
        double[][] adjacencyMatrix = createAdjacencyMatrix(cities);
        ArrayList<City> tour = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        int currentCityIndex = 0;
        tour.add(cities.get(currentCityIndex));
        visited.add(currentCityIndex);

        for (int i = 1; i < cities.size(); i++) {
            double[] distances = findShortestDistance(adjacencyMatrix, currentCityIndex);
            int nextCityIndex = findNextCity(distances, visited);
            visited.add(nextCityIndex);
            tour.add(cities.get(nextCityIndex));
            currentCityIndex = nextCityIndex;
        }

        // Return to the starting city
        tour.add(cities.get(0));
        return tour;
    }

    /**
     * Creates an adjacency matrix representing the distances between each pair of
     * cities.
     *
     * @param cities The list of cities.
     * @return A 2D array representing the distances between each pair of cities.
     */
    public static double[][] createAdjacencyMatrix(ArrayList<City> cities) {
        double[][] matrix = new double[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = cities.get(i).distanceTo(cities.get(j));
                }
            }
        }
        return matrix;
    }

    /**
     * Finds the shortest distances from a source city to all other cities using
     * Dijkstra's algorithm.
     *
     * @param graph The graph represented as an adjacency matrix.
     * @param src   The index of the source city.
     * @return An array of distances from the source city to each other city.
     */
    private static double[] findShortestDistance(double[][] graph, int src) {
        int V = graph.length;
        double[] dist = new double[V];
        PriorityQueue<CityDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(CityDistance::getDistance));
        Map<Integer, CityDistance> cityDistances = new HashMap<>();

        // Initialize distances and priority queue
        for (int i = 0; i < V; i++) {
            CityDistance cd = new CityDistance(i, Double.MAX_VALUE);
            cityDistances.put(i, cd);
            pq.add(cd);
            dist[i] = Double.MAX_VALUE;
        }

        // Set the distance of the source city to itself as zero
        dist[src] = 0;
        cityDistances.get(src).setDistance(0);

        // Dijkstra's algorithm
        while (!pq.isEmpty()) {
            CityDistance cd = pq.poll();
            int u = cd.getCity();

            // Update distances to adjacent cities
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    CityDistance nextCd = cityDistances.get(v);
                    nextCd.setDistance(dist[v]);
                    pq.remove(nextCd); // Remove old entry
                    pq.add(nextCd); // Insert updated entry
                }
            }
        }

        return dist;
    }

    /**
     * Finds the index of the nearest unvisited city from the given distances array.
     *
     * @param distances The array of distances from the current city.
     * @param visited   The set of already visited cities.
     * @return The index of the nearest unvisited city.
     */
    private static int findNextCity(double[] distances, Set<Integer> visited) {
        double minDist = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < distances.length; i++) {
            if (!visited.contains(i) && distances[i] < minDist) {
                minDist = distances[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Utility class for storing a city and its distance from the current city.
     */
    private static class CityDistance {
        private int city;
        private double distance;

        /**
         * Constructs a CityDistance object.
         *
         * @param city     The city.
         * @param distance The distance from the current city.
         */
        public CityDistance(int city, double distance) {
            this.city = city;
            this.distance = distance;
        }

        /**
         * Gets the city.
         *
         * @return The city.
         */
        public int getCity() {
            return city;
        }

        /**
         * Sets the city.
         *
         * @param city The city.
         */
        public double getDistance() {
            return distance;
        }

        /**
         * Sets the distance from the current city.
         *
         * @param distance The distance from the current city.
         */
        public void setDistance(double distance) {
            this.distance = distance;
        }
    }

}
