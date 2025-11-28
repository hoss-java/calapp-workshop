import java.util.Set;
import java.util.Scanner;

/**
 * CalAppV1
 *
 * <p>Interactive console calculator application.
 * Supports addition, subtraction, multiplication and division.
 * Enter 'exit' to terminate the program.</p>
 */

public class CalAppV1 {

    /**
     * Program entry point.
     *
     * <p>This method runs a loop that prompts the user for an operation
     * and two numbers, performs the operation, and prints the result.
     * The loop continues until the user types "exit".</p>
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String operation;
        Set<String> validOps = Set.of("exit", "+", "-", "*", "/");

        System.out.println("Welcome to the Interactive Calculator!");
        System.out.println("Enter 'exit' to quit the calculator.");

        while (true) {
            while (true) {
                System.out.print("Enter operation (+, -, *, /) or 'exit': ");
                operation = scanner.nextLine().trim();
                if (validOps.contains(operation)) break;
                System.out.println("Invalid operation. Please enter one of +, -, *, / or 'exit'.");
            }

            if (operation.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();
            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();
            scanner.nextLine();  // Consume newline left-over

            double result;
            switch (operation) {
                case "+":
                    result = add(num1, num2);
                    System.out.println("Result: " + result);
                    break;
                case "-":
                    result = subtract(num1, num2);
                    System.out.println("Result: " + result);
                    break;
                case "*":
                    result = multiply(num1, num2);
                    System.out.println("Result: " + result);
                    break;
                case "/":
                    result = divide(num1, num2);
                    if (Double.isNaN(result)) {
                        System.out.println("Error: Division by zero is not allowed.");
                    } else {
                        System.out.println("Result: " + result);
                    }
                    break;
                default:
                    System.out.println("Invalid operation. Please try again.");
                    break;
            }
        }

        scanner.close();
        System.out.println("Thank you for using the calculator. Goodbye!");
    }

    /**
     * Add two numbers.
     *
     * @param a first addend
     * @param b second addend
     * @return sum of a and b
     */
    private static double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtract two numbers.
     *
     * @param a minuend
     * @param b subtrahend
     * @return result of a - b
     */
    private static double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Multiply two numbers.
     *
     * @param a first factor
     * @param b second factor
     * @return product of a and b
     */
    private static double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divide two numbers.
     *
     * <p>Returns Double.NaN when attempting division by zero.</p>
     *
     * @param a dividend
     * @param b divisor
     * @return quotient a / b or Double.NaN if b is zero
     */
    private static double divide(double a, double b) {
        return b != 0 ? a / b : Double.NaN; // Return NaN if division by zero
    }
}
