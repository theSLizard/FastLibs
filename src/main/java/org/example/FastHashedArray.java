package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastHashedArray {

    private Integer backingStoreSize = 0;
    private List<Element> backingStore = null;

    private class Element {
        Integer Key;
        Boolean isTombstone;

        Element(Integer key, Boolean isTombstone) {
            this.isTombstone = isTombstone;
            this.Key = key;
        }
    }

    private Integer calculateHash(Element x) {
        Integer hash = x.Key % backingStore.size();
        return hash;
    }

    private Integer calculateHash(Integer x) {
        Integer hash = x % backingStore.size();
        return hash;
    }

    public void printArray() {
        if (null != backingStore) {
           Integer storageIndex = 0;
           for (Element x : backingStore) {
               if (x != null) {
                   System.out.println("Element : " + x.Key);
                   System.out.println("Located : " + storageIndex);
                   System.out.println("Deleted : " + x.isTombstone);
                   System.out.println("---------------------------");
               }
               storageIndex ++;
            }
        }
    }

    public FastHashedArray(Integer initialSize) {
        if (null == backingStore) {
            backingStore = new ArrayList<>(Collections.nCopies(initialSize, null));
        }
    }

    public void resizeBackingStore(Integer newSize) {

        if (null == backingStore) return;

        // save old backing store so we can re-distribute it
        List<Element> oldBackingStore = this.backingStore;

        // create new backing store
        this.backingStore = new ArrayList<>(Collections.nCopies(newSize, null));

        // redistribute existing contents
        for(Element existing: oldBackingStore) {
            if (null != existing) {
                this.addElement(existing);
            }
        }
    }

    public void zapElement(Integer x) {
        Integer hash = calculateHash(x);

        // check if never existed
        if (null == backingStore.get(hash)) return;

        // check of something else is using the default slot
        while(null != backingStore.get(hash)) {
            // we found it !!
            if (backingStore.get(hash).Key.equals(x) &&
                    (false == backingStore.get(hash).isTombstone)) {
                Element zapped = new Element(x, true);
                // "delete" items (all of them !!)
                backingStore.set(hash, zapped);
            };

            hash = (hash + 1) % backingStore.size();
        }
    }

    public Boolean findElement(Integer x) {
        Integer hash = calculateHash(x);

        // check if never existed
        if (null == backingStore.get(hash)) return false;

        // check of something else is using the default slot
        while(null != backingStore.get(hash)) {
            // we found it !!
            if (backingStore.get(hash).Key.equals(x) &&
                    (false == backingStore.get(hash).isTombstone)) return true;

            hash = (hash + 1) % backingStore.size();
        }

        // not found
        return false;
    }

    public void addElement(Integer x) {
        Element newElement = new Element(x, false);
        Integer hash = this.calculateHash(newElement);

        while (null != backingStore.get(hash) && false == backingStore.get(hash).isTombstone) {
            // slot already taken and active, we need to resolve the collision
            hash = (hash + 1) % backingStore.size();
        }

        backingStore.set(hash, newElement);

    }

    private void addElement(Element x) {

        Integer hash = this.calculateHash(x);

        while (null != backingStore.get(hash) && false == backingStore.get(hash).isTombstone) {
            // slot already taken and active, we need to resolve the collision
            hash = (hash + 1) % backingStore.size();
        }

        backingStore.set(hash, x);
    }
}
