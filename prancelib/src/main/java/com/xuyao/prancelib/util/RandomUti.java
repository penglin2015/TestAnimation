package com.xuyao.prancelib.util;

import java.util.Random;

public class RandomUti {

    static Random random;
    public static RandomUti instance=null;

    public static RandomUti getInstance() {
        if(instance==null){
            instance=new RandomUti();
            random=new Random(System.currentTimeMillis());
        }
        return instance;
    }

    public Random getRandom() {
        return random;
    }
}
