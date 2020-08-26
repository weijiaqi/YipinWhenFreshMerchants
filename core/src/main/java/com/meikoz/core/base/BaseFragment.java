package com.meikoz.core.base;


import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.meikoz.core.model.LogicProxy;
import com.meikoz.core.util.PDialog;

import java.io.IOException;
import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements BaseView {

    protected abstract int getLayoutResource();

    protected abstract void onInitView(Bundle savedInstanceState) throws IOException;

    protected Class getLogicClazz() {
        return null;
    }

    protected void onInitData2Remote() {
        if (getLogicClazz() != null)
            mPresenter = getLogicImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getLayoutResource() != 0) {
            rootView = inflater.inflate(getLayoutResource(), null);
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }

        ButterKnife.bind(this, rootView);

        try {
            this.onInitView(savedInstanceState);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitData2Remote();

    }

    //获得该页面的实例
    public <T> T getLogicImpl() {
        return LogicProxy.getInstance().bind(getLogicClazz(), this);
    }

    @Override
    public void onStart() {

        if (mPresenter != null && !mPresenter.isViewBind()) {
            LogicProxy.getInstance().bind(getLogicClazz(), this);
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.detachView();
    }

    protected BasePresenter mPresenter;
    protected View rootView;
    protected Context mContext = null;//context

    protected boolean showPDialogWithoutCancleable = false;





}
