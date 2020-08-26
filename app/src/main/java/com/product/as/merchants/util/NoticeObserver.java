package com.product.as.merchants.util;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 * 观察者设计模式
 */
public class NoticeObserver {

    private static final String TAG = NoticeObserver.class.getSimpleName();

    /**
     * 内部类实现单例模式 延迟加载，减少内存开销 优点：延迟加载，线程安全（java中class加载时互斥的），也减少了内存消耗
     *
     * @author limeihong
     */
    private static class SingletonHolder {
        private static NoticeObserver instance = new NoticeObserver();
    }

    /**
     * 私有的构造函数
     */
    private NoticeObserver() {

    }

    public static NoticeObserver getInstance() {
        return SingletonHolder.instance;
    }

    List<Observer> observers = new ArrayList<Observer>();

    /**
     * 这里要返回true，数据变化时，观察者才生效
     */
    // @Override
    // public boolean hasChanged() {
    // return true;
    // }
    public void addObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        }
    }

    public void removeObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (observers.contains(observer)) {
                observers.remove(observer);
            }
        }
    }

    public void notifyObservers(int what) {
        notifyObservers(what, false);
    }

    public <T> void notifyObservers(int what, T data) {
        int size = 0;
        Observer[] arrays;
        synchronized (this) {
            size = observers.size();
            arrays = new Observer[size];
            observers.toArray(arrays);
        }
        for (Observer observer : arrays) {
            // observer.update(this, data);
            observer.update(what, data);
        }
    }

    public interface Observer {
        <T> void update(int what, T t);
        // void update(Observable observable, Object data);
    }
}
