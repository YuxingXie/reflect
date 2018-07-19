package com.lingyun.generics;

public class Interval<Q extends Comparable,S> extends Pair<Q>{
    public Q getMax(){
        return getFirst().compareTo(getSecond())>0?getFirst():getSecond();
    }
    private S s;

    public S getS() {
        return s;
    }

    public void setS(S s) {
        this.s = s;
    }
}
