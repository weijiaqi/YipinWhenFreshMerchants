package com.product.as.merchants.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 耗时工作处理
 * @param <T>
 */
public class MyHandler<T> extends Handler {
    //WeakReference属于弱引用
    private final WeakReference<? extends T> mActivity;

    public MyHandler(T mActivity) {
        this.mActivity = new WeakReference<>(mActivity);
    }

    @Override
    public void handleMessage(Message msg) {
        T mainActivity = mActivity.get();
        //如果获取出来的mainActivity为空则代表该activity已被GC回收啦
        if (mainActivity != null) {
            listener.handlerMessage(msg);
        }
    }

    private OnHandlerListener listener;

    public void setOnHandlerListener(OnHandlerListener listener) {
        this.listener = listener;
    }

    public void postDelayed(Runnable runnable) {
    }

    public interface OnHandlerListener {
        public abstract void handlerMessage(Message msg);
    }
}
