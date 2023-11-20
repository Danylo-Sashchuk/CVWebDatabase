package com.webcv;

public class DeadLock {

    public static void main(String[] args) {
        Object firstLock = new Object();
        Object secondLock = new Object();

        Thread thread1 = lockTwoObjects(secondLock, firstLock);

        Thread thread2 = lockTwoObjects(firstLock, secondLock);
        thread2.start();
        thread1.start();
    }

    private static Thread lockTwoObjects(Object firstLock, Object secondLock) {
        return new Thread(() -> {
            synchronized (secondLock) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (firstLock) {

                }
            }
        });
    }
}
