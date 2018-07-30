package com.lingyun.generics;

public class MyCaptureConversion {
    static <T> T f1(GoodHolder<T> holder){
        T t=holder.get();
        return t;
    }


    public static void main(String[] args){

        GoodHolder<? super Fruit> rawBasic=new GoodHolder<Fruit>();
        rawBasic.set(new Apple());
        //Fruit apple=f1(rawBasic);//error
        Object apple=f1(rawBasic);
    }
}