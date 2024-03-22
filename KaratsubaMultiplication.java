import java.util.*;

public class KaratsubaMultiplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first number (x): ");
        String xStr = scanner.next();
        System.out.print("Enter the second number (y): ");
        String yStr = scanner.next();

        // Convert input strings to integers
        int x = Integer.parseInt(xStr);
        int y = Integer.parseInt(yStr);

        // Calculate the result using Karatsuba Algorithm
        int result = karatsuba(x, y);

        System.out.println("Intermediate Steps:");
        System.out.println("-------------------");
        System.out.println("a = " + (x / 100) + ", b = " + (x % 100));
        System.out.println("c = " + (y / 100) + ", d = " + (y % 100));
        System.out.println("(" + (x / 100) + "*" + (y / 100) + ")*10^" + (Integer.toString(x).length()) + " + ((" + (x / 100) + "*" + (y % 100) + ") + (" + (x % 100) + "*" + (y / 100) + ")) * 10^" + (Integer.toString(x).length() / 2) + " + (" + (x % 100) + "*" + (y % 100) + ")");

        System.out.println("Intermediate Steps:");
        System.out.println("-------------------");
        System.out.println("x = " + x + ", y = " + y);
        System.out.println("Result: " + result);
    }

    public static int karatsuba(int x, int y) {
        // Base case for recursion
        if (x < 100 || y < 100) {
            return x * y;
        }

        // Calculate the number of digits of the two numbers
        int n = Math.max(Integer.toString(x).length(), Integer.toString(y).length());

        // Calculate the half size
        int m = n / 2;

        // Split the input numbers into two halves
        int high1 = x / (int) Math.pow(10, m);
        int low1 = x % (int) Math.pow(10, m);
        int high2 = y / (int) Math.pow(10, m);
        int low2 = y % (int) Math.pow(10, m);

        // Recursive steps
        int z0 = karatsuba(low1, low2);
        int z1 = karatsuba((low1 + high1), (low2 + high2));
        int z2 = karatsuba(high1, high2);

        // Calculate the final result using the intermediate results
        int result = (z2 * (int) Math.pow(10, 2 * m)) + ((z1 - z2 - z0) * (int) Math.pow(10, m)) + z0;

        return result;
    }
}