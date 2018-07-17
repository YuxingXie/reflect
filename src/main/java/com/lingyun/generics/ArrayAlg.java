package com.lingyun.generics;

public class ArrayAlg {
    public static<T> T getMiddle(T...a){
        return a[a.length/2];
    }

    public static void main(String[] args){
        String middle =ArrayAlg.<String>getMiddle("John","Q.","Public");
        String middle2 =ArrayAlg.getMiddle("John","Q.","Public");
//        double d1=ArrayAlg.getMiddle(3.14,1729,0);
        double d2 =ArrayAlg.getMiddle(3.14d,1729d,0d);
        Number d3=ArrayAlg.getMiddle(3.14,1729,0);
        Comparable d4=ArrayAlg.getMiddle(3.14,1729,0);
    }
}
