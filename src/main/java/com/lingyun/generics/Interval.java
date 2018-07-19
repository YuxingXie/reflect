package com.lingyun.generics;

public class Interval<T extends Comparable> extends Pair<T>{
    public T getMax(){
        return getFirst().compareTo(getSecond())>0?getFirst():getSecond();
    }
}
