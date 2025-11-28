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
        return 0;
    }
}

