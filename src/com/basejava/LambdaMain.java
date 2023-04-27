package com.basejava;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface lambdasomth {
    String convert(String n);
    int hashCode();
}

public class LambdaMain {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("Hello", "Cat", "Java", "Bag"));
        System.out.println(list);
        list.removeIf(s -> {
            int a = s.charAt(1);
            return a > 3;
        });
        lambdasomth lambdasomth = (n) -> {
            String a = n.trim();
            return a;
        };

        Comparable<String> comparable = (n) -> {
            if (n != null) {
                return 1;
            }
            return 0;
        };
        String s = null;
        System.out.println(comparable.compareTo(null));
    }

}
