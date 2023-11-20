package com.webcv;

import com.webcv.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method print = Resume.class.getMethod("toString");
        Resume resume = new Resume("Some name");
        System.out.println(print.invoke(resume));
    }
}
