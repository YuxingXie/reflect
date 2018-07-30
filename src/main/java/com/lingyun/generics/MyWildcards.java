package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class MyWildcards {

    public static void main(String[] args){

        List<? super Fruit> apples=new ArrayList<Fruit>();


//        Fruit apple =addAndGetFirst(apples,new Apple());
        Apple apple =addAndGetFirst(apples,new Apple());
        System.out.println(apple);

        List<? extends Pair<? extends Fruit>> pairAppleList=new ArrayList<Pair<Apple>>();
        Pair<? extends Fruit> applePair=maybeGoodGetFirst(pairAppleList);
    }
    public static<T>  T addAndGetFirst(List<? super T> tlist,T t){
        tlist.add(t);
//        return tlist.get(0);
        return (T)tlist.get(0);
    }
    public static<T>  T maybeGoodGetFirst(List<? extends T> tlist){

        return tlist.get(0);
    }
}
