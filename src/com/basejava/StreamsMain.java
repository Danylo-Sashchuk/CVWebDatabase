package com.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamsMain {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 3, 2, 1, 2, 4}));
        System.out.println(oddOrEven(List.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 20)));
        System.out.println(oddOrEven(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 20)));
    }

    private static int minValue(int[] values) {
        AtomicInteger res = new AtomicInteger();
        Arrays.stream(values).distinct().sorted().forEach(i -> res.updateAndGet(v -> v * 10 + i));
        return res.get();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream().filter(i -> {
            if (sum % 2 == 0) {
                return i % 2 == 0;
            }
            return i % 2 != 0;
        }).toList();
    }

}
