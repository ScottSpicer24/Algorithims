/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int top;
    private int bot;
    private int n;
    private boolean[] status;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Neg number");
        }
        this.n = n;
        top = 0;
        bot = n * n + 1;
        grid = new WeightedQuickUnionUF((n * n) + 2);
        status = new boolean[(n * n) + 2];
        status[top] = true;
        status[bot] = true;
        openCount = 0;
    }

    private int indexOf(int row, int col) {
        // check bounds
        if (row < 1) {
            throw new IllegalArgumentException("Row is out of bounds too low.");
        }
        if (col < 1) {
            throw new IllegalArgumentException("Col is out of bounds too low.");
        }
        if (row > n) {
            throw new IllegalArgumentException("Row is out of bounds too high.");
        }
        if (col > n) {
            throw new IllegalArgumentException("Col is out of bounds too high.");
        }
        return ((row - 1) * n) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = indexOf(row, col);
        status[index] = true;
        openCount += 1;
        if (row == 1) {
            grid.union(top, index);
        }
        if (row == n) {
            grid.union(bot, index);
        }
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                grid.union(index, indexOf(row - 1, col));
            }
        }
        if (row < n) {
            if (isOpen(row + 1, col)) {
                grid.union(index, indexOf(row + 1, col));
            }
        }
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                grid.union(index, indexOf(row, col - 1));
            }
        }
        if (col < n) {
            if (isOpen(row, col + 1)) {
                grid.union(index, indexOf(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return status[indexOf(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (grid.find(top) == grid.find(indexOf(row, col)));
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return grid.find(top) == grid.find(bot);
    }


    public static void main(String[] args) {

    }
}
