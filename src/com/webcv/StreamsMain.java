package com.webcv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StreamsMain {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 3, 2, 1, 2, 4}));

        System.out.println("\n ----- third approach -----");
        System.out.println(oddOrEven4(List.of(1, 2, 3)));
        System.out.println(oddOrEven4(List.of(1, 2, 3, 3)));
        int[] num = new int[10];
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (v, i) -> v * 10 + i);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return integers.stream()
                .filter(i -> {
                    if (sum % 2 == 0) {
                        return i % 2 != 0;
                    }
                    return i % 2 == 0;
                })
                .toList();
    }

    private static List<Integer> oddOrEven2(List<Integer> integers) {
        AtomicInteger sum = new AtomicInteger();
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();

        integers.stream()
                .mapToInt(Integer::intValue)
                .forEach(i -> {
                    sum.updateAndGet(v -> v + i);
                    if (i % 2 == 0) {
                        even.add(i);
                    } else {
                        odd.add(i);
                    }
                });

        if (sum.get() % 2 == 0) {
            return odd;
        }
        return even;
    }

    private static List<Integer> oddOrEven3(List<Integer> integers) {
        //if the number of odd numbers is odd - result is also odd.
        AtomicInteger oddCounter = new AtomicInteger();
        return integers.stream()
                .peek(i -> {
                    if (i % 2 == 0) {
                        oddCounter.getAndIncrement();
                    }
                })
                .filter(i -> {
                    if (oddCounter.get() % 2 == 0) {
                        return i % 2 != 0;
                    }
                    return i % 2 == 0;

                })
                .toList();
    }

    private static List<Integer> oddOrEven4(List<Integer> integers) {
        Map<Boolean, List<Integer>> collect = integers.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        if (collect.get(false)
                .size() % 2 != 0) {
            return collect.get(true);
        }
        return collect.get(false);
    }
}
