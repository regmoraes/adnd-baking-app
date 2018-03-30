package com.regmoraes.bakingapp.data;

/**
 * Copyright {2018} {regmoraes}
 **/
public final class Optional<T> {

    private T data;

    public Optional(T data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return data == null;
    }

    public T getData() {
        return data;
    }
}
