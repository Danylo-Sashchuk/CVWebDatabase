package com.basejava;

import com.basejava.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method print = Resume.class.getMethod("toString");
        Resume resume = new Resume();
        System.out.println(print.invoke(resume));
    }
}
