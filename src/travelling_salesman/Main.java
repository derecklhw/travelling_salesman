package travelling_salesman;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the program's entry point and user interaction.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No file path provided.");
            return;
        }
        String filePath = args[0];
        ArrayList<City> cities = CityFileReader.readCitiesFromFile(filePath);

        if (cities.isEmpty()) {
            System.out.println("No cities found in the file.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            int choice = UserInterface.getUserChoice(scanner);
            executeChoice(choice, cities);
        }

    }

    /**
     * Executes the selected algorithm based on user choice.
     *
     * @param choice The user's choice of algorithm.
     * @param cities The list of cities to use in the algorithm.
     */
    private static void executeChoice(int choice, ArrayList<City> cities) {
        long startTime, endTime;
        ArrayList<City> solution;

        switch (choice) {
            case 1:
                System.out.println("\nSolving with Nearest Neighbour...");
                startTime = System.nanoTime();
                solution = SolverNN.solveNearestNeighbour(cities);
                endTime = System.nanoTime();
                break;
            case 2:
                System.out.println("\nSolving with Dijkstra's Algorithm...");
                startTime = System.nanoTime();
                solution = SolverDijkstra.solveDijkstra(cities);
                endTime = System.nanoTime();
                break;
            case 3:
                System.out.println("\nSolving with Minimum Spanning Tree...");
                startTime = System.nanoTime();
                solution = SolverMST.solveMST(cities);
                endTime = System.nanoTime();
                break;
            case 4:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }
        UserInterface.displaySolution(solution, startTime, endTime);
    }

}
