// 以后就按照这个文件写test file
package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.introcs.Stopwatch;

public class Percolation {

    private WeightedQuickUnionUF WQU; // WQU is used to store the connectivity of different sites.
    private WeightedQuickUnionUF WQUnoBottom; // For fixing backwash problem
    private int numOfTotalSites;
    private int numOfOpenSite;
    private int length;
    private int virtualTop;
    private int virtualBottom;
    public int[][] grid; // grid is used to store the status of each site (either 0 or 1)

    public Percolation(int N){

        numOfOpenSite = 0;
        numOfTotalSites = N * N;
        length = N;
        virtualTop = numOfTotalSites;
        virtualBottom = numOfTotalSites + 1;
        WQU = new WeightedQuickUnionUF(numOfTotalSites + 2);
        WQUnoBottom = new WeightedQuickUnionUF(numOfTotalSites + 1);
        grid = new int[N][N];
        if (N <= 0){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < length ; i += 1){
            for (int j = 0; j < length; j += 1){
                grid[i][j] = -1;
            }
        }
    }

    private void checkValidity(int row, int col) {
        boolean validity = (row >= 0 && row <= length - 1 && col >= 0 && col <= length - 1);
        if (!validity){
            throw new IndexOutOfBoundsException();
        }
    }

    public int xyTo1d(int row, int col){
        checkValidity(row, col);
        return length * row + col;
     }

     private void setStatusToOpen(int row, int col){
        grid[row][col] = 1;
     }

     public int getStatus(int row, int col){
        checkValidity(row, col);
        return grid[row][col];
     }

    public void open(int row, int col) {
        checkValidity(row, col);
        if (!isOpen(row, col) && !isFull(row, col)) {
            setStatusToOpen(row, col);
            numOfOpenSite += 1;
            connectOpen(row, col);
        }
    }

    private void connectOpen(int row, int col){
            int index = xyTo1d(row, col);
            // connect the first/last row site to virtualTop/virtualBottom
            if (row == 0) {
                WQU.union(index, virtualTop);
                WQUnoBottom.union(index, virtualTop);
            } else if (row == length - 1) {
                WQU.union(index, virtualBottom);
            }
            // to the upper site
            if (row - 1 >= 0 && isOpen(row - 1, col)) {
                WQU.union(index, xyTo1d(row - 1, col));
                WQUnoBottom.union(index, xyTo1d(row - 1, col));
            }
            // to the lower site
            if (row + 1 <= length - 1 && isOpen(row + 1, col)) {
                WQU.union(index, xyTo1d(row + 1, col));
                WQUnoBottom.union(index, xyTo1d(row + 1, col));
            }
            // to the left site
            if (col - 1 >= 0 && isOpen(row, col - 1)) {
                WQU.union(index, xyTo1d(row, col - 1));
                WQUnoBottom.union(index, xyTo1d(row, col - 1));
            }
            // to the right site
            if (col + 1 <= length - 1 && isOpen(row, col + 1)) {
                WQU.union(index, xyTo1d(row, col + 1));
                WQUnoBottom.union(index, xyTo1d(row, col + 1));
            }
        }

    public boolean isOpen(int row, int col) {
        checkValidity(row, col);
        return grid[row][col] == 1; // Check whether the site is open.
    }

    public boolean isFull(int row, int col){
        checkValidity(row, col);
        int index = xyTo1d(row, col);
        return WQUnoBottom.connected(index, virtualTop);
    }

    public int numberOfOpenSites(){
        return numOfOpenSite;
    }

    public int numberOfTotalSites(){
        return numOfTotalSites;
    }

    public boolean percolates(){
        return WQU.connected(virtualTop, virtualBottom);
    }

    public static double runtime(int N, int T){
        Stopwatch timer = new Stopwatch();
        PercolationFactory pf = new PercolationFactory();
        PercolationStats percStats = new PercolationStats(N, T, pf);
        return timer.elapsedTime();

    }

    public boolean isConnected(int p, int q){
        return this.WQU.connected(p, q);
    }
    public static void main(String[] args){
        int N = 50;
        int T = 30;
        System.out.println("Running time: start from N = 10, T_fixed = 30");
        for (int i = 1; i < 6; i += 1){
            double time = runtime(i*N, T);
            System.out.println(String.format("N: %d, T: %d, Time: %.3f", N*i, T, time));
        }

        System.out.println("Running time: start from N_fixed = 10, T = 30");
        for (int i = 1; i < 6; i += 1){
            double time = runtime(N, i*T);
            System.out.println(String.format("N: %d, T: %d, Time: %.3f", N, i*T, time));
        }
    }
}
