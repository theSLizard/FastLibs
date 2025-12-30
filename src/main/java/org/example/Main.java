package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

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

    }
}