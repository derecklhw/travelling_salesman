package travelling_salesman;

import java.util.ArrayList;
import java.util.Arrays;

public class SolverMST {

    /**
     * Solves the Travelling Salesman Problem using Prim's algorithm.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveMST(ArrayList<City> cities) {
        int n = cities.size();
        double[][] adjacencyMatrix = createAdjacencyMatrix(cities, n);

        // Track vertices included in MST
        boolean[] inMST = new boolean[n];

        // Store MST
        int[] parent = new int[n];

        // Key values used to pick minimum weight edge in cut
        double[] key = new double[n];
        Arrays.fill(key, Double.MAX_VALUE);

        // Include first 1st vertex in MST
        key[0] = 0;

        // First node is always root of MST
        parent[0] = -1;

        for (int count = 0; count < n - 1; count++) {

            // Pick the minimum key vertex from the set of vertices not yet included in MST
            int u = minKey(key, inMST);

            // Add the picked vertex to the MST
            inMST[u] = true;

            // Update key value and parent index of the adjacent vertices of the picked
            // vertex.
            for (int v = 0; v < n; v++) {
                // Update the key only if adjacencyMatrix[u][v] is smaller than key[v]
                if (adjacencyMatrix[u][v] != 0 && !inMST[v] && adjacencyMatrix[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = adjacencyMatrix[u][v];
                }
            }
        }

        return createTSPTourFromMST(parent, cities);
    }

    /**
     * Finds the index of the city with the minimum key value that has not been
     * included in the MST yet.
     * 
     * @param key   The array of key values for each city.
     * @param inMST The array indicating whether a city is included in MST.
     * @return The index of the city with the minimum key value.
     */
    private static int minKey(double[] key, boolean[] inMST) {
        double min = Double.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < key.length; v++) {

            // If vertex is not in MST and key is less than min
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    /**
     * Creates an adjacency matrix representing the graph of cities.
     *
     * @param cities The list of cities.
     * @param n      The number of cities.
     * @return The adjacency matrix representing distances between cities.
     */
    private static double[][] createAdjacencyMatrix(ArrayList<City> cities, int n) {
        double[][] adjacencyMatrix = new double[n][n];

        // Construct a symmetric matrix with distances between each pair of cities
        for (int i = 0; i < n; i++) {
            City city1 = cities.get(i);
            for (int j = 0; j < n; j++) {
                City city2 = cities.get(j);
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    adjacencyMatrix[i][j] = city1.distanceTo(city2);
                }
            }
        }

        return adjacencyMatrix;
    }

    /**
     * Creates a TSP tour from the Minimum Spanning Tree.
     *
     * @param parent The array representing the MST.
     * @param cities The list of cities.
     * @return The TSP tour.
     */
    private static ArrayList<City> createTSPTourFromMST(int[] parent, ArrayList<City> cities) {
        ArrayList<City> tour = new ArrayList<>();
        boolean[] visited = new boolean[cities.size()];
        dfs(0, parent, visited, cities, tour);
        tour.add(cities.get(0));
        return tour;
    }

    /**
     * Performs a DFS traversal to create a TSP tour from the MST.
     *
     * @param currentCity The current city being visited.
     * @param parent      The array representing the MST.
     * @param visited     The array tracking visited cities.
     * @param cities      The list of cities.
     * @param tour        The current tour being constructed.
     */
    private static void dfs(int currentCity, int[] parent, boolean[] visited, ArrayList<City> cities,
            ArrayList<City> tour) {
        visited[currentCity] = true;
        tour.add(cities.get(currentCity));

        // Iterate through each city in the list
        for (int i = 0; i < cities.size(); i++) {
            if (parent[i] == currentCity && !visited[i]) {
                dfs(i, parent, visited, cities, tour);
            }
        }
    }
}
