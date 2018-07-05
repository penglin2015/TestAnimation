package com.xuyao.prancelib.util;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by T470 on 2018/3/24.
 */

public class MyBundle{

    Bundle bundle;

    public MyBundle() {
        bundle = new Bundle();
    }

    public MyBundle put(String key, Object value) {
        if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) value);
        }
        return this;
    }

    public Bundle getBundle() {
        return bundle;
    }
}
