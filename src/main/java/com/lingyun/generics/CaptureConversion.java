package com.lingyun.generics;

public class CaptureConversion {
    static <T> void f1(GoodHolder<T> holder){
        T t=holder.get();
        System.out.println(t.getClass().getSimpleName());
    }

    static void f2(GoodHolder<?> holder){
        f1(holder);//call with captured type
    }
    static void f3(GoodHolder holder){
        f1(holder);
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        GoodHolder raw = new GoodHolder<Integer>(1);
        f1(raw);//Produces warnings
        f2(raw);//No warnings
        f3(raw);
        GoodHolder rawBasic=new GoodHolder();
        rawBasic.set(new Apple());//Warning
        f1(rawBasic);
        f2(rawBasic);//No warnings
        f3(rawBasic);
        //Upcast to GoodHolder<?>,still figures it out:
        GoodHolder<?> wildcarded=new GoodHolder<Double>(1.0);
        f2(wildcarded);
        f3(wildcarded);
    }
}
/*Output:
Integer
Integer
Integer
Apple
Apple
Double
Double
 */
