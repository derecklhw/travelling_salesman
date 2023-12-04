package travelling_salesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Utility class for solving the Travelling Salesman Problem.
 */
public class SolverNN {
    /**
     * Solves the Travelling Salesman Problem using the Nearest Neighbour
     * algorithm.
     * 
     * @param cities The list of cities to use in the algorithm.
     * @return The solution to the Travelling Salesman Problem.
     */
    public static ArrayList<City> solveNearestNeighbour(ArrayList<City> cities) {

        ArrayList<City> tour = new ArrayList<>();
        ArrayList<City> remainingCities = new ArrayList<>(cities);

        // Start from the first city and remove it from the list of remaining cities
        City currentCity = remainingCities.remove(0);
        tour.add(currentCity);

        // Iterate until all cities are visited
        while (!remainingCities.isEmpty()) {
            // Find the nearest city from the current city
            City nearestCity = findNearestCityNearestNeighbour(currentCity, remainingCities);
            tour.add(nearestCity);
            remainingCities.remove(nearestCity);
            currentCity = nearestCity;
        }

        tour.add(tour.get(0));
        return tour;
    }

    /**
     * Finds the nearest city to the current city
     * 
     * @param currentCity The current city.
     * @param cities      The list of cities to search.
     * 
     * @return The nearest city to the current city.
     */
    private static City findNearestCityNearestNeighbour(City currentCity, ArrayList<City> cities) {
        City nearestCity = null;
        double minDistance = Double.MAX_VALUE;

        // Iterate through each city in the list
        for (City city : cities) {
            double distance = currentCity.distanceTo(city);
            // Check if the calculated distance is less than the minimum distance found
            if (distance < minDistance) {
                minDistance = distance;
                nearestCity = city;
            }
        }
        return nearestCity;
    }

}