package csx55.threads;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixThreads {

    private final static ReentrantLock mainThreadLock = new ReentrantLock();
    public static void main(String[] args) {

        // final int THREAD_POOL_SIZE = 8;
        // final int MATRIX_DIMENSIONS = 3;
        // final int SEED = 31459;

        if (args.length < 3) {
            System.err.println("Invalid # of arguments");
            System.exit(1);
        } // End if statement

        final int THREAD_POOL_SIZE = Integer.parseInt(args[0]);
        final int MATRIX_DIMENSIONS = Integer.parseInt(args[1]);
        final int SEED = Integer.parseInt(args[2]);

        if (THREAD_POOL_SIZE <= 0 || MATRIX_DIMENSIONS <= 1 || SEED < 0) {
            System.err.println("Invalid entry for arguments");
            System.exit(1);
        } // End if statement

        ThreadPool pool = new ThreadPool(THREAD_POOL_SIZE);
        pool.startAllThreads();
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

        Random numberGenerator = new Random(SEED);
        for (Matrix m : matrices) {
             
            int[][] arr = new int[MATRIX_DIMENSIONS][MATRIX_DIMENSIONS];

            for (int column = 0; column < arr.length; ++column) {
                for (int row = 0; row < arr.length; ++row) {
                    // int randomValue = numberGenerator.nextInt(10) + 1;
                    int randomValue = 1000 - numberGenerator.nextInt(2000);
                    arr[column][row] = randomValue;
                } // End nested for loop
            } // End for loop

            m.setData(arr);
        } // End for each loop

        for (Matrix m : matrices) {
            System.out.println("Sum of the elements in input matrix " + m.getName() + " = " + m.sumOfMatrixElements(m.data, MATRIX_DIMENSIONS));
        } // End for each loop

        System.out.println();

        Matrix x = new Matrix('X', MATRIX_DIMENSIONS);
        Matrix y = new Matrix('Y', MATRIX_DIMENSIONS);
        Matrix z = new Matrix('Z', MATRIX_DIMENSIONS);

        mainThreadLock.lock();
        // System.out.println("Is main thread locked? " + mainThreadLock.isLocked());
        try {
            // mainThreadLock.wait();
            System.out.println("Calculating Matrix X");
            // System.out.println(a.toString());
            // System.out.println(b.toString());
            x.data = x.multiplyMatrices(a, b, MATRIX_DIMENSIONS, pool);
            // System.out.println(x.toString());

            System.out.println("Calculating Matrix Y");
            // System.out.println(c.toString());
            // System.out.println(d.toString());
            y.data = y.multiplyMatrices(c, d, MATRIX_DIMENSIONS, pool);
            // System.out.println(y.toString());

            System.out.println("Calculating Matrix Z");
            // System.out.println(x.toString());
            // System.out.println(y.toString());
            z.data = z.multiplyMatrices(x, y, MATRIX_DIMENSIONS, pool);
            // System.out.println(z.toString());
        } catch (Exception err) {
            System.err.println(err.getMessage());
        } finally {
            mainThreadLock.unlock();
        } // End try-catch statement
        
        double cumulativeTime = x.getTime() + y.getTime() + z.getTime();
       
        String output = String.format("Cumulative time to compute matrices X, Y, and Z using a thread pool of size = %d is : %.3f s", THREAD_POOL_SIZE, cumulativeTime);
        System.out.println(output);
        pool.close();
    } // End main method
    
} // End MatrixThreads class
