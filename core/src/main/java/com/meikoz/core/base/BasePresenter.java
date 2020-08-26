package com.meikoz.core.base;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends BaseView> implements Presenter<T> {

    private T mView;
    protected CompositeDisposable disposables = new CompositeDisposable();


    @Override
    public void attachView(T mvpView) {
        this.mView = mvpView;
    }



    @Override
    public void detachView() {
        this.mView = null;
    }

    public boolean isViewBind() {
        return mView != null;
    }

    /**
     * 页面销毁时取消所有网络请求
     */
    public void cancelAllRequest() {
        disposables.clear();
    }

    public T getView() {
        return mView;
    }

}
