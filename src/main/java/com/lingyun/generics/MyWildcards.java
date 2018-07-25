package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class MyWildcards {

    public static void main(String[] args){

        List<? super Fruit> apples=new ArrayList<Fruit>();
//        Fruit apple =addAndGetFirst(apples,new Apple());
        Apple apple =addAndGetFirst(apples,new Apple());
        System.out.println(apple);


    }
    public static<T>  T addAndGetFirst(List<? super T> tlist,T t){
        tlist.add(t);
//        return tlist.get(0);
        return (T)tlist.get(0);
    }
    public static<T extends Fruit>  T maybeGoodAddAndGetFirst(List<T> tlist,T t){
        tlist.add(t);
        return tlist.get(0);
    }
}
