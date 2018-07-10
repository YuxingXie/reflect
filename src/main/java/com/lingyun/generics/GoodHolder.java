package com.lingyun.generics;

public class GoodHolder<T> {
    private T a;
    public GoodHolder(T a){
        this.a=a;
    }

    public T get() {
        return a;
    }

    public void set(T a) {
        this.a = a;
    }
    public static void main(String[] args){

        GoodHolder<Automobile> holder=new GoodHolder<Automobile>(()->{});

        holder.get().run();
    }
}
