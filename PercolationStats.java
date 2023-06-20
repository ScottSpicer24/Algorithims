/* *****************************************************************************
 *  Name:              Scott Spicer
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double[] thresholds;
    private double stddev;
    private double confLow;
    private double confHigh;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1) {
            throw new IndexOutOfBoundsException("n is out of bounds.");
        }
        if (trials < 1) {
            throw new IndexOutOfBoundsException("Trials is out of bounds.");
        }
        // find total num of sites
        int totalSites = n * n;
        T = trials;
        thresholds = new double[trials];
        // loop through sim
        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                // choose a site
                int choosenOneRow = StdRandom.uniformInt(1, n + 1);
                int choosenOneCol = StdRandom.uniformInt(1, n + 1);
                // check if it's blocked
                if (grid.isOpen(choosenOneRow, choosenOneCol)) {
                    continue;
                }
                // if it is open it, if it is not chosen another
                grid.open(choosenOneRow, choosenOneCol);
            }
            // if it does check threshold
            thresholds[i] = (double) grid.numberOfOpenSites() / (double) totalSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(thresholds);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(thresholds);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        confLow = mean - ((1.96 * stddev) / Math.sqrt(T));
        return confLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        confHigh = mean + ((1.96 * stddev) / Math.sqrt(T));
        return confHigh;
    }

    // test client (see below)
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        int tri = Integer.parseInt(args[1]);
        PercolationStats sim = new PercolationStats(num, tri);
        System.out.println(sim.mean);
        System.out.println(sim.stddev);
        System.out.println(sim.confLow);
        System.out.println(sim.confHigh);
    }

}
