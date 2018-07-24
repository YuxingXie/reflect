package com.lingyun.generics;

import javax.xml.ws.Holder;

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

        GoodHolder<Automobile> holder=new GoodHolder<>(()->{
            System.out.println("Automobile run");
        });

        holder.get().run();

        GoodHolder<Automobile> carHolder=new GoodHolder<>(new Car());
        Automobile car=carHolder.get();
        car.run();

        Car car2=new Car();

        GoodHolder<AbstractAutomobile> carHolder2=new GoodHolder<>(car2);


/***********************      通配符     ***********************/
        GoodHolder<Apple> appleHolder=new GoodHolder<>(new Apple());

        Apple d = appleHolder.get();

        appleHolder.set(d);

        //GoodHolder<Fruit> fruitGoodHolder=appleHolder;//cannot upcast
        GoodHolder<? extends Fruit> fruitHolder=appleHolder;//通配符是这样用的

        Fruit p=fruitHolder.get();

        d=(Apple)fruitHolder.get();

        try {
            Orange c=(Orange)fruitHolder.get();
        }catch (Exception e){
            //fruitHolder.set(new Apple());//不能这样用我感到非常遗憾
            System.out.println(e);
            System.out.println(fruitHolder.equals(appleHolder));
            System.out.println(p.equals(d));
        }
    }
}
