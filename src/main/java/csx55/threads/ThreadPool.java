package csx55.threads;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ThreadPool implements Runnable {

    private Thread[] threads; /* used to hold our threads */
    private Job work = null;
    // private volatile ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<>(); /* used to hold our Jobs */
    private static boolean locked;
    private static boolean lockMain = true;
    
    /* USED FOR OUR PRIVATE CONSTRUCTOR */
    private int count; /* size of our ThreadPool (number of other threads) */
    private int threadID;

    private static boolean started = false;
    private static volatile boolean start = false;

    public volatile int product;

    private ThreadPool(int size, int id) {
        this.count = size;
        this.threadID = id;
    } // End private ThreadPool constructor
    
    public ThreadPool(final int sizeOfPool) {
        this.count = sizeOfPool;
        threads = new Thread[sizeOfPool];
        this.threadID = -1; /* set it to -1 so we know it is not a thread */

        for (int i = 0; i < threads.length; ++i) {
            Thread t = new Thread(new ThreadPool(sizeOfPool, i));
            threads[i] = t;
        } // End for loop
    } // End ThreadPool() constrcutor

    public void setJob(Job j) {
        this.work = j;
    } // End setJob(j) method

    public void unleashThreads() {
        if (!started) {
            startAllThreads();
        } // End if statement

        locked = false;
        
        while (lockMain) { /* trap the main thread in the ThreadPool class */ } // End while loop
    } // End unleashThreads() method

    public void startAllThreads() {
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }
    } // End startAllThreads() method

    

    // public synchronized void print(int[] row, int[] col) {
    //     System.out.println("Row array: " + Arrays.toString(row));
    //     System.out.println("Column array: " + Arrays.toString(col));

    //     System.out.flush();
    // }

    // public void dotProduct(int[] row, int[] col) {
    //     int prod = 0;
    //     for (int i = 0; i < row.length; ++i) {
    //         prod += row[i] * col[i];
    //     } // End outer for loop

    //     this.product = prod;
    // } // End product() method

    public void run() { 
        while(!start) { /* spin and wait for jobs to be added to the queue */
            System.out.println("Thread is spinning");
            if (jobQueue.size() != 0) {
                Job j = removeJob();

                System.out.println("Thread is working");
                if (j != null) {
                    dotProduct(j.getRowArr(), j.getColArr());
                }
            } // End if statement
        } // End while loop      
    } // End run() method

} // End ThreadPool class
