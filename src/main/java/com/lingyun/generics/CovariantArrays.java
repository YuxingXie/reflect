package com.lingyun.generics;

public class CovariantArrays {

    public static void main(String[] args){
        Fruit[] fruits= new Apple[10];//OK,向上转型
        fruits[0] = new Apple();
        fruits[1] = new Jonathan();
        fruits[0] = new Fruit();
        fruits[0] = new Orange();



    }
}

class Fruit{}
class Apple extends Fruit{}
class Jonathan extends Apple{}
class Orange extends Fruit{}
