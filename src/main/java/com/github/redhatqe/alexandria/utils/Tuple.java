package com.github.redhatqe.alexandria.utils;

/**
 * Created by stoner on 5/16/17.
 */
public class Tuple<F, S> {
    public F first;
    public S second;

    public Tuple(F f, S s) {
        this.first = f;
        this.second = s;
    }
}
