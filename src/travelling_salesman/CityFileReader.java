package travelling_salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility class for reading city data from a file.
 */
public class CityFileReader {
    /**
     * Reads city data from a specified file and creates a list of cities.
     * 
     * @param filePath The path of the file to read city data from.
     * @return A list of cities read from the file.
     * @throws RuntimeException if the file is not found or if there's an issue with
     *                          the file format.
     */
    public static ArrayList<City> readCitiesFromFile(String filePath) {
        ArrayList<City> cities = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    try {
                        int cityNumber = Integer.parseInt(parts[0]);
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        cities.add(new City(cityNumber, x, y));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in line: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw new RuntimeException("File not found", e);
        }

        return cities;
    }
}
