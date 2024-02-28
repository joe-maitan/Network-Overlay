package csx55.threads;

public class Job {
    
    private int[][] m1;
    private int[][] m2;
    private int[][] productMatrix;
    private int rowIndex;
    private int columnIndex;

    public Job() {} // End Job() constructor

    public Job(int[][] one, int[][] two, int[][] prod, int rIndex, int cIndex) {
        this.m1 = one;
        this.m2 = two;
        this.productMatrix = prod;
        this.rowIndex = rIndex;
        this.columnIndex = cIndex;
    } // End Job(r, c) constructor

    public int[][] getMatrixOne() {
        return this.m1;
    }

    public int[][] getMatrixTwo() {
        return this.m2;
    }

    public int[][] getProductMatrix() {
        return this.productMatrix;
    }

    public int getRow() {
        return this.rowIndex;
    } // End getRowArr() method

    public int getCol() {
        return this.columnIndex;
    } // End getColArr() method

} // End Job class
