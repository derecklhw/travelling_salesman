package travelling_salesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

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

    public static ArrayList<City> solveMST(ArrayList<City> cities) {
        return cities;
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
}