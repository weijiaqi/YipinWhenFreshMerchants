package com.product.as.merchants;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.luck.picture.lib.permissions.RxPermissions;
import com.meikoz.core.base.BaseActivity;
import com.meikoz.core.util.MD5Util;

import com.next.easynavigation.view.EasyNavigationBar;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.fragment.CommodityManagementFragment;
import com.product.as.merchants.fragment.IndexFragment;
import com.product.as.merchants.fragment.OrderManagementFragment;
import com.product.as.merchants.fragment.UserFragment;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.ReleaseCommoditiesActivity;
import com.product.as.merchants.ui.SettingActivity;
import com.product.as.merchants.util.CProgressDialogUtils;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.HProgressDialogUtils;
import com.product.as.merchants.util.OkGoUpdateHttpUtil;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.VersionUtil;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.service.DownloadService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends BaseActivity {
    @Bind(R.id.navigationBar)
    EasyNavigationBar navigationBar;
    private String[] tabText = {"首页", "商品管理","订单管理", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.task,  R.mipmap.order, R.mipmap.user};
    //选中时icon
    private int[] selectIcon = {R.mipmap.indexdown, R.mipmap.taskdown, R.mipmap.orderdown, R.mipmap.userdown};
    private List<Fragment> fragments = new ArrayList<>();
    private final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10001;
    boolean delta;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        getPermission();

        fragments.add(new IndexFragment());
        fragments.add(new CommodityManagementFragment());
        fragments.add(new OrderManagementFragment());
        fragments.add(new UserFragment());

        navigationBar
                .defaultSetting()  //恢复默认配置、可用于重绘导航栏
                .titleItems(tabText)      //  Tab文字集合  只传文字则只显示文字
                .normalIconItems(normalIcon)   //  Tab未选中图标集合
                .selectIconItems(selectIcon)   //  Tab选中图标集合
                .fragmentList(fragments)       //  fragment集合
                .fragmentManager(getSupportFragmentManager())
                .iconSize(20)     //Tab图标大小
                .tabTextSize(10)   //Tab文字大小
                .tabTextTop(2)     //Tab文字距Tab图标的距离
                .normalTextColor(Color.parseColor("#999999"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#1C75BC"))   //Tab选中时字体颜色
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)  //同 ImageView的ScaleType
                .setOnCenterTabClickListener(new EasyNavigationBar.OnCenterTabSelectListener() {
                    @Override
                    public boolean onCenterTabSelectEvent(View view) {

                        startActivity(ReleaseCommoditiesActivity.class);
                        return false;
                    }
                })

                .smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
                .canScroll(true)    //Viewpager能否左右滑动
                .mode(EasyNavigationBar.NavigationMode.MODE_ADD)   //默认MODE_NORMAL 普通模式  //MODE_ADD 带加号模式
                .centerTextStr("")
                .centerImageRes(R.mipmap.ic_tab_more)
                .centerIconSize(36)    //中间加号图片的大小
                .centerLayoutHeight(100)   //包含加号的布局高度 背景透明  所以加号看起来突出一块
                .navigationHeight(50)  //导航栏高度
                .centerLayoutRule(EasyNavigationBar.RULE_BOTTOM) //RULE_CENTER 加号居中addLayoutHeight调节位置 EasyNavigationBar.RULE_BOTTOM 加号在导航栏靠下
                .centerLayoutBottomMargin(10)   //加号到底部的距离
                .hasPadding(true)    //true ViewPager布局在导航栏之上 false有重叠
                .hintPointLeft(-3)  //调节提示红点的位置hintPointLeft hintPointTop（看文档说明）
                .hintPointTop(-3)
                .hintPointSize(6)    //提示红点的大小
                .msgPointLeft(-10)  //调节数字消息的位置msgPointLeft msgPointTop（看文档说明）
                .msgPointTop(-10)
                .msgPointTextSize(9)  //数字消息中字体大小
                .msgPointSize(18)    //数字消息红色背景的大小
                .centerAlignBottom(true)  //加号是否同Tab文字底部对齐  RULE_BOTTOM时有效；
                .centerTextTopMargin(50)  //加号文字距离加号图片的距离
                .centerTextSize(15)      //加号文字大小
                .centerNormalTextColor(Color.parseColor("#ff0000"))    //加号文字未选中时字体颜色
                .centerSelectTextColor(Color.parseColor("#00ff00"))    //加号文字选中时字体颜色
                .setMsgPointColor(Color.BLUE) //数字消息、红点背景颜色
                .textSizeType(EasyNavigationBar.TextSizeType.TYPE_DP)  //字体单位 建议使用DP  可切换SP
                .build();


//        //Tab点击事件  return true 页面不会切换
//        navigationBar.defaultSetting()
//                .titleItems(tabText)      //必传  Tab文字集合
//                .normalIconItems(normalIcon)   //必传  Tab未选中图标集合
//                .selectIconItems(selectIcon)   //必传  Tab选中图标集合
//                .fragmentList(fragments)       //必传  fragment集合
//                .fragmentManager(getSupportFragmentManager())     //必传
//                .iconSize(20)     //Tab图标大小
//                .tabTextSize(10)   //Tab文字大小
//                .tabTextTop(2)     //Tab文字距Tab图标的距离
//                .normalTextColor(Color.parseColor("#999999"))   //Tab未选中时字体颜色
//                .selectTextColor(Color.parseColor("#1C75BC"))   //Tab选中时字体颜色
//                .scaleType(ImageView.ScaleType.CENTER_INSIDE)  //同 ImageView的ScaleType

//                .smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
//                .canScroll(false)    //Viewpager能否左右滑动
//                .mode(EasyNavigationBar.NavigationMode.MODE_ADD)   //默认MODE_NORMAL 普通模式  //MODE_ADD 带加号模式
//
//                .centerIconSize(30)    //中间加号图片的大小
//                .centerLayoutHeight(50)   //包含加号的布局高度 背景透明  所以加号看起来突出一块
//                .navigationHeight(50)  //导航栏高度
//                .centerLayoutRule(EasyNavigationBar.RULE_CENTER) //RULE_CENTER 加号居中addLayoutHeight调节位置 EasyNavigationBar.RULE_BOTTOM 加号在导航栏靠下
//                .hasPadding(true)    //true ViewPager布局在导航栏之上 false有重叠
//                .hintPointLeft(-3)  //调节提示红点的位置hintPointLeft hintPointTop（看文档说明）
//                .hintPointTop(-3)
//                .hintPointSize(6)    //提示红点的大小
//                .msgPointLeft(-10)  //调节数字消息的位置msgPointLeft msgPointTop（看文档说明）
//                .msgPointTop(-10)
//                .msgPointTextSize(9)  //数字消息中字体大小
//                .msgPointSize(18)    //数字消息红色背景的大小
//                .centerAlignBottom(true)  //加号是否同Tab文字底部对齐  RULE_BOTTOM时有效；
//                .centerNormalTextColor(Color.parseColor("#666666"))    //加号文字未选中时字体颜色
//                .centerSelectTextColor(Color.parseColor("#FB374F"))    //加号文字选中时字体颜色
//                .build();


        requestPermissions();
    }


    private void requestPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

    }


    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        //版本更新
                        versionUpdate(true);
                    } else {

                    }
                });
    }

    private void versionUpdate(boolean isShowDownloadProgress) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Map<String, String> params = new HashMap<>();
        /**需要向后台传的参数*/
        String token = (String) SharedPreferencesUtils.getSp(Constants.UID, "");
        String version = String.valueOf(VersionUtil.getVersionName(this));
        String keysort = "VeZ16GuXyKaYhKbA?type=1&uid=" + token + "&version=" + version + "";  //参数排序
        String api_token = MD5Util.md5(keysort.toString());//加密
        params.put("uid", token);
        params.put("version", version);
        params.put("type", "1");
        params.put("api_token", api_token);
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(this)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(ApiInterface.BASE_URL2)
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(true)
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")

                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            delta = Boolean.parseBoolean(jsonObject.optString("delta"));
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(jsonObject.optString("update"))
                                    //（必须）新版本号，
                                    .setNewVersion(jsonObject.optString("new_version"))
                                    //（必须）下载地址
                                    .setApkFileUrl(jsonObject.optString("apk_url"))
                                    //（必须）更新内容
                                    .setUpdateLog(jsonObject.optString("update_log"))
                                    //是否强制更新，可以不设置
                                    .setConstraint(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    /**
                     * 有新版本
                     *
                     * @param updateApp        新版本信息
                     * @param updateAppManager app更新管理器
                     */
                    @Override
                    public void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {


                        showDiyDialog(isShowDownloadProgress, updateApp, updateAppManager);

                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        CProgressDialogUtils.showProgressDialog(MainActivity.this);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(MainActivity.this);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
                    }
                });
    }


    /**
     * 自定义对话框
     *
     * @param isShowDownloadProgress
     * @param updateApp
     * @param updateAppManager
     */
    private void showDiyDialog(boolean isShowDownloadProgress, final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
        String targetSize = updateApp.getTargetSize();
        String updateLog = updateApp.getUpdateLog();
        String msg = "";
        if (!TextUtils.isEmpty(targetSize)) {
            msg = "新版本大小" + targetSize + "\n\n";
        }
        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog;
        }
        DialogUtils.getInstance().showTimeDialog(
                this, R.layout.dialog_version,
                R.style.dialog_lhp, (contentView, utils) -> {

                    TextView tvUpdate = contentView.findViewById(R.id.tv_update);
                    ImageView iv_close = contentView.findViewById(R.id.iv_close);
                    TextView updatecontent = contentView.findViewById(R.id.update_content);
                    updatecontent.setText(updateApp.getUpdateLog());
                    if (delta == true) {
                        iv_close.setVisibility(View.GONE);
                    } else {
                        iv_close.setVisibility(View.VISIBLE);
                    }
                    tvUpdate.setOnClickListener(v -> {
                        RxPermissions rxPermissions = new RxPermissions(this);
                        rxPermissions.request(WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .subscribe(granted -> {
                                    if (granted) {
                                        utils.close();
                                        //显示下载进度
                                        if (isShowDownloadProgress) {
                                            updateAppManager.download(new DownloadService.DownloadCallback() {
                                                @Override
                                                public void onStart() {
                                                    HProgressDialogUtils.showHorizontalProgressDialog(MainActivity.this, "下载进度", false);
                                                }

                                                /**
                                                 * 进度
                                                 *
                                                 * @param progress  进度 0.00 -1.00 ，总大小
                                                 * @param totalSize 总大小 单位B
                                                 */
                                                @Override
                                                public void onProgress(float progress, long totalSize) {
                                                    HProgressDialogUtils.setProgress(Math.round(progress * 100));
                                                }

                                                /**
                                                 *
                                                 * @param total 总大小 单位B
                                                 */
                                                @Override
                                                public void setMax(long total) {

                                                }


                                                @Override
                                                public boolean onFinish(File file) {
                                                    HProgressDialogUtils.cancel();
                                                    boolean installAllowed;
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        installAllowed = getPackageManager().canRequestPackageInstalls();
                                                        if (installAllowed) {
                                                            installAPK(file);
                                                        } else {
                                                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                            installAPK(file);
                                                        }
                                                    } else {
                                                        installAPK(file);
                                                    }
                                                    return true;
                                                }

                                                @Override
                                                public void onError(String msg) {
                                                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                    HProgressDialogUtils.cancel();
                                                }

                                                @Override
                                                public boolean onInstallAppAndAppOnForeground(File file) {
                                                    return false;
                                                }
                                            });
                                        } else {
                                            //不显示下载进度
                                            updateAppManager.download();
                                        }
                                    }
                                });
                    });
                    iv_close.setOnClickListener(v -> {
                        utils.close();

                    });

                });

    }


    public void installAPK(File file) {
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkuri = null;
        //在服务器中开启flag
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            apkuri = FileProvider.getUriForFile(getApplicationContext(), "com.syb.cobank.FileProvider", file);
        } else {
            apkuri = Uri.parse("file://" + file.toString());
        }
        intent.setDataAndType(apkuri, "application/vnd.android.package-archive");
        startActivity(intent);
    }


    public EasyNavigationBar getNavigationBar() {
        return navigationBar;
    }
}
