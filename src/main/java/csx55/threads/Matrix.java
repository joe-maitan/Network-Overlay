package csx55.threads;

import java.util.*;

public class Matrix {

    private char name;
    private int[][] data;
    private double timeToComputeSum;

    public Matrix() {} // End default constructor

    public Matrix(char name, int dimensions) {
        this.name = name;
        int[][] newArr = new int[dimensions][dimensions];
        this.data = newArr;
    } // End Matrix() constructor

    public char getName() {
        return this.name;
    } // End getName() method

    // synchronized
    public void populateArray(int[][] arr, int seed) {
        Random numberGenerator = new Random(seed);
        for (int column = 0; column < arr.length; ++column) {
            for (int row = 0; row < arr.length; ++row) {
                arr[column][row] = numberGenerator.nextInt();
            } // End for loop
        } // End for loop
    } // End populateArray() method

    // This would probably be sycnhronized as we can only manipulate data one thread at a time
    public int[][] multiplyMatrices(int[][] arr_one, int[][] arr_two, int desiredDimensions) {
        long startTime;
        long endTime;
        int[][] newArr = new int[desiredDimensions][desiredDimensions];

        startTime = System.nanoTime();
        for (int row = 0; row < desiredDimensions; ++row) {
            
            for (int column = 0; column < desiredDimensions; ++column) {

                for (int k = 0; k < desiredDimensions; ++k) {
                    // System.out.print(arr_one[row][k] + " * " + arr_two[k][column] + " = ");
                    newArr[row][column] = (arr_one[row][k] * arr_two[k][column]);
                    // System.out.println(newArr[row][column]);
                }

            } // End for loop

        } // End for loop
        endTime = System.nanoTime();

        double totalTime = (endTime - startTime) / 1e9;

        System.out.println("Calculation of matrix " + this.getName() + " (Product of ? and ?) complete - sum of the elements in " + this.getName() + " is: " + sumOfMatrixElements(newArr, desiredDimensions));
        String timeToCompute = String.format("Time to compute matrix " + this.getName() + ": %.3f s", totalTime);
        System.out.println(timeToCompute);
        System.out.println();

        this.timeToComputeSum = totalTime;

        return newArr;
        // We have to sum the timeToCompute for all 3 matrices X, Y, and Z.
    } // End multiplyMatrices() method

    public int sumOfMatrixElements(int[][] arr, int dimensions) {
        int sum = 0;
        
        for (int row = 0; row < dimensions; ++row) {
            for (int column = 0; column < dimensions; ++column) {
                sum += arr[row][column];
            }
        }

        return sum;
    } // End sumOfMatrixElements

    public static void main(String[] args) {
        /* the program will take in three arguments
        * - thread pool size
        * - matrix dimensions (for the purposes of this assignment the matrices are squares)
        * - seed for populating the random number generator
        */

        // if (args.length < 3) {
        //     System.out.println("Invalid number of arguments. Exiting program.");
        //     System.exit(1);
        // } // End if statement

        int threadPoolSize = 8;
        int matrixDimensions = 100;
        int seed = 42;

        if (threadPoolSize < 0 || matrixDimensions < 0 || seed < 0) {
            System.out.println("Invalid input for arguments. Exiting program.");
        } // End if statement

        ArrayList<Matrix> matrices = new ArrayList<>();
        
        System.out.println("Dimensionality of the square matrices is: " + matrixDimensions);
        System.out.println("The thread pool has been initialized to: " + threadPoolSize);
        System.out.println();

        /* These are our four matrices used to calculate X and Y 
        *   X = A * B 
        *   and 
        *   Y = C * D
        */
        Matrix a = new Matrix('A', matrixDimensions);
        Matrix b = new Matrix('B', matrixDimensions);
        Matrix c = new Matrix('C', matrixDimensions);
        Matrix d = new Matrix('D', matrixDimensions);

        matrices.add(a);
        matrices.add(b);
        matrices.add(c);
        matrices.add(d);

        for (Matrix m : matrices) {
            m.populateArray(m.data, seed);
            System.out.println("Sum of the elements in input matrix " + m.getName() + " = " + m.sumOfMatrixElements(m.data, matrixDimensions));
        } // End for each loop

        System.out.println();

        Matrix x = new Matrix('X', matrixDimensions);
        x.data = x.multiplyMatrices(a.data, b.data, matrixDimensions);

        Matrix y = new Matrix('Y', matrixDimensions);
        y.data = y.multiplyMatrices(c.data, d.data, matrixDimensions);

        Matrix z = new Matrix('Z', matrixDimensions);
        z.data = z.multiplyMatrices(x.data, y.data, matrixDimensions);

       
        String output = String.format("Cumulative time to compute matrices X, Y, and Z using a thread pool of size = %d is : %.3f", threadPoolSize, 72.12071231);
        System.out.println(output);

    } // End main method

} // End Matrix class