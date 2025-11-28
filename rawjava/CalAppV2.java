import java.util.Scanner;
import java.util.Stack;

public class CalAppV2 {

    public static void main(String[] args) {
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

    // Method to check if the character is an operator
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Method to determine precedence of operators
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    // Method to apply an operator to two numbers
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
