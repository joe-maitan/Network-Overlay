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
        } // End for loop
    } // End ThreadPool() constrcutor

    public void addJob(Job j) {
        this.jobQueue.add(j);
    } // End addJob(j) method

    public Job removeJob() {
        return this.jobQueue.poll();
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
        int value = 0;
        while (jobQueue.size() != 0) {
            Job j = removeJob();
            value = dotProduct(j.getRowArr(), j.getColArr());
            setValue(value);
        } // End while loop
    } // End run() method

} // End ThreadPool class
