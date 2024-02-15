package csx55.threads;

import java.util.*;

public class Matrix {

    private char name;
    public int[][] data;
    private double timeToComputeSum;

    public Matrix() {} // End default constructor

    public Matrix(char name, int dimensions) {
        this.name = name;
        this.data = new int[dimensions][dimensions];
    } // End Matrix() constructor

    public char getName() {
        return this.name;
    } // End getName() method

    public int[][] getData() {
        return this.data;
    } // End getData() method

    public void setData(int[][] d) {
        this.data = d;
    } // End setData() method

    public double getTime() {
        return this.timeToComputeSum;
    } // End getTime() method

    // This would probably be sycnhronized as we can only manipulate data one thread at a time
    /* Give one thread a dot product at a time */
    // Could do it without the synchronized keyword
    public int[][] multiplyMatrices(int[][] arr_one, int[][] arr_two, int desiredDimensions) {
        long startTime;
        long endTime;
        int[][] newArr = new int[desiredDimensions][desiredDimensions];

        startTime = System.nanoTime();
        for (int row = 0; row < desiredDimensions; ++row) {
            for (int column = 0; column < desiredDimensions; ++column) {
                for (int k = 0; k < desiredDimensions; ++k) {
                    newArr[row][column] = (arr_one[row][k] * arr_two[k][column]);
                } // End nested for loop
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
    } // End multiplyMatrices() method

    /* DO NOT SYNCHRONIZE */
    public int sumOfMatrixElements(int[][] arr, int dimensions) {
        int sum = 0;
        
        for (int row = 0; row < dimensions; ++row) {
            for (int column = 0; column < dimensions; ++column) {
                sum += arr[row][column];
            } // End nested for loop
        } // End for loop

        return sum;
    } // End sumOfMatrixElements() method

} // End Matrix class