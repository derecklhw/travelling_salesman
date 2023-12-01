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
        } else {
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.println("Select an algorithm to solve the TSP:");
                System.out.println("1. Nearest Neighbour");
                System.out.println("2. Dijkstra's Algorithm");
                System.out.println("3. Minimum Spanning Tree");
                System.out.println("4. Exit");
                System.out.println("");

                // Add more algorithms if you have them
                System.out.print("Enter your choice (number): ");

                try {
                    choice = scanner.nextInt();

                    // Check if the choice is within the valid range
                    if (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                        validChoice = true;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear the buffer
                }
            }

            ArrayList<City> solution = new ArrayList<>();
            switch (choice) {
                case 1:
                    System.out.println("Solving with Algorithm 1...");
                    solution = solve(cities);
                    break;
                case 2:
                    System.out.println("Solving with Algorithm 2...");
                    solution = solve(cities);
                    break;
                case 3:
                    System.out.println("Solving with Algorithm 3...");
                    solution = solve(cities);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    break;
            }

            for (int i = 0; i < solution.size(); i++) {
                if (i > 0) {
                    System.out.print("-");
                }
                System.out.print(solution.get(i).getNumber());
            }
            System.out.println();
            scanner.close();	
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
                if (line.isEmpty()) continue; // Skip empty lines
    
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    try {
                        int cityNumber = Integer.parseInt(parts[0]);
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        cities.add(new City(cityNumber, x, y));
                        // System.out.println("Read city: " + cityNumber + " (" + x + ", " + y + ")"); // Optional: For debugging
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


