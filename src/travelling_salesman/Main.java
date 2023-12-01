package travelling_salesman;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No file path provided.");
            return;
        }
        String filePath = args[0];
        ArrayList<City> cities = FileReader.readCitiesFromFile(filePath);

        if (cities.isEmpty()) {
            System.out.println("No cities found in the file.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            int choice = UserInterface.getUserChoice(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Solving with Nearest Neighbour...");
                    ArrayList<City> solution = Solver.solveNearestNeighbour(cities);
                    UserInterface.displaySolution(solution);
                    break;
                case 2:
                    System.out.println("Solving with Dijkstra's Algorithm...");
                    ArrayList<City> solution2 = Solver.solveDijkstra(cities);
                    UserInterface.displaySolution(solution2);
                    break;
                case 3:
                    System.out.println("Solving with Minimum Spanning Tree...");
                    ArrayList<City> solution3 = Solver.solveMST(cities);
                    UserInterface.displaySolution(solution3);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }

    }

}
