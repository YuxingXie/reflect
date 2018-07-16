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

        GoodHolder<Automobile> holder=new GoodHolder<Automobile>(()->{
            System.out.println("Automobile run");
        });

        holder.get().run();

        GoodHolder<Automobile> carHolder=new GoodHolder<>(new Car());

        carHolder.get().run();

        Car car=new Car();

        GoodHolder<AbstractAutomobile> carHolder2=new GoodHolder<>(car);
    }
}
