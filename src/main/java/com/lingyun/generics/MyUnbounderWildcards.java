package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class MyUnbounderWildcards {

    public static void main(String[] args){
        List<?> fruits= new ArrayList<Apple>();
        //ruits.add(new Apple());//error
        //Apple apple=fruits.get(0);//error
        List<? super Fruit> apples=new ArrayList<>();
        Fruit apple =addAndGetFirst(apples,new Apple());
    }
    public static<T>  T addAndGetFirst(List<? super T> tlist,T t){
        tlist.add(t);
//        return tlist.get(0);
        return (T)tlist.get(0);
    }

}
