package com.lingyun.generics;

public class BadHolder1 {
    private Automobile a;
    public BadHolder1(Automobile a){
        this.a=a;
    }

    public Automobile get() {
        return a;
    }
}
