package csx55.threads;

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

    public int sumOfMatrixElements(int[][] arr, int dimensions) { /* DO NOT SYNCHRONIZE */
        int sum = 0;
        
        for (int row = 0; row < dimensions; ++row) {
            for (int column = 0; column < dimensions; ++column) {
                sum += arr[row][column];
            } // End nested for loop
        } // End for loop

        return sum;
    } // End sumOfMatrixElements() method

    public int[] getColumn(int[][] array, int columnIndex) {
        int[] column = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            column[i] = array[i][columnIndex];
        } // End for loop

        return column;
    } // End getColumn() method

    // public int dotProduct(int[] row, int[] col) {
    //     int  product = 0;
    //     for (int i = 0; i < row.length; ++i) {
    //         for (int j = 0; j < col.length; ++j) {
    //             product += row[i] * col[j];
    //         } // End nested for loop
    //     } // End outer for loop

    //     return product;
    // } // End product() method

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
                columnArr = getColumn(arr_two, column);

                Job newJob = new Job(rowArr, columnArr);
                pool.addJob(newJob);
                productArr[row][column] = pool.getValue();
                
                // productArr[row][column] = dotProduct(new, columnArr); /* Give one thread a dot product at a time */
                
                // for (int k = 0; k < desiredDimensions; ++k) {
                //     productArr[row][column] = (arr_one[row][k] * arr_two[k][column]);
                // } // End nested for loop
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