package com.accumulation.lee.androidaccumulationproject.demo;

/**
 * Created by lee on 15/7/15.
 * 利用内部类初始化时才进行单例类的初始化，实现了延迟加载，而且防止了并发，且避免使用锁的开销
 */
public class SingleTon {

    private static class Holder {
        static final SingleTon instance = new SingleTon();
    }

    public static SingleTon getInstance() {
        return Holder.instance;
    }
}
