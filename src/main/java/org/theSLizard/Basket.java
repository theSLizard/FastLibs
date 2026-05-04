package org.theSLizard;

import java.util.Objects;

public final class Basket<T> {

    private final Integer index;
    private final T data;

    public Integer getIndex() {
        return index;
    }

    public T getData() {
        return data;
    }

    public Basket(Integer index, T data) {
        this.data = data;
        this.index = Objects.requireNonNull(index);
    }
}
