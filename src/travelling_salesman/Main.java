package travelling_salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No file path provided.");
            return;
        }
        String filePath = args[0];
        ArrayList<City> cities = readCitiesFromFile(filePath);

        if (cities.isEmpty()) {
            System.out.println("No cities found in the file.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            int choice = getUserChoice(scanner);

            if (choice == 4) {
                System.out.println("Exiting...");
                return; // natural termination
            }

            System.out.println("Solving with Algorithm " + choice + "...");
            ArrayList<City> solution = solve(cities);
            displaySolution(solution);
        }

    }

    /**
     * Reads cities from a file.
     *
     * @param filePath The path of the file containing city data.
     * @return A list of cities.
     */
    private static ArrayList<City> readCitiesFromFile(String filePath) {
        ArrayList<City> cities = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // Trim each line
                if (line.isEmpty())
                    continue; // Skip empty lines

                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    try {
                        int cityNumber = Integer.parseInt(parts[0]);
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        cities.add(new City(cityNumber, x, y));
                        // System.out.println("Read city: " + cityNumber + " (" + x + ", " + y + ")");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in line: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

        return cities;
    }

    private static int getUserChoice(Scanner scanner) {
        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            displayMenu();
            try {
                choice = scanner.nextInt();
                validChoice = choice >= 1 && choice <= 4;
                if (!validChoice) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the buffer
            }
        }
        return choice;
    }

    private static void displayMenu() {
        System.out.println("Select an algorithm to solve the TSP:");
        System.out.println("1. Nearest Neighbour");
        System.out.println("2. Dijkstra's Algorithm");
        System.out.println("3. Minimum Spanning Tree");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (number): ");
    }

    private static void displaySolution(ArrayList<City> solution) {
        for (int i = 0; i < solution.size(); i++) {
            if (i > 0)
                System.out.print("-");
            System.out.print(solution.get(i).getNumber());
        }
        System.out.println();
    }

    // Nearest Neighbour Algorithm for TSP
    public static ArrayList<City> solve(ArrayList<City> cities) {
        ArrayList<City> tour = new ArrayList<>();
        ArrayList<City> remainingCities = new ArrayList<>(cities);

        // Start from the first city
        City currentCity = remainingCities.remove(0);
        tour.add(currentCity);

        // Iterate until all cities are visited
        while (!remainingCities.isEmpty()) {
            City nearestCity = findNearestCity(currentCity, remainingCities);
            tour.add(nearestCity);
            remainingCities.remove(nearestCity);
            currentCity = nearestCity;
        }

        // Return to the starting city
        tour.add(tour.get(0));
        return tour;
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
