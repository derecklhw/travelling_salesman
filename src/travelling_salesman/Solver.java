package travelling_salesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Utility class for solving the Travelling Salesman Problem.
 */
public class Solver {
    /**
     * Solves the Travelling Salesman Problem using the Nearest Neighbour
     * algorithm.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveNearestNeighbour(ArrayList<City> cities) {
        int n = cities.size();
        double[][] distanceMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    distanceMatrix[i][j] = cities.get(i).distanceTo(cities.get(j));
                }
            }
        }

        ArrayList<City> tour = new ArrayList<>();
        ArrayList<City> remainingCities = new ArrayList<>(cities);

        City currentCity = remainingCities.remove(0);
        tour.add(currentCity);

        while (!remainingCities.isEmpty()) {
            City nearestCity = findNearestCityNearestNeighbour(currentCity, remainingCities, distanceMatrix);
            tour.add(nearestCity);
            remainingCities.remove(nearestCity);
            currentCity = nearestCity;
        }

        tour.add(tour.get(0));
        return tour;
    }

    /**
     * Finds the nearest city to the current city using the Nearest Neighbour
     * algorithm.
     * 
     * @param currentCity    The current city.
     * @param cities         The list of cities to search.
     * @param distanceMatrix The distance matrix containing the distances between
     *                       each pair of cities.
     * @return The nearest city to the current city.
     */
    private static City findNearestCityNearestNeighbour(City currentCity, ArrayList<City> cities,
            double[][] distanceMatrix) {
        City nearestCity = null;
        double minDistance = Double.MAX_VALUE;
        int currentIndex = currentCity.getNumber() - 1;

        for (City city : cities) {
            double distance = distanceMatrix[currentIndex][city.getNumber() - 1];
            if (distance < minDistance) {
                minDistance = distance;
                nearestCity = city;
            }
        }
        return nearestCity;
    }

    /**
     * Solves the Travelling Salesman Problem using Dijkstra's algorithm.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveDijkstra(ArrayList<City> cities) {
        ArrayList<City> tour = new ArrayList<>();
        ArrayList<City> remainingCities = new ArrayList<>(cities);

        City currentCity = remainingCities.remove(0);
        tour.add(currentCity);

        while (!remainingCities.isEmpty()) {
            City nearestCity = findNearestCityDijkstra(currentCity, remainingCities);
            tour.add(nearestCity);
            remainingCities.remove(nearestCity);
            currentCity = nearestCity;
        }

        tour.add(tour.get(0));
        return tour;
    }

    /**
     * Finds the nearest city to the current city using Dijkstra's algorithm.
     * 
     * @param startCity The current city.
     * @param cities    The list of cities to search.
     * @return The nearest city to the current city.
     */
    private static City findNearestCityDijkstra(City startCity, ArrayList<City> cities) {
        City nearestCity = null;
        double minDistance = Double.MAX_VALUE;

        for (City city : cities) {
            double distance = shortestPathDijkstra(startCity, city, cities);
            if (distance < minDistance) {
                minDistance = distance;
                nearestCity = city;
            }
        }
        return nearestCity;
    }

    /**
     * Finds the shortest path between two cities using Dijkstra's algorithm.
     * 
     * @param startCity The start city.
     * @param endCity   The end city.
     * @param cities    The list of cities to search.
     * @return The shortest path between the two cities.
     */
    private static double shortestPathDijkstra(City startCity, City endCity, ArrayList<City> cities) {
        Map<City, Double> shortestPathMap = new HashMap<>();
        PriorityQueue<CityDistancePair> priorityQueue = new PriorityQueue<>(
                Comparator.comparing(CityDistancePair::getDistance));

        for (City city : cities) {
            shortestPathMap.put(city, Double.MAX_VALUE);
        }

        shortestPathMap.put(startCity, 0.0);
        priorityQueue.add(new CityDistancePair(startCity, 0.0));

        while (!priorityQueue.isEmpty()) {
            CityDistancePair pair = priorityQueue.poll();
            City currentCity = pair.getCity();

            if (currentCity.equals(endCity)) {
                return pair.getDistance();
            }

            for (City neighbour : cities) {
                if (!neighbour.equals(currentCity)) {
                    double distance = currentCity.distanceTo(neighbour);
                    double newDistance = shortestPathMap.get(currentCity) + distance;

                    if (newDistance < shortestPathMap.get(neighbour)) {
                        shortestPathMap.put(neighbour, newDistance);
                        priorityQueue.add(new CityDistancePair(neighbour, newDistance));
                    }
                }
            }
        }

        return shortestPathMap.get(endCity);
    }

    /**
     * Represents a pair of a city and its distance from another city.
     */
    private static class CityDistancePair {
        private City city;
        private double distance;

        /**
         * Creates a new CityDistancePair.
         * 
         * @param city     The city.
         * @param distance The distance from another city.
         */
        public CityDistancePair(City city, double distance) {
            this.city = city;
            this.distance = distance;
        }

        /**
         * Returns the city.
         * 
         * @return The city.
         */
        public City getCity() {
            return city;
        }

        /**
         * Returns the distance from another city.
         * 
         * @return The distance from another city.
         */
        public double getDistance() {
            return distance;
        }
    }

    /**
     * Solves the Travelling Salesman Problem using Prim's algorithm.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveMST(ArrayList<City> cities) {
        int n = cities.size();
        double[][] graph = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue;
                graph[i][j] = cities.get(i).distanceTo(cities.get(j));
            }
        }

        boolean[] visited = new boolean[n];
        double[] key = new double[n];
        int[] parent = new int[n];
        PriorityQueue<CityEdge> pq = new PriorityQueue<>(Comparator.comparingDouble(CityEdge::getWeight));

        for (int i = 0; i < n; i++) {
            key[i] = Double.MAX_VALUE;
            parent[i] = -1;
        }
        key[0] = 0;
        pq.offer(new CityEdge(0, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().getDest();
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] < key[v]) {
                    key[v] = graph[u][v];
                    pq.offer(new CityEdge(v, key[v]));
                    parent[v] = u;
                }
            }
        }

        ArrayList<City> tour = new ArrayList<>();
        Set<Integer> visitedCities = new HashSet<>();
        dfsMST(0, parent, visitedCities, cities, tour);

        tour.add(tour.get(0));
        return tour;
    }

    /**
     * Performs a depth-first search on the minimum spanning tree to find a tour.
     * 
     * @param current       The current city.
     * @param parent        The parent array of the minimum spanning tree.
     * @param visitedCities The set of visited cities.
     * @param cities        The list of cities.
     * @param tour          The tour.
     */
    private static void dfsMST(int current, int[] parent, Set<Integer> visitedCities, ArrayList<City> cities,
            ArrayList<City> tour) {
        visitedCities.add(current);
        tour.add(cities.get(current));

        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == current && !visitedCities.contains(i)) {
                dfsMST(i, parent, visitedCities, cities, tour);
            }
        }
    }

    /**
     * Represents an edge between two cities.
     */
    private static class CityEdge {
        private int dest;
        private double weight;

        /**
         * Creates a new CityEdge.
         * 
         * @param dest   The destination city.
         * @param weight The weight of the edge.
         */
        public CityEdge(int dest, double weight) {
            this.dest = dest;
            this.weight = weight;
        }

        /**
         * Returns the destination city.
         * 
         * @return The destination city.
         */
        public int getDest() {
            return dest;
        }

        /**
         * Returns the weight of the edge.
         * 
         * @return The weight of the edge.
         */
        public double getWeight() {
            return weight;
        }
    }
}