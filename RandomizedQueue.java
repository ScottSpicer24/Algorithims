/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int arraySize;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[0];
        arraySize = 0;
        tail = 0;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < tail; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return tail == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // if array is empty, resize, add item, set head to 0 and tail to 1
        if (tail == 0) {
            resize(2);
            items[0] = item;
            tail = 1;
            arraySize = 2;
        }
        // check is there's space in array, if not double it, if there is add item to end and move tail
        else {
            if (tail == arraySize) {
                arraySize = tail * 2;
                resize(arraySize);
            }
            items[tail] = item;
            tail += 1;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (tail == 0) {
            throw new NoSuchElementException();
        }
        int choosenOne = StdRandom.uniformInt(0, tail);
        Item item = items[choosenOne];
        for (int i = choosenOne + 1; i < tail; i++) {
            items[i - 1] = items[i];
        }
        items[tail - 1] = null;
        tail -= 1;
        if (tail == (arraySize / 4)) {
            arraySize = arraySize / 2;
            resize(arraySize);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (tail == 0) {
            throw new NoSuchElementException();
        }
        int choosenOne = StdRandom.uniformInt(0, tail);
        return items[choosenOne];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        // pointer to traverse through list
        int[] randomCopy = new int[tail];
        int ptr = 0;

        private void randomize() {
            for (int i = 0; i < tail; i++) {
                randomCopy[i] = i;
            }
            StdRandom.shuffle(randomCopy, 0, tail);
        }


        public boolean hasNext() {
            return ptr != tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (ptr == 0) {
                randomize();
            }
            Item item = items[randomCopy[ptr]];
            ptr += 1;
            return item;
        }
    }

    public static void main(String[] args) {

    }
}
