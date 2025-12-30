package org.theSLizard;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

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