import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter first number: ");
        double num1 = input.nextDouble();

        System.out.print("Enter operator (+, -, *, /): ");
        char op = input.next().charAt(0);

        System.out.print("Enter second number: ");
        double num2 = input.nextDouble();

        double result;

        if (op == '+') {
            result = num1 + num2;
            System.out.println("Result: " + result);
        } 
        else if (op == '-') {
            result = num1 - num2;
            System.out.println("Result: " + result);
        } 
        else if (op == '*') {
            result = num1 * num2;
            System.out.println("Result: " + result);
        } 
        else if (op == '/') {
            if (num2 != 0) {
                result = num1 / num2;
                System.out.println("Result: " + result);
            } else {
                System.out.println("Cannot divide by zero.");
            }
        } 
        else {
            System.out.println("Invalid operator.");
        }

        input.close();
    }
}