package org.theSLizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Xpmts {

    /** Recursive Fibonacci – exactly the same logic as your Python code */
    public static int fib(int n) {
        System.out.println("fib called with n = " + n);
        if (n == 0 || n == 1) {   // base cases
            System.out.println("fib returned, base case, n = " + n);
            return n;
        }
        return fib(n - 2) + fib(n - 1);
    }

    /** Public helper – starts printing at depth 0 */
    public static void printFibTree(int n) {
        printFibTreeHelper(n, 0);
    }

    /**
     * Recursively prints one node and then its two children.
     *
     * @param n      the argument of the fib call being printed
     * @param depth  how deep we are in the tree (root has depth 0)
     */
    private static void printFibTreeHelper(int n, int depth) {
        // indent according to depth – 2 spaces per level
        for (int i = 0; i < depth * 2; i++) System.out.print(' ');

        System.out.println("fib(" + n + ")");

        // Base case: leaf nodes stop recursing further
        if (n == 0 || n == 1) {
            return;
        }

        // The original recursive order is fib(n-2) first, then fib(n-1)
        printFibTreeHelper(n - 2, depth + 1);
        printFibTreeHelper(n - 1, depth + 1);
    }

    // Fibonacci with memoization
    private static int[] memo;          // cache for computed values

    public static int fibMemo(int n) {
        if (memo == null || memo.length <= n) {
            // initialise/resize the cache
            System.out.println(" initialise/resize the cache, n = " + n);
            memo = new int[Math.max(2, n + 1)];
            Arrays.fill(memo, -1);      // -1 means “not computed yet”
        }

        if (memo[n] != -1) {            // already cached?
            System.out.println("already cached memo[n]: n= " + n + " memo[n]= " + memo[n]);
            return memo[n];
        }

        if (n == 0 || n == 1) {         // base case
            System.out.println("base case, storing memo[n]: " + "n= " + n + " memo[n]= " + memo[n]);
            memo[n] = n;
            return n;
        }

        // compute, cache and return
        memo[n] = fibMemo(n - 2) + fibMemo(n - 1);

        System.out.println("returning memo[n]: " + "n= " + n + " memo[n]= " + memo[n]);
        return memo[n];
    }

    // bottom-up fibonacci
    //---------------------
    /* -------------------------------------------------------------- */
    /** Bottom‑up, iterative Fibonacci – O(n) time, O(1) space */
    public static int bottom_up_fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");

        // base cases
        if (n == 0) return 0;
        if (n == 1) return 1;

        // start with the two seed values
        int prevPrev = 0;   // fib(0)
        int prev     = 1;   // fib(1)

        for (int i = 2; i <= n; i++) {
            int current = prevPrev + prev; // fib(i) = fib(i-2) + fib(i-1)
            prevPrev = prev;
            prev     = current;
        }
        return prev;   // at loop end, prev == fib(n)
    }

    /* -------------------------------------------------------------- */
    /** Build and return the full sequence up to n (inclusive). */
    public static int[] buildSequence(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");

        int[] seq = new int[n + 1];
        seq[0] = 0;
        if (n == 0) return seq;

        seq[1] = 1;
        for (int i = 2; i <= n; i++) {
            seq[i] = seq[i - 2] + seq[i - 1];
        }
        return seq;
    }

    /* -------------------------------------------------------------- */
    /** Pretty‑print the sequence. */
    public static void printSequence(int[] seq) {
        for (int i = 0; i < seq.length; i++) {
            System.out.printf("fib(%d) = %d%n", i, seq[i]);
        }
    }

    // ------------------------------------------------------------
    // ------------------------------------------------------------

    public static List<String> anagramsOf(String s) {
        // Base case: one character – only one anagram
        if (s.length() == 1) {
            List<String> base = new ArrayList<>();
            base.add(s);
            return base;
        }

        // Recursive step:
        // 1. Take the first character
        char first = s.charAt(0);

        // 2. Recursively find anagrams of the rest of the string
        String rest = s.substring(1);          // e.g. "bcd" if s == "abcd"
        List<String> subAnagrams = anagramsOf(rest);

        // 3. Insert 'first' into every position of each sub‑anagram
        List<String> result = new ArrayList<>();

        for (String sub : subAnagrams) {
            int len = sub.length();
            // We can insert at positions 0 … len (inclusive)
            for (int i = 0; i <= len; i++) {
                StringBuilder sb = new StringBuilder(sub);
                sb.insert(i, first);           // same as Ruby's `insert`
                result.add(sb.toString());
            }
        }

        return result;
    }


    public static Integer number_of_paths(Integer n) {
        System.out.println("n = " + n);

        if (n <= 0) {
            System.out.println("Ret. ");
            return 0;
        }
        if (n == 1) {
            System.out.println("Ret. ");
            return 1;
        }
        if (n == 2) {
            System.out.println("Ret. ");
            return 2;
        }
        if (n == 3) {
            System.out.println("Ret. ");
            return 3;
        }

        return number_of_paths(n-3) + number_of_paths(n-2) + number_of_paths(n-1);
    }

    // recursive string reversing (with slicing)
    public static String recurse(String str) {
        if (1 == str.length()) {
            System.out.println("str: " + str);
            return str;
        }
        System.out.println("str.charAt(0): " + str.charAt(0) + " Substring is: " + str.substring(1) );
        return recurse(str.substring(1)) + str.charAt(0);
    }

    // recursive string reversing (travelling index based)
    private static String recurse_noslice(String str, Character ch, Integer index) {
        if (index == str.length()) {
            System.out.println("ch: " + ch);
            return ch.toString();
        }
        System.out.println("ch: " + ch);
        return recurse_noslice(str, str.charAt(index++), index++) + ch.toString();
    }

    public static String recurse_noslice(String str) {
        return recurse_noslice(str, str.charAt(0), 1);
    }


    public static List<Object> generic_array = new ArrayList<>(Arrays.asList(
            1, 2, 3,

            Arrays.asList(4, 5, 6),          // inner list

            7,

            // [8, [9,10,11,[12,13,14]]]
            Arrays.asList(
                    8,
                    Arrays.asList(
                            9, 10, 11,
                            Arrays.asList(12, 13, 14)
                    )
            ),

            // [15,…,32]
            Arrays.asList(
                    15, 16, 17, 18, 19,
                    Arrays.asList(
                            20, 21, 22,
                            Arrays.asList(
                                    23, 24, 25,
                                    Arrays.asList(26, 27, 29)
                            ), 30, 31
                    ),
                    32
            ),

            33
    ));

    public static Object[] raw_array = new Object[]{
            1, 2, 3,
            new Integer[]{4, 5, 6},
            7,

            // [8, [9, 10, 11, [12, 13, 14]]]
            new Object[]{
                    8,
                    new Object[]{
                            9, 10, 11,
                            new Integer[]{12, 13, 14}
                    }
            },

            // [15, 16, 17, 18, 19, [20, 21, 22, [23, 24, 25, [26, 27, 29]], 30, 31], 32]
            new Object[]{
                    15, 16, 17, 18, 19,
                    new Object[]{
                            20, 21, 22,
                            new Object[]{
                                    23, 24, 25,
                                    new Integer[]{26, 27, 29}
                            }, 30, 31
                    }, 32
            },

            33
    };

    public void print_raw(Object[] array) {
        //
        for (int x = 0; x < array.length; x++) {
            if (array[x] instanceof Object[]) {
                // dive into sub-arrays
                print_raw((Object[])array[x]);
            } else {
                // print the number
                System.out.println(array[x]);
            }
        }
        //
    }

    public void print_generic(List<?> array) {
        //
        for (Object elem : array) {          // iterate over each item
            if (elem instanceof List<?>) {   // it’s a nested list → recurse
                @SuppressWarnings("unchecked")
                List<Object> sub = (List<Object>) elem;  // cast is safe after the check
                print_generic(sub);
            } else {
                System.out.println(elem);    // leaf element – an Integer, String, …
            }
        }
        //
    }


    public static String[] someStrings = new String[]{
            "Lalala", "Tralalala", "Shalala"
    };

    public static Integer StringCount(String[] array){
        // base case
        if (array.length == 1) {
            System.out.println("StringCount Returned - array length is 1");
            return array[0].length();
        }

        return array[0].length() + StringCount(Arrays.copyOfRange(array, 1, array.length));
    }


    public static Integer StringCount_noSlice(String[] array) {
        return StringCount_noSlice_helper(array, 0);
    }

    private static Integer StringCount_noSlice_helper(String[] array, Integer index){
        // base case
        if (index == array.length - 1) {
            return array[index].length();
        }

        // important: array is actually on the heap, so only its address will be passed in recursively onto the stack
        return array[index].length() + StringCount_noSlice_helper(array, ++index);
    }

    /**
     * Tail‑recursive factorial.
     *
     * @param n the number whose factorial to compute (n >= 0)
     * @return n! as a long value
     */
    public static long factorial(int n) {
        // Start recursion with i = 1 and product = 1
        return factorial(n, 1, 1L);
    }

    /**
     * Helper that carries the two accumulators.
     *
     * @param n       original number
     * @param i       current multiplier (starts at 1)
     * @param product accumulated product so far
     * @return n! as a long value
     */
    private static long factorial(int n, int i, long product) {
        if (i > n) {          // base case: all multipliers processed
            return product;
        }
        // recursive step: multiply and advance the counter
        // return factorial(n, i + 1, product * i);
        return product * factorial(n, i + 1, i); // I like this better
    }

}
