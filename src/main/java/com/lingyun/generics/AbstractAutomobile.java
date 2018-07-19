package com.lingyun.generics;

import java.io.Serializable;

public abstract class AbstractAutomobile implements Automobile {
    @Override
    public void run() {
        System.out.println("automobile run");
    }

    public Serializable doSomething(){
        return null;
    }
}
