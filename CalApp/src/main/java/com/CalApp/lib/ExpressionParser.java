package com.CalApp.lib;

import java.util.Scanner;
import java.util.Stack;

/**
 * @file ExpressionParser.java
 * @brief Interactive console calculator application.
 *
 * This class implements a simple expression parser/evaluator that supports
 * addition, subtraction, multiplication and division with parentheses.
 * The application reads expressions from standard input and prints results.
 * Enter 'exit' to terminate the program.
 */
public class ExpressionParser {

    /**
     * @brief Program entry point.
     *
     * Reads expressions from the console in a loop, evaluates them and prints
     * the result. The loop exits when the user types "exit".
     *
     * @param args Command-line arguments (ignored).
     */

    public static void cal(String[] args) {
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
                double result = evaluate(input);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Thank you for using the parser. Goodbye!");
    }

    /**
     * @brief Evaluate an arithmetic expression represented as a string.
     *
     * This method parses and evaluates an arithmetic expression containing
     * positive decimal numbers, the binary operators +, -, *, / and parentheses.
     * It uses two stacks (one for values, one for operators) and applies the
     * standard operator precedence and parenthesis rules.
     *
     * @param expression The infix arithmetic expression to evaluate.
     * @return The computed numeric result as a double.
     * @throws NumberFormatException If a numeric token cannot be parsed.
     * @throws java.util.EmptyStackException If the expression is malformed.
     * @throws UnsupportedOperationException For invalid operators or divide-by-zero.
     */
    public static double evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            // Skip whitespace
            if (tokens[i] == ' ') continue;

            // If the token is a number, parse it
            if (Character.isDigit(tokens[i])) {
                StringBuilder stringBuilder = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) {
                    stringBuilder.append(tokens[i++]);
                }
                values.push(Double.parseDouble(stringBuilder.toString()));
                i--; // Adjust index to avoid skipping the next token
            } 
            // If the token is an opening bracket, push it to the operator stack
            else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            } 
            // If the token is a closing bracket, solve the entire brace
            else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Remove the '('
            } 
            // If the token is an operator
            else if (isOperator(tokens[i])) {
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(tokens[i]);
            }
        }

        // Perform all remaining operations
        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop(); // The final result
    }

    /**
     * @brief Check whether a character is a supported operator.
     *
     * Supported operators: +, -, *, /
     *
     * @param c Character to test.
     * @return true if c is one of the supported operators, false otherwise.
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * @brief Determine if op2 has higher or same precedence than op1.
     *
     * This method is used to decide whether to apply operators already on the
     * operator stack before pushing a new operator op1.
     *
     * @param op1 The incoming operator.
     * @param op2 The operator at the top of the stack.
     * @return true if op2 has higher or same precedence and is not a parenthesis.
     */
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    /**
     * @brief Apply a binary operator to two operands.
     *
     * Note: The order of operands when popped from the stack is b then a, so
     * the operation performed is a <operator> b.
     *
     * @param operator The operator character ('+', '-', '*', '/').
     * @param b The right-hand operand.
     * @param a The left-hand operand.
     * @return The result of applying the operator to a and b.
     * @throws UnsupportedOperationException If operator is invalid or division by zero occurs.
     */
    private static double applyOperation(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            default:
                throw new UnsupportedOperationException("Invalid operator: " + operator);
        }
    }
}
