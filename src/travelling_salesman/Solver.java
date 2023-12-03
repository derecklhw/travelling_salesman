package travelling_salesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Solver {
    public static ArrayList<City> solveNearestNeighbour(ArrayList<City> cities) {
        ArrayList<City> tour = new ArrayList<>();
        ArrayList<City> remainingCities = new ArrayList<>(cities);

        City currentCity = remainingCities.remove(0);
        tour.add(currentCity);

        while (!remainingCities.isEmpty()) {
            City nearestCity = findNearestCityNearestNeighbour(currentCity, remainingCities);
            tour.add(nearestCity);
            remainingCities.remove(nearestCity);
            currentCity = nearestCity;
        }

        tour.add(tour.get(0));
        return tour;
    }

    private static City findNearestCityNearestNeighbour(City currentCity, ArrayList<City> cities) {
        City nearestCity = null;
        double minDistance = Double.MAX_VALUE;
        for (City city : cities) {
            double distance = currentCity.distanceTo(city);
            if (distance < minDistance) {
                minDistance = distance;
                nearestCity = city;
            }
        }
        return nearestCity;
    }

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

    // Helper class to pair cities with their distances
    private static class CityDistancePair {
        private City city;
        private double distance;

        public CityDistancePair(City city, double distance) {
            this.city = city;
            this.distance = distance;
        }

        public City getCity() {
            return city;
        }

        public double getDistance() {
            return distance;
        }
    }

    public static ArrayList<City> solveMST(ArrayList<City> cities) {
        // Create a graph from the cities
        int n = cities.size();
        double[][] graph = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue;
                graph[i][j] = cities.get(i).distanceTo(cities.get(j));
            }
        }

        // Prim's algorithm to find MST
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

        // Depth-First Search (DFS) traversal of the MST to create the tour
        ArrayList<City> tour = new ArrayList<>();
        Set<Integer> visitedCities = new HashSet<>();
        dfsMST(0, parent, visitedCities, cities, tour);

        tour.add(tour.get(0)); // Add the start city at the end to complete the tour
        return tour;
    }

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

    // Helper class for edges in the graph
    private static class CityEdge {
        private int dest;
        private double weight;

        public CityEdge(int dest, double weight) {
            this.dest = dest;
            this.weight = weight;
        }

        public int getDest() {
            return dest;
        }

        public double getWeight() {
            return weight;
        }
    }
}