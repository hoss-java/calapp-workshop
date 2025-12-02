package com.CalApp;

import java.util.Scanner;

import com.CalApp.lib.ExpressionParser;

    /**
     * @file CalApp.java
     * @brief Interactive console calculator application.
     *
     * This class provides the program entry point for the calculator application.
     * It delegates actual parsing and evaluation to {@link com.CalApp.lib.ExpressionParser}.
     *
     * Features:
     * - Reads mathematical expressions from standard input.
     * - Supports addition, subtraction, multiplication, division and parentheses
     *   (handled by ExpressionParser).
     * - Prints evaluated results to standard output.
     * - Typing "exit" stops the interactive loop (behavior implemented by
     *   ExpressionParser.cal()).
     */
    public class CalApp {

        /**
         * @brief Program entry point.
         *
         * Delegates to {@link com.CalApp.lib.ExpressionParser#cal(String[])} to run
         * the interactive calculator loop.
         *
         * @param args Command-line arguments (ignored by this application).
         */
        public static void main(String[] args) {
            expressionCalculator(args);
        }

    /**
     * Launches an interactive expression calculator that reads expressions from
     * standard input, evaluates them using ExpressionParser, and prints results.
     *
     * Usage:
     * - Call without arguments; the method opens a prompt and repeatedly accepts
     *   input lines.
     * - Enter an expression to evaluate; the result is printed as a double.
     * - Enter "exit" (case-insensitive) to terminate the interactive session.
     *
     * Behavior:
     * - Prompts the user with "> " for each expression.
     * - Uses ExpressionParser.evaluate(String) to evaluate expressions.
     * - On evaluation errors, prints "Error: " followed by the exception message.
     * - Closes the Scanner before returning.
     *
     * @param args Command-line arguments (ignored).
     */
    public static void expressionCalculator(String[] args) {
        ExpressionParser myExpressionParser = new ExpressionParser();

        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Welcome to the Interactive Expression Parser!");
        System.out.println("Enter an expression to evaluate or type 'exit' to quit:");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                double result = myExpressionParser.evaluate(input);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Thank you for using the parser. Goodbye!");
    }
}
