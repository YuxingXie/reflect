package com.lingyun.generics;

import java.util.Date;

public class DateInterval extends Pair<Date>{
    public void setSecond(Date second){
        if(second.compareTo(getFirst())>0){
            super.setSecond(second);
        }
    }
}
