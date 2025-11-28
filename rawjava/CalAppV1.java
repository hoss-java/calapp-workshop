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

            switch (operation) {
                case "+":
                    break;
                case "-":
                    break;
                case "*":
                    break;
                case "/":
                    break;
                default:
                    System.out.println("Invalid operation. Please try again.");
                    break;
            }
        }

        scanner.close();
        System.out.println("Thank you for using the calculator. Goodbye!");
    }
}
