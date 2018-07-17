package com.lingyun.generics;

public class ArrayAlg {
    public static<T> T getMiddle(T...a){
        return a[a.length/2];
    }

    public static void main(String[] args){
        String middle =ArrayAlg.<String>getMiddle("John","Q","Public");
    }
}
