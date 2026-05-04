package org.theSLizard;

import java.io.File;
import java.util.*;

import static org.theSLizard.CoinGame.gameWinner;
import static org.theSLizard.CoinGame.gameWinnerOptimised;
import static org.theSLizard.FileUtils.findDirectories;
import static org.theSLizard.Xpmts.factorial;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void recurseTestBox(Integer n) {
        if (n < 0)  {
            System.out.print("-- unwinding: ");
            return;
        }
        System.out.print(n + " ");
        recurseTestBox(n - 1); // push onto the stack (the "stack frame")

        System.out.print(n + " ");
    }

    public static boolean sumOf2(Integer[] integers, Integer hasToBe) {
        //Map<Integer, Boolean> map = new HashMap<>();
        Set<Integer> map = new HashSet<>(); // HashSet here is more efficient
        for (Integer integer : integers) {
            // if (map.getOrDefault((hasToBe - integers[x]), false)) {
            if (map.contains(hasToBe - integer)) {
                System.out.println("We found 2 numbers that sum up to "
                        + hasToBe + ": " + integer + " + " + (hasToBe - integer));
                return true;
            } else {
                //map.put(integers[x], Boolean.TRUE);
                map.add(integer);
            }
        }
        return false;
    }

    public static void main(String[] args) {


        System.out.println(gameWinner(4, "you"));  // Should print who wins with 5 coins starting as "you"
        System.out.println(gameWinnerOptimised(4, "you"));  // Should print who wins with 5 coins starting as "you"


        System.out.println("Starting with you ..");

        for(int x = 0; x < 20; x++) {
            String recursiveMethod = gameWinner(x, "you");
            String optimisedMethod = gameWinnerOptimised(x, "you");
            if (recursiveMethod.equals(optimisedMethod)) {
                System.out.println("Pass for numberOfCoins = " + x + " Winner is: " + recursiveMethod);
            } else {
                System.out.println("Failed for numberOfCoins = " + x + " Winner is: " + recursiveMethod);
            }
        }

        System.out.println("Starting with them ..");

        for(int x = 0; x < 20; x++) {
            String recursiveMethod = gameWinner(x, "them");
            String optimisedMethod = gameWinnerOptimised(x, "them");
            if (recursiveMethod.equals(optimisedMethod)) {
                System.out.println("Pass for numberOfCoins = " + x + " Winner is: " + recursiveMethod);
            } else {
                System.out.println("Failed for numberOfCoins = " + x + " Winner is: " + recursiveMethod);
            }
        }

        Integer hasToBe = 10;
        boolean fnd = sumOf2(new Integer[]{1, 2, 3, 4, 5, 6, 7}, hasToBe);
        if (fnd) {
            System.out.println("2 numbers found that add up to " + hasToBe);
        } else {
            System.out.println("2 numbers NOT found that add up to " + hasToBe);
        }

        boolean fnd2 = sumOf2(new Integer[]{1, 2, 3, 4, 5, 0, 11}, hasToBe);
        if (fnd2) {
            System.out.println("2 numbers found that add up to " + hasToBe);
        } else {
            System.out.println("2 numbers NOT found that add up to " + hasToBe);
        }

        recurseTestBox(7);

        City atlanta   = new City("Atlanta");
        City boston    = new City("Boston");
        City chicago   = new City("Chicago");
        City denver    = new City("Denver");
        City elPaso    = new City("El Paso");

        // Build directed edges (you need to add the reverse if you want two‑way travel)
        atlanta.addRoute(boston, 100);
        atlanta.addRoute(denver, 160);

        boston.addRoute(chicago, 120);
        boston.addRoute(denver, 180);

        chicago.addRoute(elPaso, 80);

        denver.addRoute(chicago, 40);
        denver.addRoute(elPaso, 140);

        elPaso.addRoute(boston, 100);

        // Find cheapest path from Atlanta to El Paso
        List<String> route = Dijkstra.shortestPath(atlanta, elPaso);

        System.out.println("Cheapest route: " + String.join(" -> ", route));

        // Starting from Atlanta to El Paso
        List<String> shortestPath = DijkstraPQ.shortestPath(atlanta, elPaso);
        System.out.println("Shortest Path: " + String.join(", ", shortestPath));


        SocialGraph sg = new SocialGraph();

        sg.addFriendship("Alice", "Bob");
        sg.addFriendshipManual("Alice", "Diana");
        sg.addFriendship("Alice", "Fred");
        sg.addFriendship("Bob", "Cynthia");
        sg.addFriendshipManual("Bob", "Diana");
        sg.addFriendshipManual("Bob", "Diana");
        sg.addFriendship("Cynthia", "Bob");
        sg.addFriendship("Diana", "Fred");
        sg.addFriendship("Elise", "Fred");
        // Fred's friendships are already covered by the above

        System.out.println("Alice's friends: " + sg.getFriends("Alice"));
        System.out.println("Are Bob and Diana friends? " + sg.isFriend("Bob", "Diana"));
        System.out.println("Are Alice and Elise friends? " + sg.isFriend("Alice", "Elise"));

        System.out.println("Depth-first traversal starting from Alice:");
        sg.dfsTraverse("Alice");

        System.out.println("\nSearching for Fred starting from Alice:");
        String found = sg.dfs("Alice", "Fred");
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Not found");
        }

        System.out.println("\nSearching for NonExistent starting from Alice:");
        found = sg.dfs("Alice", "NonExistent");
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Not found");
        }


        // Example of finding all friends of friends
        String target = "Alice";
        Set<String> friendsOfFriends = new HashSet<>();
        for (String friend : sg.getFriends(target)) {
            friendsOfFriends.addAll(sg.getFriends(friend));
        }
        System.out.println("Friends of Alice's friends: " + friendsOfFriends);

        Basket<Integer> rootNode = new Basket<Integer>(50, 128);
        Basket<Integer> leftNode = new Basket<Integer>(25, 64);
        Basket<Integer> rightNode = new Basket<Integer>(75, 368);

        FasTree myTree = new FasTree(rootNode, leftNode, rightNode);

        Basket<Integer> newBasket = new Basket<Integer>(33, 512);
        myTree.addBasket(newBasket);

        newBasket = new Basket<Integer>(21, 791);
        myTree.addBasketRecursive(newBasket);

        newBasket = new Basket<Integer>(17, 316);
        myTree.addBasket(newBasket);

        newBasket = new Basket<Integer>(72, 317);
        myTree.addBasketRecursive(newBasket);

        newBasket = new Basket<Integer>(73, 318);
        myTree.addBasket(newBasket);

        newBasket = new Basket<Integer>(85, 319);
        myTree.addBasket(newBasket);

        Basket<Integer> retrievedNode = myTree.retrieveBasketByIndex(21);
        System.out.println("Retrieved basket [21] contents is: " + retrievedNode.getData());

        retrievedNode = myTree.retrieveBasketByIndexRecursive(73);
        System.out.println("Retrieved basket [73] contents is: " + retrievedNode.getData());

        retrievedNode = myTree.retrieveBasketByIndex(87); // this does not exist
        System.out.println("Retrieved basket [87] contents is: "
                + ((retrievedNode != null) ? retrievedNode.getData(): "Not Found"));

        retrievedNode = myTree.retrieveBasketByIndexRecursive(17);
        System.out.println("Retrieved basket [17] contents is: " + retrievedNode.getData());

        retrievedNode = myTree.retrieveBasketByIndex(72);
        System.out.println("Retrieved basket [72] contents is: " + retrievedNode.getData());

        FasTree<String> t = new FasTree<>(new Basket<>(10, "root"),
                new Basket<>(5,  "left"),
                new Basket<>(15, "right"));

        t.addBasket(new Basket<>(12, "l.right"));
        t.addBasket(new Basket<>(20, "r.right"));

        List<Basket<String>> inorder = t.inorder();
        List<Basket<String>> postorder = t.postorder();

        System.out.println("In order: ");
        for(Basket<String> basket: inorder) {
            System.out.print(basket.getIndex() + ":" + basket.getData() + ", ");
        }

        System.out.println();
        System.out.println("Post order: ");
        for(Basket<String> basket: postorder) {
            System.out.print(basket.getIndex() + ":" + basket.getData() + ", ");
        }

        //System.out.println(t.inorder() );   // [5, 10, 12, 15, 20]
        //System.out.println(t.postorder());  // [5, 12, 20, 15, 10] (left subtree first)

        UniversalSerialMemory usm = new UniversalSerialMemory();
        UniversalSerialMemory.Cell<Integer> cell = new UniversalSerialMemory.Cell<Integer>(10);
        UniversalSerialMemory.Cell<Integer> cell2 = new UniversalSerialMemory.Cell<Integer>(20);
        UniversalSerialMemory.Cell<Integer> cell3 = new UniversalSerialMemory.Cell<Integer>(30);
        UniversalSerialMemory.Cell<Integer> cell4 = new UniversalSerialMemory.Cell<Integer>(40);
        UniversalSerialMemory.Cell<Integer> cell5 = new UniversalSerialMemory.Cell<Integer>(50);
        UniversalSerialMemory.Cell<Integer> cell7 = new UniversalSerialMemory.Cell<Integer>(70);

        usm.appendCell(cell);
        usm.appendCell(cell2);
        usm.appendCell(cell3);

        usm.prependCell(cell4);
        usm.prependCell(cell5);
        usm.prependCell(cell7);

        List<Integer> dumpedCells = usm.dumpCells();
        for(Integer x: dumpedCells) {
            System.out.println("Cell: " + x);
        }

        System.out.println("Lower index: " + usm.getLowerIndex());
        System.out.println("Upper index: " + usm.getUpperIndex());

        System.out.println("Reading memory cell at index 1 = " + usm.readMemory(1));
        System.out.println("Reading memory cell at index -1 = " + usm.readMemory(-1));
        System.out.println("Reading memory cell at index -2 = " + usm.readMemory(-2));
        System.out.println("Reading memory cell at index 2 = " + usm.readMemory(2));
        System.out.println("Reading memory cell at index 0 = " + usm.readMemory(0));

        System.out.println("Reading memory cell at lower index = " + usm.readMemory(usm.getLowerIndex()));
        System.out.println("Reading memory cell at upper index = " + usm.readMemory(usm.getUpperIndex()));

        usm.writeMemory(1, 72);

        for(Object x: usm.dumpCells()) {
            System.out.println("Cell: " + x);
        }

        System.out.println("Inserting 27 at position 1");
        usm.wedgeMemory(1, 27);
        usm.wedgeMemory(-1, 52);
        usm.wedgeMemory(-2, 128);
        usm.wedgeMemory(-3, 24); // there will never be a -3 index, unless we do a pre-pend


        for(Object x: usm.dumpCells()) {
            System.out.println("Cell: " + x);
        }

        System.out.println("Using it as a queue");
        usm.enqueue(101);
        usm.enqueue(177);
        usm.enqueue(335601);


        UniversalSerialMemory usmPriority = new UniversalSerialMemory();
        UniversalSerialMemory.Cell<Integer> prcell = new UniversalSerialMemory.Cell<Integer>(10);
        UniversalSerialMemory.Cell<Integer> prcell2 = new UniversalSerialMemory.Cell<Integer>(20);
        UniversalSerialMemory.Cell<Integer> prcell3 = new UniversalSerialMemory.Cell<Integer>(30);
        UniversalSerialMemory.Cell<Integer> prcell4 = new UniversalSerialMemory.Cell<Integer>(40);
        UniversalSerialMemory.Cell<Integer> prcell5 = new UniversalSerialMemory.Cell<Integer>(50);
        UniversalSerialMemory.Cell<Integer> prcell7 = new UniversalSerialMemory.Cell<Integer>(70);

        usmPriority.appendCell(prcell);
        usmPriority.appendCell(prcell2);
        //usmPriority.appendCell(prcell3); // we're going to add 30 into the priority queue to check re-indexing
        usmPriority.appendCell(prcell4);
        usmPriority.appendCell(prcell5);
        usmPriority.appendCell(prcell7);

        // treating this as a priority queue (e.g. data extends Comparable interface)
        usmPriority.priorityWedgeMemory(60);
        usmPriority.priorityWedgeMemory(30);

        System.out.println("Dumping the normal queue to the screen ...");
        Integer deqData = 0;
        while(null != deqData) {
            deqData = (Integer) usm.dequeue();
            System.out.println("Dequeued --> " + deqData);
        }

        System.out.println("Dumping the priority queue to the screen ...");
        deqData = 0;
        while(null != deqData) {
            deqData = (Integer) usmPriority.dequeue();
            System.out.println("Dequeued [priority, ascending] --> " + deqData);
        }

        System.out.println("Put back some items");
        // put these back as a test
        usm.enqueue(101);
        usm.enqueue(177);
        usm.enqueue(335601);

        deqData = 0;
        while(null != deqData) {
            deqData = (Integer) usm.dequeue();
            System.out.println("Dequeued --> " + deqData);
        }

        usm.enqueue(101);
        usm.enqueue(177);
        usm.enqueue(335601);
        usm.enqueue(335602);
        usm.enqueue(335603);
        usm.enqueue(335604);

        usm.dequeue();
        usm.dequeue();

        usm.enqueue(179);


        System.out.println("Final Check ...");
        deqData = 0;
        while(null != deqData) {
            deqData = (Integer) usm.dequeue();
            System.out.println("Dequeued --> " + deqData);
        }

        System.out.println("----------------------- quick-sort ------------------------");
        int[] data = { 3, 6, 8, 10, 1, 2, 1 };
        SortableArray sa = new SortableArray(data);

        System.out.println("Before: " + java.util.Arrays.toString(sa.getArray()));
        sa.quickSort();
        System.out.println("After : " + java.util.Arrays.toString(sa.getArray()));

        System.out.println("----------------------- quick-select ------------------------");
        int[] data2 = { 7, 10, 4, 3, 20, 15 };
        SortableArray sa2 = new SortableArray(data2);

        // Find the 2nd smallest element (index 1 if sorted)
        int k = 1;
        System.out.println("Before: " + java.util.Arrays.toString(sa2.getArray()));
        int kthSmallest = sa2.quickselect(k);
        System.out.println("After : " + java.util.Arrays.toString(sa2.getArray()));
        System.out.println("The " + (k + 1) + "-th smallest value is: " + kthSmallest);


        Xpmts xp = new Xpmts();

        int n = 10;                       // change this to any non‑negative integer
        System.out.println("fib(" + n + ") = " + Xpmts.fib(n));
        System.out.println("\n--- Fibonacci recursion tree for fib(" + n + ") ---");
        Xpmts.printFibTree(n);
        System.out.println("-----------------------------------------------------------------");

        // fibonacci with memoization ("dynamic programming")
        System.out.println("fibMemo(" + n + ") = " + Xpmts.fibMemo(n));

        n = 10;   // change this to any non‑negative integer

        // bottom-up fibonacci
        long start = System.nanoTime();
        int result = Xpmts.bottom_up_fibonacci(n);
        long elapsed = System.nanoTime() - start;

        System.out.printf("bottom_up_fibonacci(%d) (bottom‑up) = %d%n", n, result);
        System.out.printf("(time: %.2f ms)%n", elapsed / 1_000_000.0);

        // show the whole sequence
        int[] seq = Xpmts.buildSequence(n);
        System.out.println("\nFull Fibonacci sequence up to fib(" + n + "):");
        Xpmts.printSequence(seq);

        System.out.println("-----------------------------------------------------------------");
        System.out.println("Number of characters in out array of strings is: "
                + Xpmts.StringCount(Xpmts.someStrings));
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Number of characters [using index instead of sub-arrays]: "
                + Xpmts.StringCount_noSlice(Xpmts.someStrings));
        System.out.println("-----------------------------------------------------------------");

        String word = "abcd";
        List<String> anagrams = Xpmts.anagramsOf(word);

        System.out.println("Number of anagrams: " + anagrams.size());
        // Uncomment to see all the anagrams
        anagrams.forEach(System.out::println);


        System.out.println("Number of paths: " + Xpmts.number_of_paths(10));

        System.out.println("5! = " + factorial(5));   // 120
        System.out.println("0! = " + factorial(0));   // 1 (by definition)
        System.out.println("10! = " + factorial(10)); // 3628800

        System.out.println("Lofasz reversed is: " + Xpmts.recurse("Lofasz"));
        System.out.println("Lofasz-noslice reversed is: " + Xpmts.recurse_noslice("Lofasz-noslice"));




        xp.print_raw(Xpmts.raw_array);
        System.out.println("-----------------------------------------------------------------");
        xp.print_generic(Xpmts.generic_array);

        System.out.println("-----------------------------------------------------------------");
        // or new File("C:/path/to/root");
        File start_path = new File(".");               // or new File("C:/path/to/root");
        findDirectories(start_path);
        System.out.println("-----------------------------------------------------------------");
        /// TurboQueue examples ///
        TurboQueue<String> myQueue = new TurboQueue(20);

        myQueue.write("Hey, you, out there in the cold ");
        myQueue.write("Getting lonely, getting old ");
        myQueue.write("Can you feel me? ");
        myQueue.write("Hey, you, standing in the aisles ");
        myQueue.write("With itchy feet and fading smiles ");
        myQueue.write("Can you feel me? ");
        myQueue.write("Hey, you ");
        myQueue.write("Don't help them to bury the light ");
        myQueue.write("Don't give in without a fight ");

        // ---------------------------------------------------
        // read out
        String floyd;

        for(;;) {
            floyd = myQueue.read();
            if (null != floyd) {
                System.out.println(floyd);
            } else {
                break;
            }
        }

        myQueue.write("Hey, you, out there beyond the wall ");
        myQueue.write("Always doing what you're told ");
        myQueue.write("Can you help me? ");

        myQueue.write("Hey, you, out there on the road ");
        myQueue.write("Always doing what you're told ");
        myQueue.write("Can you help me? ");

        // read out just 3 items
        System.out.println(myQueue.read());
        System.out.println(myQueue.read());
        System.out.println(myQueue.read());

        // and add some more
        myQueue.write("Hey, you ");
        myQueue.write("Don't tell me there's no hope at all ");
        myQueue.write("Together we stand, divided we fall ");

        // ---------------------------------------------------
        // read out
        for(;;) {
            floyd = myQueue.read();
            if (null != floyd) {
                System.out.println(floyd);
            } else {
                break;
            }
        }

        /// FastHashArray examples ///
        FastHashedArray myArray = new FastHashedArray(100);

        myArray.addElement(10);
        myArray.addElement(27);
        myArray.addElement(32);

        myArray.addElement(16384);
        myArray.addElement(563563563);

        myArray.addElement(84); // force a collision  (16384 is already at position 84)

        // edge case 1
        myArray.addElement(100); // force a collision  (16384 is already at position 84)
        myArray.addElement(101); // force a collision  (16384 is already at position 84)
        myArray.addElement(100); // force a collision  (16384 is already at position 84) // should wrap
        myArray.addElement(101); // add 101 again, to test multiple entry deletion
        myArray.addElement(101); // add 101 yet again, to test multiple entry deletion

        // edge case 2
        myArray.addElement(1); // should go to position 3 (!!)

        // edge case 3
        myArray.addElement(100); // should go to position 4 (!!)

        // edge case 4
        myArray.addElement(63); // slot already taken

        // checking deletion of single entry
        myArray.zapElement(16384);

        // checking deletion of element with multiple entries
        myArray.zapElement(101);

        System.out.println("-----------------------------------------------------------------");

        myArray.printArray();

        System.out.println("-----------------------------------------------------------------");

        System.out.println("Get element 563563563: " + myArray.findElement(563563563));
        System.out.println("Get element 563563564: " + myArray.findElement(563563564));
        System.out.println("Get element 563563563: " + myArray.findElement(563563563));
        System.out.println("Get element 101: " + myArray.findElement(101));
        System.out.println("Get element 1: " + myArray.findElement(1));
        System.out.println("Get element 64: " + myArray.findElement(64));
        System.out.println("Get element 63: " + myArray.findElement(63));
        System.out.println("Get element 16384: " + myArray.findElement(16384));
        System.out.println("Get element 16385: " + myArray.findElement(16385));
        System.out.println("Get element 100: " + myArray.findElement(100));
        System.out.println("Get element 102: " + myArray.findElement(102));

        System.out.println("-----------------------------------------------------------------");
        // resize the backing store and re-distribute all elements
        myArray.resizeBackingStore(1000);
        myArray.printArray();

        System.out.println("-----------------------------------------------------------------");

        // re-check the data again
        System.out.println("Get element 563563563: " + myArray.findElement(563563563));
        System.out.println("Get element 563563564: " + myArray.findElement(563563564));
        System.out.println("Get element 563563563: " + myArray.findElement(563563563));
        System.out.println("Get element 101: " + myArray.findElement(101));
        System.out.println("Get element 1: " + myArray.findElement(1));
        System.out.println("Get element 64: " + myArray.findElement(64));
        System.out.println("Get element 63: " + myArray.findElement(63));
        System.out.println("Get element 16384: " + myArray.findElement(16384));
        System.out.println("Get element 16385: " + myArray.findElement(16385));
        System.out.println("Get element 100: " + myArray.findElement(100));
        System.out.println("Get element 102: " + myArray.findElement(102));

        System.out.println("-----------------------------------------------------------------");

        // add elements of other types:

        myArray.addElement("Underneath the bridge, tarp has sprung a leak");
        myArray.addElement("And the animals I've trapped have all become my pets");
        myArray.addElement("And I'm living off of grass, and the drippings from my ceiling");
        myArray.addElement("It's okay to eat fish 'cause they don't have any feelings");

        myArray.printArray();
        System.out.println("-----------------------------------------------------------------");

        // check for some lyrics:
        System.out.println("Check for some lyrics");
        System.out.println("-----------------------");
        System.out.println("It's okay to eat fish 'cause they don't have any feelings: "
                +  myArray.findElement("It's okay to eat fish 'cause they don't have any feelings"));
        System.out.println("Something in the way, yeah, hmm-mmm: "
                +  myArray.findElement("Something in the way, yeah, hmm-mmm"));

        System.out.println("-----------------------------------------------------------------");
        System.out.println("Delete some lyrics");
        System.out.println("-------------------");
        myArray.zapElement("And I'm living off of grass, and the drippings from my ceiling");
        System.out.println("And I'm living off of grass, and the drippings from my ceiling: "
                +  myArray.findElement("And I'm living off of grass, and the drippings from my ceiling"));


    }
}