package csx55.threads;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ThreadPool implements Runnable {

    private Thread[] threads; /* used to hold our threads */
    private volatile String name;

    // TODO: Figure out a better way to get jobs and return them?
    private static volatile ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<>();
    public static volatile int product;
    private static volatile boolean start = false;

    private ThreadPool(String id) {
        this.name = id;
    }
    
    public ThreadPool(final int sizeOfPool) {
        threads = new Thread[sizeOfPool];

        int i;
        for (i = 0; i < threads.length; ++i) {
            Thread t = new Thread(new ThreadPool(Integer.toString(i)));
            threads[i] = t;
        } // End for loop

        // System.out.println("ThreadPool(size) constructor - Created " + i + " threads");
    } // End ThreadPool() constrcutor

    public void startAllThreads() {
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
            // System.out.println(threads[i].getName() + " has started");
        }
    } // End startAllThreads() method

    public void close() {
        start = true;
    } // End close() method

    public String getThreadName() {
        return this.name;
    }

    public void addJob(Job j) {
        try {
            jobQueue.add(j);
        } catch (Exception err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End addJob(j) method

    public Job removeJob() {
        Job j = null;

        try {
            j = jobQueue.poll();
        } catch (Exception err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return j; 
    } // End removeJob() method

    public void setStart(boolean status) {
        start = status;
    } // End setStart(bool) method

    public synchronized void print(int[] row, int[] col) {
        System.out.println("Row array: " + Arrays.toString(row));
        System.out.println("Column array: " + Arrays.toString(col));

        System.out.flush();
    }

    public void dotProduct(int[] row, int[] col) {
        // System.out.println(getThreadName() + " is computing a dot product");

        int prod = 0;
        for (int i = 0; i < row.length; ++i) {
            prod += row[i] * col[i];
        } // End outer for loop

        // System.out.println(prod);
        // setProduct(prod);
        product = prod;
    } // End product() method

    // public void setProduct(int prod) {
    //     product = prod;
    // } // setProduct(prod) method

    public int getProduct() {
        return product;
    } // End getProduct() method

    public void run() { 
        while(!start) { /* spin and wait for jobs to be added to the queue */
            if (jobQueue.size() != 0) {
                Job j = removeJob();

                if (j != null) {
                    // System.out.println(getThreadName() + " has got a job");
                    // print(j.getRowArr(), j.getColArr());
                    dotProduct(j.getRowArr(), j.getColArr());
                }
            } // End if statement
        } // End while loop      
    } // End run() method

} // End ThreadPool class
