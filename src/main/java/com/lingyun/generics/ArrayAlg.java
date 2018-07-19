package com.lingyun.generics;

public class ArrayAlg {
    public static<T> T getMiddle(T...a){
        return a[a.length/2];
    }
    public static<T extends Comparable> T min(T[] a){
        if (a==null ||a.length==0) return null;
        T smallest=a[0];
        for(int i=0;i<a.length;i++){
            if (smallest.compareTo(a[i])>0) smallest=a[i];
        }
        return smallest;
    }
    public static<T  extends Comparable> Pair<T>minMax(T[] a){
        if (a==null||a.length==0) return null;
        Pair<T> pair =new Pair<T>();
        T min=a[0];
        T max =a[0];
        for(T t:a){
            if (t.compareTo(max)>0) max=t;
            if (t.compareTo(min)<0) min=t;
        }
        pair.setFirst(min);
        pair.setSecond(max);
        return pair;
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
