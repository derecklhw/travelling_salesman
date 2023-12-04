package travelling_salesman;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user interactions for the Travelling Salesman problem solver.
 */
public class UserInterface {
    /**
     * Prompts the user to select an algorithm and returns the user's choice.
     * 
     * @param scanner The scanner to use for reading user input.
     * @return The user's choice of algorithm.
     */
    public static int getUserChoice(Scanner scanner) {
        int choice = 0;

        while (true) {
            displayMenu();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Choice must be between 1 and 4. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    /**
     * Displays the menu of algorithms to the user.
     */
    private static void displayMenu() {
        System.out.println("Select an algorithm to solve the TSP:");
        System.out.println("1. Nearest Neighbour");
        System.out.println("2. Dijkstra's Algorithm");
        System.out.println("3. Minimum Spanning Tree");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (number): ");
    }

    /**
     * Displays the solution to the user.
     * 
     * @param solution  The solution to display.
     * @param startTime The time at which the algorithm started.
     * @param endTime   The time at which the algorithm ended.
     */
    public static void displaySolution(ArrayList<City> solution, long startTime, long endTime) {
        for (int i = 0; i < solution.size(); i++) {
            if (i > 0)
                System.out.print("-");
            System.out.print(solution.get(i).getNumber());
        }
        System.out.println();
        long duration = endTime - startTime;
        System.out.println("Execution Time: " + duration + " nanoseconds");
    }
}
