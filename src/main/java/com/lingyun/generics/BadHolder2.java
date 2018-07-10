package com.lingyun.generics;

public class BadHolder2 {
    private Object a;
    public BadHolder2(Object a){
        this.a=a;
    }

    public Object get() {
        return a;
    }
}
