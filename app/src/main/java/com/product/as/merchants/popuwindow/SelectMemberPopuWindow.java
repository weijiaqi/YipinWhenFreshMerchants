package com.product.as.merchants.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.product.as.merchants.R;

public class SelectMemberPopuWindow extends PopupWindow {
    private View mView; // PopupWindow 菜单布局
    private Activity mContext; // 上下文参数
    private View.OnClickListener mSelectListener; // 相册选取的点击监听器
    private View.OnClickListener mCaptureListener; // 拍照的点击监听器
    private View.OnClickListener mLogisticsListener;

    public SelectMemberPopuWindow(Activity context, View.OnClickListener selectListener, View.OnClickListener captureListener) {
        super(context);
        this.mContext = context;
        this.mSelectListener = selectListener;
        this.mCaptureListener = captureListener;

        Init();
    }

    /**
     * 设置布局以及点击事件
     */
    private void Init() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.item_selectmember_popuwindow, null);
        // 设置背景颜色变暗
        backgroundAlpha(0.7f);
        Button btn_camera = mView.findViewById(R.id.item_popupwindows_camera);
        Button btn_select = mView.findViewById(R.id.item_popupwindows_Photo);

        btn_camera.setOnClickListener(mSelectListener);
        btn_select.setOnClickListener(mCaptureListener);


        // 导入布局
        this.setContentView(mView);
        // 设置动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mView.setOnTouchListener((v, event) -> {
            int height = mView.findViewById(R.id.ll_popup).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height) {
                    backgroundAlpha(1.0f);
                    dismiss();
                }
            }

            return true;
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
