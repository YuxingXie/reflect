package com.lingyun.generics;

public class DifferentItemsHolder<F,S> {
    private F first;
    private S second;

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }
    public static void main(String[] args){
        DifferentItemsHolder<Car,Motorcycle> holder=new DifferentItemsHolder<>();
        holder.setFirst(new Car());
        holder.setSecond(new Motorcycle());
        holder.getFirst().run();
        holder.getSecond().run();
    }
}
