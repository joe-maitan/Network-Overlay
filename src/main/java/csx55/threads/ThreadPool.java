package csx55.threads;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ThreadPool implements Runnable {

    private Thread[] threads; /* used to hold our threads */
    private String name;
    private volatile ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<>();
    public volatile int product;
    private static volatile boolean start = false;
    
    private ThreadPool(String id) {
        this.name = id;
    } // End private ThreadPool() constructor

    public ThreadPool(final int sizeOfPool) {
        threads = new Thread[sizeOfPool];

        for (int i = 0; i < threads.length; ++i) {
            Thread t = new Thread(new ThreadPool(Integer.toString(i)));
            threads[i] = t;
        } // End for loop
    } // End ThreadPool() constrcutor

    public void startAllThreads() {
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }
    } // End startAllThreads() method

    public String getThreadName() {
        return this.name;
    }

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

    public void setStart(boolean status) {
        start = status;
    } // End setStart(bool) method

    public synchronized void print(int[] row, int[] col) {
        System.out.println("Row array: " + Arrays.toString(row));
        System.out.println("Column array: " + Arrays.toString(col));

        System.out.flush();
    }

    public void getProduct(int[] r, int[] c) {
        dotProduct(r, c);
    } // End getProduct() method

    public int dotProduct(int[] row, int[] col) {
        int prod = 0;
        
        for (int i = 0; i < row.length; ++i) {
            prod += row[i] * col[i];
        } // End outer for loop

        return prod;
    } // End product() method

    public void run() { 
        while(!start) { /* spin and wait for jobs to be added to the queue */
            if (jobQueue.size() != 0) {
                Job j = removeJob();

                System.out.println(this.getThreadName() + " is taking a job");
                if (j != null) {
                    getProduct(j.getRowArr(), j.getColArr());
                }
            } // End if statement
        } // End while loop      
    } // End run() method

} // End ThreadPool class
