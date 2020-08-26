package com.product.as.merchants.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/5/17.
 * 对话框工具类
 */
public class DialogUtils {
    private View contentView;//对话框的内容布局
    private Dialog dialog;//对话框对象
    private static DialogUtils utils;

    private DialogUtils() {

    }

    //单例模式创建简单的对话框工具实例
    public static DialogUtils getInstance() {
        synchronized (DialogUtils.class) {
            if (utils == null) {
                utils = new DialogUtils();
            }
            return utils;
        }
    }

    //这个方法抽取了所有的contentView内容
    public void showSimpleDialog(Context context, int customerViewRes, int styleRes, CustomerSetListener listener) {
        dialog = new Dialog(context, styleRes);
        dialog.setCanceledOnTouchOutside(true);
        contentView = View.inflate(context, customerViewRes, null);
        if (listener != null)
            listener.customerSet(contentView, utils);
        dialog.setContentView(contentView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.alpha=1.0f;
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels / 1.2);
        params.gravity = Gravity.CENTER;
        dialog.show();
    }
    //这个方法抽取了所有的contentView内容
    public void showTimeDialog(Context context, int customerViewRes, int styleRes, CustomerSetListener listener) {
        dialog = new Dialog(context, styleRes);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        contentView = View.inflate(context, customerViewRes, null);
        if (listener != null)
            listener.customerSet(contentView, utils);
        dialog.setContentView(contentView);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels / 1.1);
        params.gravity = Gravity.CENTER;
        dialog.show();
    }

    //这个方法抽取了所有的contentView内容
    public void showBottomDialog(Context context, int customerViewRes, int styleRes, CustomerSetListener listener) {
        dialog = new Dialog(context, styleRes);
        dialog.setCanceledOnTouchOutside(true);
        contentView = View.inflate(context, customerViewRes, null);
        if (listener != null)
            listener.customerSet(contentView, utils);
        dialog.setContentView(contentView);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        //params.height = (int) (context.getResources().getDisplayMetrics().heightPixels / 1.5);
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels / 1);
        params.gravity = Gravity.BOTTOM;
        dialog.show();
    }

    public void showTopDialog(Context context, Double num, int customerViewRes, int styleRes, CustomerSetListener listener) {
        dialog = new Dialog(context, styleRes);
        dialog.setCanceledOnTouchOutside(true);
        contentView = View.inflate(context, customerViewRes, null);
        if (listener != null)
            listener.customerSet(contentView, utils);
        dialog.setContentView(contentView);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels / 1.2);
        params.y= (int) (context.getResources().getDisplayMetrics().heightPixels / num);
        params.gravity = Gravity.TOP;
        dialog.show();
    }

    public void showTopDialogBtom(Context context, Double num, int customerViewRes, int styleRes, CustomerSetListener listener) {
        dialog = new Dialog(context, styleRes);
        dialog.setCanceledOnTouchOutside(true);
        contentView = View.inflate(context, customerViewRes, null);
        if (listener != null)
            listener.customerSet(contentView, utils);
        dialog.setContentView(contentView);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels / 1.2);
        params.y= (int) (context.getResources().getDisplayMetrics().heightPixels / num);
        params.gravity = Gravity.BOTTOM;
        dialog.show();
    }


    //关闭对话框
    public void close() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    //点击区域外不会关闭对话框
    public void setCancelable(boolean b) {
        dialog.setCancelable(b);
    }

    //自定义对话框用户逻辑处理的回调方法
    public interface CustomerSetListener {
        public void customerSet(View contentView, DialogUtils utils);

    }
}
