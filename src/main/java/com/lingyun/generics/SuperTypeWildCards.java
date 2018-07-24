package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class SuperTypeWildCards {
    public void writeTo(List<? super Apple> apples){
        apples.add(new Apple());
        apples.add(new Jonathan());
        //apples.add(new Fruit());//Error
        Object s=apples.get(0);
    }
    public void usefulWriteTo(List<? super Fruit> fruits){
        fruits.add(new Apple());
        fruits.add(new Jonathan());
        fruits.add(new Fruit());
        Object s=fruits.get(0);
    }
    public static void main(String[] args){
        SuperTypeWildCards a = new SuperTypeWildCards();
        //List<? super  Fruit> fruits=new ArrayList<Apple>();
        List<? super  Fruit> fruits=new ArrayList<Fruit>();
        a.usefulWriteTo(fruits);
    }
}
