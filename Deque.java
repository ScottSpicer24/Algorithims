/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

// SHOULD HAVE A PREVIOUS NODE IN EACH NODE TO MAKE REMOVE LAST FASTER

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }


    // construct an empty deque
    public Deque() {
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst;
        oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (count == 0) {
            last = first;
        }
        count += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // if empty
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = last;
        if (count == 0) {
            last = newLast;
            first = last;
        }
        else {
            last.next = newLast;
            last = newLast;
        }
        count += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (count == 0) {
            throw new NoSuchElementException();
        }
        Node oldFirst = first;
        first = first.next;
        first.prev = null;
        count -= 1;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (count == 0) {
            throw new NoSuchElementException();
        }
        if (count == 1) {
            Item retItem = last.item;
            last = null;
            first = null;
            count -= 1;
            return retItem;
        }
        else {
            Item retItem = last.item;
            last = last.prev;
            last.next = null;
            count -= 1;
            return retItem;
            /*
            // need to traverse through the linked list pointing to the one before the current last
            Node pointer = first;
            // keep track of last item (the one you are removing) so you can return it's item
            Node oldLast = last;
            while (pointer.next != last) {
                pointer = pointer.next;
            }
            // make the last node the node that was pointing to it at start
            last = pointer;
            last.next = null;
            count -= 1;
            return oldLast.item;
            */

        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        // pointer to traverse through list
        Node pointer = first;

        // treverse through list
        public boolean hasNext() {
            return pointer != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (pointer == null) {
                throw new NoSuchElementException();
            }
            Item item = pointer.item;
            pointer = pointer.next;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {

    }

}


