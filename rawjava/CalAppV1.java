import java.util.Scanner;

public class CalAppV1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String operation;

        System.out.println("Welcome to the Interactive Calculator!");
        System.out.println("Enter 'exit' to quit the calculator.");

        while (true) {
            System.out.print("Enter operation (+, -, *, /) or 'exit': ");
            operation = scanner.nextLine();

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

    private static double add(double a, double b) {
        return a + b;
    }

    private static double subtract(double a, double b) {
        return a - b;
    }

    private static double multiply(double a, double b) {
        return a * b;
    }

    private static double divide(double a, double b) {
        return b != 0 ? a / b : Double.NaN; // Return NaN if division by zero
    }
}
