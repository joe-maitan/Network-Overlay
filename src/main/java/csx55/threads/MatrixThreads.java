package csx55.threads;

import java.util.ArrayList;

public class MatrixThreads {

    // final int THREAD_POOL_SIZE;
    // final int MATRIX_DIMENSIONS;
    // final int SEED;
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Invalid # of arguments");
            System.exit(1);
        } 

        final int THREAD_POOL_SIZE = Integer.parseInt(args[0]);
        final int MATRIX_DIMENSIONS = Integer.parseInt(args[1]);
        final int SEED = Integer.parseInt(args[2]);

        if (THREAD_POOL_SIZE <= 0 || MATRIX_DIMENSIONS <= 1 || SEED < 0) {
            System.err.println("Invalid entry for arguments");
            System.exit(1);
        }

        ArrayList<Matrix> matrices = new ArrayList<>();
        
        System.out.println("Dimensionality of the square matrices is: " + MATRIX_DIMENSIONS);
        System.out.println("The thread pool has been initialized to: " + THREAD_POOL_SIZE);
        System.out.println();

        /* These are our four matrices used to calculate X and Y 
        *   X = A * B 
        *   and 
        *   Y = C * D
        */
        Matrix a = new Matrix('A', MATRIX_DIMENSIONS);
        Matrix b = new Matrix('B', MATRIX_DIMENSIONS);
        Matrix c = new Matrix('C', MATRIX_DIMENSIONS);
        Matrix d = new Matrix('D', MATRIX_DIMENSIONS);

        matrices.add(a);
        matrices.add(b);
        matrices.add(c);
        matrices.add(d);

        for (Matrix m : matrices) {
            m.populateArray(m.data, MATRIX_DIMENSIONS);
            System.out.println("Sum of the elements in input matrix " + m.getName() + " = " + m.sumOfMatrixElements(m.data, MATRIX_DIMENSIONS));
        } // End for each loop

        System.out.println();

        Matrix x = new Matrix('X', MATRIX_DIMENSIONS);
        x.data = x.multiplyMatrices(a.data, b.data, MATRIX_DIMENSIONS);

        Matrix y = new Matrix('Y', MATRIX_DIMENSIONS);
        y.data = y.multiplyMatrices(c.data, d.data, MATRIX_DIMENSIONS);

        Matrix z = new Matrix('Z', MATRIX_DIMENSIONS);
        z.data = z.multiplyMatrices(x.data, y.data, MATRIX_DIMENSIONS);

        double cumulativeTime = x.getTime() + y.getTime() + z.getTime();
       
        String output = String.format("Cumulative time to compute matrices X, Y, and Z using a thread pool of size = %d is : %.3f", THREAD_POOL_SIZE, cumulativeTime);
        System.out.println(output);
    } // End main method
    
} // End MatrixThreads class
