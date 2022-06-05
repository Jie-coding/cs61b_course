package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.Stopwatch;
public class PercolationStats {

    private double[] fracOfOpenSites;
    private int times;
    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        fracOfOpenSites = new double[T];
        this.times = fracOfOpenSites.length;
        for(int i = 0; i < T; i += 1){
            Percolation perc = pf.make(N);
            while(!perc.percolates()){
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                perc.open(row, col);
            }
            fracOfOpenSites[i] = (double) perc.numberOfOpenSites()/ perc.numberOfTotalSites();
        }
    }
    public double mean(){
        double sum = 0;
        for (double i : fracOfOpenSites){
            sum += i;
        }
        return sum / times;
    }
    public double stddev(){
        double sumOfStd = 0;
        double mean = mean();
        for (double i : fracOfOpenSites){
            sumOfStd += Math.pow((i - mean), 2.0);
        }
        return sumOfStd/(times - 1);
    }
    public double confidenceLow(){
        return mean() - (1.96*Math.sqrt(stddev())) / Math.sqrt(times);
    }
    public double confidenceHigh(){
        return mean() + (1.96*Math.sqrt(stddev())) / Math.sqrt(times);
    }
    public static void main(String[] args){

    }
}
