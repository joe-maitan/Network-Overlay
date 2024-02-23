package csx55.threads;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ThreadPool {

    private Thread[] threads; /* used to hold our threads */
    private volatile ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<>();
    public volatile int product;
    private static volatile boolean start = false;
    
    public ThreadPool(final int sizeOfPool) {
        threads = new Thread[sizeOfPool];

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(this::run);
            String name = Integer.toString(i);
            threads[i].setName(name);
        } // End for loop
    } // End ThreadPool() constrcutor

    public void startAllThreads() {
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }
    } // End startAllThreads() method

    public void close() {
        start = true;
    } // End close() method

    public void addJob(Job j) {
        try {
            this.jobQueue.add(j);
        } catch (Exception err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End addJob(j) method

    public Job removeJob() {
        Job j = null;

        try {
            j = this.jobQueue.poll();
        } catch (Exception err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return j; 
    } // End removeJob() method

    // public void setValue(int value) {
    //     // System.out.println("Setting value to: " + value);
    //     this.value = value;
    // } // End addValue() method

    // public int getValue() {
    //     // System.out.println("Getting value: " + this.value);
    //     return this.value;
    // } // End getValue() method

    public void setStart(boolean status) {
        start = status;
    } // End setStart(bool) method

    public synchronized void print(int[] row, int[] col) {
        System.out.println("Row array: " + Arrays.toString(row));
        System.out.println("Column array: " + Arrays.toString(col));

        System.out.flush();
    }

    public void dotProduct(int[] row, int[] col) {
        // System.out.println("Computing the dot product");
        // print(row, col);

        int prod = 0;
        for (int i = 0; i < row.length; ++i) {
            prod += row[i] * col[i];
        } // End outer for loop

        // System.out.println(prod);
        this.product = prod;
    } // End product() method

    public void run() { 
        while(!start) { /* spin and wait for jobs to be added to the queue */
            if (jobQueue.size() != 0) {
                Job j = removeJob();

                if (j != null) {
                    // System.out.println("Removing job from the queue and it is not null");
                    // print(j.getRowArr(), j.getColArr());
                    dotProduct(j.getRowArr(), j.getColArr());
                }
            } // End if statement
        } // End while loop      
    } // End run() method

} // End ThreadPool class
