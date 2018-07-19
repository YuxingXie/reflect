package com.lingyun.generics;

import java.io.File;

public class Pair<T> {
    private T first;
    private T second;

    public Pair() {
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public static void main(String[] args){
        Manager[] topHonchos=new Manager[4];
        Pair<Employee> result=ArrayAlg.minMax(topHonchos);
//
        Pair<Manager> managerPair=new Pair<Manager>();
//        result = managerPair; //error
        Pair rawPair=managerPair;
        rawPair.setFirst(new File(""));
    }
}
