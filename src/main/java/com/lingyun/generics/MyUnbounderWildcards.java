package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class MyUnbounderWildcards {

    public static void main(String[] args){
        List<?> fruits= new ArrayList<Apple>();
        //ruits.add(new Apple());//error
        //Apple apple=fruits.get(0);//error
        Fruit apple =getFirst(new ArrayList<Apple>());
    }
    public static<T>  T getFirst(List<? extends T> tlist){
        return tlist.get(0);
    }
}
