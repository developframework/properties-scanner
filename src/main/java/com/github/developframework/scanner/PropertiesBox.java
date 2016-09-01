package com.github.developframework.scanner;

import java.util.HashMap;

public class  PropertiesBox {

    private HashMap<Class<?>, Object> box = new HashMap<>();

    public void  push(Class<?> clazz, Object object) {
        box.put(clazz, object);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        return (T) box.get(clazz);
    }
}
