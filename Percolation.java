/* *****************************************************************************
 *  Name:              Scott Spicer
 The model. 
 We model a percolation system using an n-by-n grid of sites. 
 Each site is either open or blocked. A full site is an open site that can be 
 connected to an open site in the top row via a chain of neighboring 
 (left, right, up, down) open sites. We say the system percolates if there is a 
 full site in the bottom row. In other words, a system percolates if we 
 fill all open sites connected to the top row and that process fills some open
 site on the bottom row. (For the insulating/metallic materials example, 
 the open sites correspond to metallic materials, so that a system that 
 percolates has a metallic path from top to bottom, with full sites conducting. 
 For the porous substance example, the open sites correspond to empty space 
 through which water might flow, so that a system that percolates 
 lets water fill open sites, flowing from top to bottom.)

 The problem. 
 In a famous scientific problem, researchers are interested in the following 
 question: if sites are independently set to be open with probability p 
 (and therefore blocked with probability 1 − p), 
 what is the probability that the system percolates?
 When n is sufficiently large, there is a threshold value p* such that 
 when p < p* a random n-by-n grid almost never percolates, and when p > p*, 
 a random n-by-n grid almost always percolates. No mathematical solution for 
 \determining the percolation threshold p* has yet been derived. 
 Your task is to write a computer program to estimate p*.
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
