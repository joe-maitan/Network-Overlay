package csx55.threads;

public class ThreadPool {

    private Thread[] threads;
    // Schronized queue or concurrnet queue
    
    public ThreadPool(final int sizeOfPool) {
        threads = new Thread[sizeOfPool];

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(this::run);
        } // End for loop
    } // End ThreadPool() constrcutor

    public void run() {
        // sycnrhonize queue, when a job is avaliable take it.
        // Get a new job from the q
        // Do the job (dot product here)
        // repeat
    } // End run() method

} // End ThreadPool class
