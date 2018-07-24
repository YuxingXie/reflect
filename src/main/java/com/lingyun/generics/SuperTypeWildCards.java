package com.lingyun.generics;

import java.util.List;

public class SuperTypeWildCards {
    public void writeTo(List<? super Apple> apples){
        apples.add(new Apple());
        apples.add(new Jonathan());
        //apples.add(new Fruit());//Error
        Object s=apples.get(0);
    }
}
