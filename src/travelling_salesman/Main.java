package travelling_salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            for (City city : cities) {
                System.out.println(city.distanceTo(cities.get(0)));
            }
        	
        }

        // Here you can pass the cities array to your TSP solver
        // For example: TSPSolver.solve(cities);
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
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    try {
                        int cityNumber = Integer.parseInt(parts[0]);
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        cities.add(new City(cityNumber, x, y));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in line: " + line);
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
}


