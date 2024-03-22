import java.util.*;

public class MP2_2BSCS1_Llana {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean terminate = false;

        while (!terminate) {
        System.out.println("Choose the multiplication algorithm:");
        System.out.println("1. Strassen's Algorithm");
        System.out.println("2. Karatsuba Multiplication");
        System.out.println("3. Terminate");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                strassensAlgorithm(scanner);
                break;
            case 2:
                karatsubaMultiplication(scanner);
                break;
            case 3:
                System.out.println(" - Aliyah Aira Llana");
                terminate = true;
                break;
            default:
                System.out.println("Invalid choice. Please choose a valid option.");
        }
    }
}

    public static void strassensAlgorithm(Scanner scanner) {
        System.out.println("Enter the dimension of the square matrices:");
        int n = scanner.nextInt();

        System.out.println("Enter the elements of the first matrix:");
        int[][] A = new int[n][n];
        inputMatrix(scanner, A);

        System.out.println("Enter the elements of the second matrix:");
        int[][] B = new int[n][n];
        inputMatrix(scanner, B);

        int[][] C = strassenMultiply(A, B);

        System.out.println("Resultant Matrix after Strassen's multiplication:");
        printMatrix(C);
    }

    public static void karatsubaMultiplication(Scanner scanner) {
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

        System.out.println("-------------------");
        System.out.println("x = " + x + ", y = " + y);
        System.out.println("Result: " + result);
    }

    // Strassen's Algorithm methods

    public static int[][] strassenMultiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        if (n == 1) {
            C[0][0] = A[0][0] * B[0][0];
        } else {
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            splitMatrix(A, A11, 0, 0);
            splitMatrix(A, A12, 0, n / 2);
            splitMatrix(A, A21, n / 2, 0);
            splitMatrix(A, A22, n / 2, n / 2);
            splitMatrix(B, B11, 0, 0);
            splitMatrix(B, B12, 0, n / 2);
            splitMatrix(B, B21, n / 2, 0);
            splitMatrix(B, B22, n / 2, n / 2);

            int[][] M1 = strassenMultiply(addMatrices(A11, A22), addMatrices(B11, B22));
            int[][] M2 = strassenMultiply(addMatrices(A21, A22), B11);
            int[][] M3 = strassenMultiply(A11, subtractMatrices(B12, B22));
            int[][] M4 = strassenMultiply(A22, subtractMatrices(B21, B11));
            int[][] M5 = strassenMultiply(addMatrices(A11, A12), B22);
            int[][] M6 = strassenMultiply(subtractMatrices(A21, A11), addMatrices(B11, B12));
            int[][] M7 = strassenMultiply(subtractMatrices(A12, A22), addMatrices(B21, B22));

            int[][] C11 = addMatrices(subtractMatrices(addMatrices(M1, M4), M5), M7);
            int[][] C12 = addMatrices(M3, M5);
            int[][] C21 = addMatrices(M2, M4);
            int[][] C22 = addMatrices(subtractMatrices(subtractMatrices(M1, M2), M3), M6);

            mergeMatrices(C11, C, 0, 0);
            mergeMatrices(C12, C, 0, n / 2);
            mergeMatrices(C21, C, n / 2, 0);
            mergeMatrices(C22, C, n / 2, n / 2);
        }
        return C;
    }


    public static int karatsuba(int x, int y) {
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

    public static void inputMatrix(Scanner scanner, int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }

    public static void printMatrix(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void splitMatrix(int[][] parent, int[][] child, int iOffset, int jOffset) {
        int n = child.length;
        for (int i1 = 0, i2 = iOffset; i1 < n; i1++, i2++) {
            for (int j1 = 0, j2 = jOffset; j1 < n; j1++, j2++) {
                child[i1][j1] = parent[i2][j2];
            }
        }
    }

    public static void mergeMatrices(int[][] child, int[][] parent, int iOffset, int jOffset) {
        int n = child.length;
        for (int i1 = 0, i2 = iOffset; i1 < n; i1++, i2++) {
            for (int j1 = 0, j2 = jOffset; j1 < n; j1++, j2++) {
                parent[i2][j2] = child[i1][j1];
            }
        }
    }

    public static int[][] addMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public static int[][] subtractMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }
}
