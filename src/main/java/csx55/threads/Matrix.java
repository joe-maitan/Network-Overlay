package csx55.threads;

public class Matrix {

    private char name;
    public int[][] data;
    private double timeToComputeSum;

    public Matrix() {} // End default constructor

    public Matrix(char name, int dimensions) {
        this.name = name;
        this.data = new int[dimensions][dimensions];
    } // End Matrix(name, dimensions) constructor

    public Matrix(char name, Matrix one, Matrix two, int dimensions) {
        this.name = name;
        this.data = multiplyMatrices(one, two, dimensions, null);
    } // End Matrix(one, two, dimensions) constructor

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

    public String toString() {
        String output = "";

        for (int i = 0; i < this.data.length; ++i) {
            output += "[";
            for (int j = 0; j < this.data[0].length; ++j) {
                if (j == this.data[0].length - 1) {
                    output += data[i][j];
                } else {
                    output += data[i][j] + " ";
                }
            }
            output += "]\n";
        }

        return output;
    } // End toString() method

    public long sumOfMatrixElements(int[][] arr, int dimensions) { /* DO NOT SYNCHRONIZE */
        long sum = 0;
        
        for (int row = 0; row < dimensions; ++row) {
            for (int column = 0; column < dimensions; ++column) {
                sum += (long) arr[row][column];
            } // End nested for loop
        } // End for loop

        return sum;
    } // End sumOfMatrixElements() method

    public void getColumn(int[][] array, int columnIndex, int[] column) {
        for (int i = 0; i < array.length; i++) {
            column[i] = array[i][columnIndex];
        } // End for loop
    } // End getColumn() method

    public int[][] multiplyMatrices(Matrix one, Matrix two, int desiredDimensions, ThreadPool pool) {
        long startTime;
        long endTime;
        
        int[][] arr_one = one.getData();
        int[][] arr_two = two.getData();
        int[][] productArr = new int[desiredDimensions][desiredDimensions];
        
        int[] rowArr = new int[desiredDimensions];
        int[] columnArr = new int[desiredDimensions];

        startTime = System.nanoTime();
        for (int row = 0; row < desiredDimensions; ++row) {
            rowArr = arr_one[row];
            for (int column = 0; column < desiredDimensions; ++column) {
                getColumn(arr_two, column, columnArr);

                Job newJob = new Job(rowArr, columnArr);
                pool.addJob(newJob);
                pool.dotProduct(rowArr, columnArr);
                productArr[row][column] = pool.product;
            } // End for loop
        } // End for loop

        endTime = System.nanoTime();

        double totalTime = (endTime - startTime) / 1e9;

        System.out.println("Calculation of matrix " + this.getName() + " (Product of " + one.getName() + " and " + two.getName() + ") complete - sum of the elements in " + this.getName() + " is: " + sumOfMatrixElements(productArr, desiredDimensions));
        String timeToCompute = String.format("Time to compute matrix " + this.getName() + ": %.3f s", totalTime);
        System.out.println(timeToCompute);
        System.out.println();

        this.timeToComputeSum = totalTime;

        return productArr;
    } // End multiplyMatrices() method

} // End Matrix class