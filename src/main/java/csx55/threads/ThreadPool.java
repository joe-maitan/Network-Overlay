package csx55.threads;

import java.util.concurrent.SynchronousQueue;

public class ThreadPool {

    private Thread[] threads;
    private SynchronousQueue<Job> jobQueue = new SynchronousQueue<>();
    private int value;
    
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
            this.jobQueue.put(j);
            System.out.println("Successfully added a job");
        } catch (InterruptedException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End addJob(j) method

    public Job removeJob() {
        Job j = null;

        try {
            j = this.jobQueue.take();
            System.out.println("Successfully removed a job");
        } catch (InterruptedException err) {
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

    public int dotProduct(int[] row, int[] col) {
        int  product = 0;
        for (int i = 0; i < row.length; ++i) {
            for (int j = 0; j < col.length; ++j) {
                product += row[i] * col[j];
            } // End nested for loop
        } // End outer for loop

        return product;
    } // End product() method

    public void run() {
        // System.out.println("Entering run method");
        boolean calculatingMatrices = true;
        
        int value = 0;
        while (calculatingMatrices) {
            if (jobQueue.size() != 0) {
                System.out.println("Thread is taking on a job");
                Job j = removeJob();
                value = dotProduct(j.getRowArr(), j.getColArr());
                setValue(value);
            }
        } // End while loop

        // System.out.println("run() - exiting while loop");
    } // End run() method

} // End ThreadPool class
