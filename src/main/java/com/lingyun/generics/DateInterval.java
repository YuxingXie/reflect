package com.lingyun.generics;

import java.util.Date;

public class DateInterval extends Pair<Date>{
    public void setSecond(Date second){
        if(second.compareTo(getFirst())>0){
            super.setSecond(second);
        }
    }

    @Override
    public Date getSecond() {
        return super.getSecond();
    }
    public static void main(String[] args){
        Pair<String>[] table=new Pair[10];
        table[0] =new Pair<String>();

    }
}
