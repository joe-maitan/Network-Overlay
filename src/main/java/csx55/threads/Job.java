package csx55.threads;

public class Job {
    
    private int[] row;
    private int[] col;

    private int howManyTasks; /* THE NUMBER OF DOT PRODUCTS TO BE COMPUTED */

    public Job(int numTasks) {
        this.howManyTasks = numTasks;
    } // End Job() constructor

    public int getHowManyTasks() {
        return this.howManyTasks;
    } // End getHowManyTasks() method

    public void doDotProduct(int[] row, int[] col) {
        int prod = 0;

        for (int i = 0; i < row.length; ++i) {
            prod += row[i] * col[i];
        } // End outer for loop
    } // End doDotProduct(i) method

    public void addToQueue() {}

    public int[] getRowArr() {
        return this.row;
    } // End getRowArr() method

    public int[] getColArr() {
        return this.col;
    } // End getColArr() method

} // End Job class