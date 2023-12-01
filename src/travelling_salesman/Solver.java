package travelling_salesman;

import java.util.ArrayList;

public class Solver {
    public static ArrayList<City> solveNearestNeighbour(ArrayList<City> cities) {
        ArrayList<City> tour = new ArrayList<>();
        ArrayList<City> remainingCities = new ArrayList<>(cities);

        City currentCity = remainingCities.remove(0);
        tour.add(currentCity);

        while (!remainingCities.isEmpty()) {
            City nearestCity = findNearestCity(currentCity, remainingCities);
            tour.add(nearestCity);
            remainingCities.remove(nearestCity);
            currentCity = nearestCity;
        }

        tour.add(tour.get(0));
        return tour;
    }

    public static ArrayList<City> solveDijkstra(ArrayList<City> cities) {
        return cities;
    }

    public static ArrayList<City> solveMST(ArrayList<City> cities) {
        return cities;
    }

    // Method to find the nearest city to the current city
    private static City findNearestCity(City currentCity, ArrayList<City> cities) {
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
}
