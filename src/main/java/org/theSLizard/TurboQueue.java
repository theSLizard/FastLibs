package org.theSLizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TurboQueue <T> {

    private Integer depthIndex = 0;
    private Integer readIndex = 0;

    private List<Data<T>> backingStore = null;

    private class Data<T> {
        final T Item;
        Data(T item) {
            this.Item = item;
        }
    }

    public TurboQueue (Integer size) {
        if (null == backingStore) {
            backingStore = new ArrayList<>(Collections.nCopies(size, null));
        }
    }

    public Boolean write(T x) {
        Data<T> item = null;

        if (depthIndex < backingStore.size()) {
            item = new Data(x);
            backingStore.set(depthIndex, item);
            this.depthIndex ++;
            return true;
        } else {
            // we're full - pop/remove elements before adding new ones
            return false;
        }
    }

    public T read() {
        Data<T> data = null;

        if (readIndex < depthIndex) {
            data = backingStore.get(readIndex);
            this.readIndex ++;
            return data.Item;
        } else {
            // reset indicators
            this.readIndex = 0;
            this.depthIndex = 0;
            // queue is empty - please add some stuff
            return null;
        }
    }

}
