package travelling_salesman;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Utility class for solving the Travelling Salesman Problem using the Nearest
 * Neighbour algorithm.
 */
public class SolverNN {
    /**
     * Solves the Travelling Salesman Problem using the Nearest Neighbour algorithm.
     *
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveNearestNeighbour(ArrayList<City> cities) {
        ArrayList<City> tour = new ArrayList<>();
        HashSet<Integer> visitedCities = new HashSet<>();

        // Start from the first city
        City currentCity = cities.get(0);
        tour.add(currentCity);
        visitedCities.add(currentCity.getNumber());

        // Iterate until all cities are visited
        while (tour.size() < cities.size()) {
            City nearestCity = findNearestCity(currentCity, visitedCities, cities);

            tour.add(nearestCity);
            visitedCities.add(nearestCity.getNumber());
            currentCity = nearestCity;
        }

        // Return to the starting city to complete the tour
        tour.add(tour.get(0));
        return tour;
    }

    /**
     * Finds the nearest unvisited city to the current city.
     *
     * @param currentCity   The current city.
     * @param visitedCities The set of visited cities.
     * @param cities        The list of cities to search.
     * @return The nearest unvisited city to the current city.
     */
    private static City findNearestCity(City currentCity, HashSet<Integer> visitedCities, ArrayList<City> cities) {
        City nearestCity = null;
        double minDistance = Double.MAX_VALUE;

        // Find the nearest unvisited city
        for (City city : cities) {
            if (!visitedCities.contains(city.getNumber())) {
                double distance = currentCity.distanceTo(city);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestCity = city;
                }
            }
        }
        return nearestCity;
    }
}
