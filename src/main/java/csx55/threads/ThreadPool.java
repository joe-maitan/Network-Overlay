package csx55.threads;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPool implements Runnable {

    private Thread[] threads; /* used to hold our threads */
    private Job work = null;
    // private volatile ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<>(); /* used to hold our Jobs */
    private static volatile boolean lockStart;
    private static volatile boolean lockFinish;
    private static volatile boolean lockMain;
    private static volatile boolean finished = false; /* when finished is true */

    private static AtomicInteger counter;
    
    /* USED FOR OUR PRIVATE CONSTRUCTOR */
    private int sizeOfPool; /* size of our ThreadPool (number of other threads) */
    private int threadID;

    private static boolean started = false;

    public volatile int product;

    private ThreadPool(int size, int id) {
        this.sizeOfPool = size;
        this.threadID = id;
    } // End private ThreadPool constructor
    
    public ThreadPool(final int size) {
        this.sizeOfPool = size;
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

        counter.set(0);
        lockMain = true;
        lockStart = false; // We allow the threads to do stuff
        
        while (lockMain) { /* main thread (MatrixThreads) is waiting */ } // End while loop

        /* Threads have finished, reset them */
        lockStart = true;
        counter.set(0);
        lockFinish = false;
        lockMain = true;

        while (lockMain == true) { /* waiting for reset... */ } // End while loop

        lockMain = true;
        lockFinish = true;
        // The threads are ready to be unleashed again
    } // End unleashThreads() method

    public void startAllThreads() {
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }
    } // End startAllThreads() method

    public void endPool() {
        finished = true;
    } // End endPool() method

    public void run() {
        while (!finished) {
            if (counter.incrementAndGet() == sizeOfPool) {
                // Last thread enters
                // All other threads have passed this
                // unlock the main thread
                lockMain = false;
            } 

            while (lockStart) { /* spin and do nothing */ }

            // DO WORK SECTION

            int stride = (work.getHowManyTasks() + (sizeOfPool - 1)) / sizeOfPool;
            int start = threadID * stride;
            int end = start + stride;

            if (start >= work.getHowManyTasks()) {
                start = work.getHowManyTasks();
            }

            if (end >= work.getHowManyTasks()) {
                end = work.getHowManyTasks();
            }

            if (counter.incrementAndGet() == sizeOfPool) {
                lockMain = false;
            }
        }
    } // End run() method 

} // End ThreadPool class