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
                    long startTime = System.nanoTime();
                    ArrayList<City> solution = Solver.solveNearestNeighbour(cities);
                    long endTime = System.nanoTime();
                    UserInterface.displaySolution(solution, startTime, endTime);
                    break;
                case 2:
                    System.out.println("Solving with Dijkstra's Algorithm...");
                    long startTime2 = System.nanoTime();
                    ArrayList<City> solution2 = Solver.solveDijkstra(cities);
                    long endTime2 = System.nanoTime();
                    UserInterface.displaySolution(solution2, startTime2, endTime2);
                    break;
                case 3:
                    System.out.println("Solving with Minimum Spanning Tree...");
                    long startTime3 = System.nanoTime();
                    ArrayList<City> solution3 = Solver.solveMST(cities);
                    long endTime3 = System.nanoTime();
                    UserInterface.displaySolution(solution3, startTime3, endTime3);
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
