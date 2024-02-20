package csx55.threads;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

public class ThreadPool {

    private Thread[] threads;
    // private volatile SynchronousQueue<Job> jobQueue = new SynchronousQueue<>();
    private ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<>();
    private int value;
    private boolean calculatingMatrices = true;
    
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

    public void setValue(int value) {
        this.value = value;
    } // End addValue() method

    public int getValue() {
        return this.value;
    } // End getValue() method

    public void dotProduct(int[] row, int[] col) {
        int  product = 0;
        for (int i = 0; i < row.length; ++i) {
            for (int j = 0; j < col.length; ++j) {
                product += row[i] * col[j];
            } // End nested for loop
        } // End outer for loop

        setValue(product);
    } // End product() method

    public void run() {        
        while (calculatingMatrices) {
            if (jobQueue.size() != 0) {
                Job j = removeJob();
                dotProduct(j.getRowArr(), j.getColArr());
            } // End if statement
        } // End while loop
    } // End run() method

} // End ThreadPool class
