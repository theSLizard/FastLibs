package org.theSLizard;

/**
 * A tiny wrapper around an int array that exposes a quick‑sort routine.
 *
 * The original Ruby code used destructive methods (`partition!`, `quicksort!`)
 * and mutates the underlying array.  In Java we do the same – the array is
 * sorted in place – but we use ordinary method names without the trailing
 * exclamation mark (that’s just a Ruby convention).
 */
public class SortableArray {

    /* The array that will be sorted in place */
    private final int[] array;

    /**
     * Create a SortableArray that wraps an existing int[].
     *
     * @param array the array to sort
     */
    public SortableArray(int[] array) {
        this.array = array;
    }

    /* ------------------------------------------------------------------ *
     * Public API
     * ------------------------------------------------------------------ */

    /**
     * Return the value that would appear at position {@code kthLowestIndex}
     * if the array were sorted.
     *
     * @param kthLowestIndex 0‑based rank (0 → smallest element)
     * @return the k‑th smallest value
     */
    public int quickselect(int kthLowestIndex) {
        return quickselect(kthLowestIndex, 0, array.length - 1);
    }

    /* ------------------------------------------------------------------ *
     * Internal recursive routine – matches the Ruby logic you posted.
     * ------------------------------------------------------------------ */

    /**
     * Recursive QuickSelect working on a sub‑array [left … right].
     */
    private int quickselect(int kthLowestIndex, int left, int right) {

        // Base case: sub‑array has one element
        if (right - left <= 0) {
            return array[left];
        }

        // Partition the sub‑array and get the pivot position.
        int pivotIndex = partition_quickselect(left, right);

        // Decide which side to recurse on.
        if (kthLowestIndex < pivotIndex) {                     // target is left of pivot
            return quickselect(kthLowestIndex, left, pivotIndex - 1);
        } else if (kthLowestIndex > pivotIndex) {              // target is right of pivot
            return quickselect(kthLowestIndex, pivotIndex + 1, right);
        } else {                                               // pivot itself is the answer
            return array[pivotIndex];
        }
    }

    /* ------------------------------------------------------------------ *
     * Helper methods
     * ------------------------------------------------------------------ */

    /**
     * Lomuto partition scheme.
     * Moves all elements ≤ pivot to the left of the pivot and returns its final index.
     */
    private int partition_quickselect(int left, int right) {
        int pivotValue = array[right];          // choose last element as pivot
        int i = left - 1;                       // place for swapping

        for (int j = left; j < right; j++) {
            if (array[j] <= pivotValue) {       // keep elements ≤ pivot on the left
                i++;
                swap(i, j);
            }
        }

        // Put pivot in its correct position
        swap(i + 1, right);
        return i + 1;
    }

    /** Simple array element swap. */
    private void swap(int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }



    /* ------------------------------------------------------------------ */
    /*  Public API – starts the quick‑sort on the whole array              */
    /* ------------------------------------------------------------------ */

    /**
     * Sorts the entire underlying array in ascending order.
     */
    public void quickSort() {
        if (array.length > 1) {          // nothing to do for 0 or 1 elements
            quickSort(0, array.length - 1);
        }
    }

    /* ------------------------------------------------------------------ */
    /*  Private helpers – exactly the same logic as in your Ruby code     */
    /* ------------------------------------------------------------------ */

    /**
     * Recursive quick‑sort routine.
     *
     * @param left  leftmost index of the sub‑array to sort
     * @param right rightmost index of the sub‑array to sort
     */
    private void quickSort(int left, int right) {
        // Base case: 0 or 1 element – already sorted
        if (right - left <= 0) {
            return;
        }

        System.out.println("left = " + left);
        System.out.println("right = " + right);
        System.out.println("the Array: " + java.util.Arrays.toString(this.array));

        // Partition the sub‑array and get back the final pivot index.
        int pivotIndex = partition(left, right);
        System.out.println("pivotIndex = " + pivotIndex);
        System.out.println("quickSort, " + "left = " + left + " right = " + (pivotIndex - 1));
        System.out.println("quickSort, " + "left = " + (pivotIndex + 1) + " right = " + right);

        // Recursively sort the two partitions.
        quickSort(left, pivotIndex - 1);
        quickSort(pivotIndex + 1, right);

    }

    /**
     * Partition a sub‑array so that every element left of the pivot
     * is < pivot and every element right of the pivot is > pivot.
     *
     * @param left  index of the leftmost element to consider
     * @param right index of the rightmost element (the pivot)
     * @return the final position of the pivot after partitioning
     */
    private int partition(int left, int right) {
        // Pick the right‑most element as the pivot.
        int pivotIndex = right;
        int pivotValue = array[pivotIndex];

        // Move the right pointer one step to start just before the pivot.
        right -= 1;

        while (true) {
            /* Move left pointer rightwards until we find a value
               that is >= the pivot. */
            while (array[left] < pivotValue) {
                left++;
            }

            /* Move right pointer leftwards until we find a value
               that is <= the pivot. */
            while (right >= 0 && array[right] > pivotValue) {
                right--;
            }

            // If the pointers have crossed, break – the partitioning is done.
            if (left >= right) {
                break;
            } else {
                /* Swap the out‑of‑place values so that
                   everything left of 'right' stays < pivot,
                   and everything right of 'left' stays > pivot. */
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;

                // Advance the left pointer to continue scanning.
                left++;
            }
        }

        /* Place the pivot element in its final sorted position
           – which is exactly one place right of the 'right' pointer. */
        int temp = array[pivotIndex];
        array[pivotIndex] = array[right + 1];
        array[right + 1] = temp;

        // Return the index where the pivot ended up.
        return right + 1;
    }

    /* ------------------------------------------------------------------ */
    /*  Convenience accessor – lets you inspect the sorted array           */
    /* ------------------------------------------------------------------ */

    /**
     * @return a reference to the underlying array (sorted after quickSort)
     */
    public int[] getArray() {
        return array;
    }
}

