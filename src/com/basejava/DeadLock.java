package com.basejava;

public class DeadLock {

    public static void main(String[] args) {
        Object firstLock = new Object();
        Object secondLock = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (firstLock) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (secondLock) {

                }
            }
        });

        Thread thread2 = new Thread(() -> {
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
        thread2.start();
        thread1.start();
    }
}
