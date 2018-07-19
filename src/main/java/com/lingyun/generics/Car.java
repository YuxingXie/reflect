package com.lingyun.generics;

import java.io.Serializable;

public class Car extends AbstractAutomobile implements Cloneable{
    @Override
    public void run() {
        System.out.println("car run");
    }

    @Override
    public String doSomething(Serializable doing) {
        return "car is running";
    }
    public String doSomething(String doing) {
        return doSomething((Serializable)doing);
    }
}
