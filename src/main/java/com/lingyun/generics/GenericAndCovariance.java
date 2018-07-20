package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericAndCovariance {
    public static void main(String[] args){
        List<? extends Fruit> flist= new ArrayList<Apple>();//有讲究
//        flist.add(new Apple());//不是吧，这也报错，我能传什么进去？
//        flist.add(new Fruit());//wrong
//        flist.add(new Object());//啥都不行？
        flist.add(null);//难道只能传null
        Fruit f=flist.get(0);//correct
    }
}
