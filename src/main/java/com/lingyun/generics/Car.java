package com.lingyun.generics;

public class Car extends AbstractAutomobile implements Cloneable{
    @Override
    public void run() {
        System.out.println("car run");
    }

    @Override
    public String doSomething() {
        return "car is doing something";
    }
}
