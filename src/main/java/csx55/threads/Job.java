package csx55.threads;

public class Job {
    
    private int[] row;
    private int[] col;

    public Job() {} // End Job() constructor

    public Job(int[] r, int[] c) {
        this.row = r;
        this.col = c;
    } // End Job(r, c) constructor

    public int[] getRowArr() {
        return this.row;
    } // End getRowArr() method

    public int[] getColArr() {
        return this.col;
    } // End getColArr() method

} // End Job class