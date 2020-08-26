package com.meikoz.core.base;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;


import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.meikoz.core.ActivityCollector;
import com.meikoz.core.AppManager;

import com.meikoz.core.model.LogicProxy;
import com.meikoz.core.util.PDialog;

import com.zhy.autolayout.AutoLayoutActivity;

import java.io.IOException;
import java.io.Serializable;


import butterknife.ButterKnife;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

/**
 * base基类
 */
public abstract class BaseActivity extends AutoLayoutActivity implements BaseView {
    private PDialog pDialog;
    private boolean showPDialogWithoutCancleable = false;
    protected BasePresenter mPresenter;
    protected abstract int getLayoutResource();

    protected abstract void onInitialization(Bundle bundle) throws IOException;

    public Context context;

    protected Class getLogicClazz() {
        return null;
    }

    protected void onInitData2Remote() {
        if (getLogicClazz() != null)
            mPresenter = getLogicImpl();

    }
    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(true);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //核心代码.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.BLACK);
        }
        context = BaseActivity.this;
        AppManager.getAppManager().addActivity(this);
        ActivityCollector.addActivity(this, getClass());
        if (getLayoutResource() != 0)
            setContentView(getLayoutResource());
        ButterKnife.bind(this);
        this.pDialog = new PDialog(this);

        try {
            onInitialization(savedInstanceState);
        } catch (IOException e) {
            e.printStackTrace();
        }
        onInitData2Remote();


    }


    //获得该页面的实例
    public <T> T getLogicImpl() {
        return LogicProxy.getInstance().bind(getLogicClazz(), this);
    }

    @Override
    protected void onStart() {
        if (!this.showPDialogWithoutCancleable)
            cancelPDialog();
        super.onStart();
        if (mPresenter != null && !mPresenter.isViewBind()) {
            LogicProxy.getInstance().bind(getLogicClazz(), this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
        ActivityCollector.removeActivity(this);
        LogicProxy.getInstance().unbind(getLogicClazz(), this);
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.cancelAllRequest();//取消网络请求
        }


        cancelPDialog();
    }


    public void cancelPDialog() {
        if (this.pDialog != null) {
            this.pDialog.cancel();
            this.showPDialogWithoutCancleable = false;
        }
    }

    public void dismissPDialog() {
        if ((this.pDialog != null) && (this.pDialog.isShowing())) {
            this.pDialog.dismiss();
            this.pDialog.cancel();
            this.showPDialogWithoutCancleable = false;
        }
    }

    public void showPDialog() {
        if (this.pDialog != null) {
            this.showPDialogWithoutCancleable = false;
            this.pDialog.show();
        }
    }

    /**
     * 获取Edittext内容
     *
     * @param editText
     * @return
     */
    public String getEdtText(EditText editText) {
        if (editText.getText() != null) {
            return editText.getText().toString();
        } else {
            return "";
        }
    }

    /**
     * 获取Textview内容
     *
     * @param tv
     * @return
     */
    public String getTvText(TextView tv) {
        if (tv.getText() != null) {
            return tv.getText().toString();
        } else {
            return "";
        }
    }

    /**
     * 判断Edittext 或TextView 内容是否为空 或者""；
     */
    public boolean EditTextIsNotNull(EditText editText) {
        if (editText.getText() != null) {
            if (editText.getText().toString().length() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean TextViewIsNotNull(TextView tv) {
        if (tv.getText() != null) {
            if (tv.getText().toString().length() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void toast0(String msg) {
        if (msg == null) {
            toast0("");
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void toast1(String msg) {
        if (msg == null) {
            toast1("");
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startfinishActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }
    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /*
     * Activity的跳转-带参数
     */
    public void setIntentClass(Class<?> cla, String type, Object obj) {
        Intent intent = new Intent();
        intent.setClass(this, cla);
        intent.putExtra(type, (Serializable) obj);
        startActivity(intent);
    }



}