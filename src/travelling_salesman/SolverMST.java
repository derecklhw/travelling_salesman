package travelling_salesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class SolverMST {

    /**
     * Utility class for representing an edge in a graph.
     */
    private static class Edge implements Comparable<Edge> {
        int source;
        int destination;
        double weight;

        /**
         * Creates an edge between two cities.
         * 
         * @param source      The source city.
         * @param destination The destination city.
         * @param weight      The weight of the edge.
         */
        private Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        /**
         * Compares two edges by weight.
         * 
         * @param other The other edge.
         * @return A negative integer, zero, or a positive integer as this edge is less
         *         than, equal to, or greater than the other edge.
         */
        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
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

        // Create a graph with all edges between all cities
        ArrayList<ArrayList<Edge>> graph = createGraph(cities);

        // Perform Prim's algorithm to find the minimum spanning tree
        Edge[] edgeTo = performPrimsAlgorithm(graph, n);

        // Build a graph for preorder traversal
        ArrayList<ArrayList<Edge>> mstGraph = buildMstGraphForTraversal(edgeTo, n);

        HashSet<Integer> visited = new HashSet<>();
        ArrayList<City> tour = new ArrayList<>();

        // Perform preorder traversal of the minimum spanning tree
        preorderTraversal(0, mstGraph, visited, tour, cities);

        optimizeTourWith2Opt(tour);
        tour.add(tour.get(0));

        return tour;
    }

    /**
     * Creates a graph with all edges between all cities.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The graph with all edges between all cities.
     */
    private static ArrayList<ArrayList<Edge>> createGraph(ArrayList<City> cities) {
        int n = cities.size();
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    graph.get(i).add(new Edge(i, j, cities.get(i).distanceTo(cities.get(j))));
                }
            }
        }
        return graph;
    }

    /**
     * Performs Prim's algorithm to find the minimum spanning tree.
     * 
     * @param graph The graph with all edges between all cities.
     * @param n     The number of cities.
     * @return The minimum spanning tree.
     */
    private static Edge[] performPrimsAlgorithm(ArrayList<ArrayList<Edge>> graph, int n) {
        boolean[] inMST = new boolean[n];
        Edge[] edgeTo = new Edge[n];
        double[] distTo = new double[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        Arrays.fill(distTo, Double.MAX_VALUE);
        distTo[0] = 0.0;
        pq.add(new Edge(-1, 0, 0.0));

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int v = e.destination;
            if (inMST[v])
                continue;
            inMST[v] = true;

            for (Edge edge : graph.get(v)) {
                int w = edge.destination;
                if (!inMST[w] && edge.weight < distTo[w]) {
                    edgeTo[w] = edge;
                    distTo[w] = edge.weight;
                    pq.add(new Edge(v, w, distTo[w]));
                }
            }
        }
        return edgeTo;
    }

    /**
     * Builds a graph for preorder traversal.
     * 
     * @param edgeTo The minimum spanning tree.
     * @param n      The number of cities.
     * @return The graph for preorder traversal.
     */
    private static ArrayList<ArrayList<Edge>> buildMstGraphForTraversal(Edge[] edgeTo, int n) {
        ArrayList<ArrayList<Edge>> mstGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mstGraph.add(new ArrayList<>());
        }

        for (Edge e : edgeTo) {
            if (e != null) {
                mstGraph.get(e.source).add(e);
                mstGraph.get(e.destination).add(new Edge(e.destination, e.source, e.weight));
            }
        }
        return mstGraph;
    }

    /**
     * Performs preorder traversal of the minimum spanning tree.
     * 
     * @param currentCity The current city.
     * @param mstGraph    The graph for preorder traversal.
     * @param visited     The set of visited cities.
     * @param tour        The tour.
     * @param cities      The list of cities.
     */
    private static void preorderTraversal(int currentCity, ArrayList<ArrayList<Edge>> mstGraph,
            HashSet<Integer> visited, ArrayList<City> tour, ArrayList<City> cities) {
        visited.add(currentCity);
        tour.add(cities.get(currentCity));

        for (Edge edge : mstGraph.get(currentCity)) {
            if (!visited.contains(edge.destination)) {
                preorderTraversal(edge.destination, mstGraph, visited, tour, cities);
            }
        }
    }

    /**
     * Optimizes the tour using the 2-opt algorithm.
     * 
     * @param tour The tour.
     */
    private static void optimizeTourWith2Opt(ArrayList<City> tour) {
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i = 0; i < tour.size() - 2; i++) {
                for (int j = i + 2; j < tour.size() - 1; j++) {
                    double currentDistance = tour.get(i).distanceTo(tour.get(i + 1))
                            + tour.get(j).distanceTo(tour.get(j + 1));
                    double newDistance = tour.get(i).distanceTo(tour.get(j))
                            + tour.get(i + 1).distanceTo(tour.get(j + 1));

                    if (newDistance < currentDistance) {
                        reverseSegment(tour, i + 1, j);
                        improvement = true;
                    }
                }
            }
        }
    }

    /**
     * Reverses a segment of the tour.
     * 
     * @param tour  The tour.
     * @param start The start index of the segment.
     * @param end   The end index of the segment.
     */
    private static void reverseSegment(ArrayList<City> tour, int start, int end) {
        while (start < end) {
            City temp = tour.get(start);
            tour.set(start, tour.get(end));
            tour.set(end, temp);
            start++;
            end--;
        }
    }

}
