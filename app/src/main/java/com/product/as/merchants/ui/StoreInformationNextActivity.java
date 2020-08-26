package com.product.as.merchants.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.MainActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.WholeSalerEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.popuwindow.PhotoPopupWindow;
import com.product.as.merchants.util.BitmapAndStringUtils;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.UiUtils;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.meikoz.core.util.TimeUtils.getNowString;

//门店信息2
public class StoreInformationNextActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.doorface)
    ImageView doorface;
    @Bind(R.id.Licence)
    ImageView Licence;
    @Bind(R.id.FrontIdCard)
    ImageView FrontIdCard;
    @Bind(R.id.FrontIdsideCard)
    ImageView FrontIdsideCard;
    @Bind(R.id.Handphotograph)
    ImageView Handphotograph;
    @Bind(R.id.HandphotographSide)
    ImageView HandphotographSide;
    @Bind(R.id.end)
    TextView end;
    private String saler_id;
    private PhotoPopupWindow mPhotoPopupWindow;
    private static final int REQUEST_TAKE_PHOTO = 0;// 拍照
    private static final int REQUEST_CROP = 1;// 裁剪
    private static final int SCAN_OPEN_PHONE = 2;// 相册
    private File imgFile;// 拍照保存的图片文件
    private Uri imgUri; // 拍照时返回的uri
    private Uri mCutUri;// 图片裁剪时返回的uri
    private String corp_url, licence_url, legal_pic, legal_back_pic, op_pic, op_back_pic;
    String path = null;
    int type = 1;
    View rootView;
    private TransferManager transferManager;
    private CosXmlService cosXmlService;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_store_information_next;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        initCosService();
        tvTitles.setText("门店信息(2/2)");
        saler_id = getIntent().getExtras().getString("saler_id");
    }

    @OnClick({R.id.end, R.id.doorface, R.id.Licence, R.id.FrontIdCard, R.id.FrontIdsideCard, R.id.Handphotograph, R.id.HandphotographSide})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.end:
                if (TextUtils.isEmpty(corp_url) || corp_url == null) {
                    ToastUtil.show("请上传营业执照", 25);
                    return;
                }
                if (TextUtils.isEmpty(licence_url) || licence_url == null) {
                    ToastUtil.show("请上传许可证", 25);
                    return;
                }
                if (TextUtils.isEmpty(legal_pic) || legal_pic == null) {
                    ToastUtil.show("请上传法人身份证正面", 25);
                    return;
                }
                if (TextUtils.isEmpty(legal_back_pic) || legal_back_pic == null) {
                    ToastUtil.show("请上传法人身份证反面", 25);
                    return;
                }
                if (TextUtils.isEmpty(op_pic)&&TextUtils.isEmpty(op_back_pic)){
                    wholesalerprofilecreate(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")),saler_id,"" ,"",corp_url,licence_url,legal_pic,legal_back_pic,"","");
                }else if (TextUtils.isEmpty(op_pic)&&!TextUtils.isEmpty(op_back_pic)) {
                    wholesalerprofilecreate(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")),saler_id,"" ,"",corp_url,licence_url,legal_pic,legal_back_pic,"",op_back_pic);
                }else if (!TextUtils.isEmpty(op_pic)&&TextUtils.isEmpty(op_back_pic)){
                    wholesalerprofilecreate(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")),saler_id,"" ,"",corp_url,licence_url,legal_pic,legal_back_pic,op_pic,"");
                }else {
                    wholesalerprofilecreate(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")),saler_id,"" ,"",corp_url,licence_url,legal_pic,legal_back_pic,op_pic,op_back_pic);
                }

                break;
            case R.id.doorface:
                type = 1;
                mPhotoPopupWindow = new PhotoPopupWindow(this, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    } else {
                        // 如果权限已经申请过，直接进行图片选择
                        mPhotoPopupWindow.dismiss();
                        openGallery();
                    }
                }, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                    } else {
                        // 权限已经申请，直接拍照
                        mPhotoPopupWindow.dismiss();
                        takePhoto();
                    }
                });
                rootView = LayoutInflater.from(this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.Licence:  //许可证
                type = 2;
                mPhotoPopupWindow = new PhotoPopupWindow(this, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    } else {
                        // 如果权限已经申请过，直接进行图片选择
                        mPhotoPopupWindow.dismiss();
                        openGallery();
                    }
                }, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                    } else {
                        // 权限已经申请，直接拍照
                        mPhotoPopupWindow.dismiss();
                        takePhoto();
                    }
                });
                rootView = LayoutInflater.from(this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.FrontIdCard: //身份证正面
                type = 3;
                mPhotoPopupWindow = new PhotoPopupWindow(this, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    } else {
                        // 如果权限已经申请过，直接进行图片选择
                        mPhotoPopupWindow.dismiss();
                        openGallery();
                    }
                }, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                    } else {
                        // 权限已经申请，直接拍照
                        mPhotoPopupWindow.dismiss();
                        takePhoto();
                    }
                });
                rootView = LayoutInflater.from(this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.FrontIdsideCard://身份证反面
                type = 4;
                mPhotoPopupWindow = new PhotoPopupWindow(this, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    } else {
                        // 如果权限已经申请过，直接进行图片选择
                        mPhotoPopupWindow.dismiss();
                        openGallery();
                    }
                }, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                    } else {
                        // 权限已经申请，直接拍照
                        mPhotoPopupWindow.dismiss();
                        takePhoto();
                    }
                });
                rootView = LayoutInflater.from(this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.Handphotograph:  //经办人正面
                type = 5;
                mPhotoPopupWindow = new PhotoPopupWindow(this, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    } else {
                        // 如果权限已经申请过，直接进行图片选择
                        mPhotoPopupWindow.dismiss();
                        openGallery();
                    }
                }, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                    } else {
                        // 权限已经申请，直接拍照
                        mPhotoPopupWindow.dismiss();
                        takePhoto();
                    }
                });
                rootView = LayoutInflater.from(this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.HandphotographSide:  //经办人反面

                type = 6;
                mPhotoPopupWindow = new PhotoPopupWindow(this, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    } else {
                        // 如果权限已经申请过，直接进行图片选择
                        mPhotoPopupWindow.dismiss();
                        openGallery();
                    }
                }, v -> {
                    UiUtils.backgroundAlpha(StoreInformationNextActivity.this, 1.0f);
                    // 权限申请
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                    } else {
                        // 权限已经申请，直接拍照
                        mPhotoPopupWindow.dismiss();
                        takePhoto();
                    }
                });
                rootView = LayoutInflater.from(this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SCAN_OPEN_PHONE);
    }

    /**
     * 判断系统及拍照
     */
    private void takePhoto() {
        // 要保存的文件名
        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        String fileName = "photo_" + time;
        // 创建一个文件夹
        String path = Environment.getExternalStorageDirectory() + "/take_photo";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 要保存的图片文件
        imgFile = new File(file, fileName + ".jpeg");
        // 将file转换成uri
        // 注意7.0及以上与之前获取的uri不一样了，返回的是provider路径
        imgUri = getUriForFile(this, imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 添加Uri读取权限
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        // 或者
//        grantUriPermission("com.rain.takephotodemo", imgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 添加图片保存位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }


    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.product.as.merchants.FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


    /**
     * 处理回调结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CROP:
                    switch (type) {
                        case 1:
                            doorface.setImageURI(mCutUri);
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());

                            try {
                                path = BitmapAndStringUtils.getPath(this, mCutUri);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            corp_url = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/"+SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1);
                            uploadphoto(path);
                            break;
                        case 2:
                            Licence.setImageURI(mCutUri);
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());

                            try {
                                path = BitmapAndStringUtils.getPath(this, mCutUri);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            licence_url = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/"+SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1);
                            uploadphoto(path);
                            break;
                        case 3:
                            FrontIdCard.setImageURI(mCutUri);
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());
                            try {
                                path = BitmapAndStringUtils.getPath(this, mCutUri);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            legal_pic = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/"+SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1);
                            uploadphoto(path);
                            break;
                        case 4:
                            FrontIdsideCard.setImageURI(mCutUri);
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());
                            try {
                                path = BitmapAndStringUtils.getPath(this, mCutUri);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            legal_back_pic = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/"+SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1);
                            uploadphoto(path);
                            break;
                        case 5:
                            Handphotograph.setImageURI(mCutUri);
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());
                            try {
                                path = BitmapAndStringUtils.getPath(this, mCutUri);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            op_pic = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/"+SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1);
                            uploadphoto(path);
                            break;
                        case  6:
                            HandphotographSide.setImageURI(mCutUri);
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());
                            try {
                                path = BitmapAndStringUtils.getPath(this, mCutUri);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            op_back_pic = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/"+SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1);
                            uploadphoto(path);
                            break;
                    }

                    break;
                // 相册选取
                case SCAN_OPEN_PHONE:
                    Log.e(TAG, "onActivityResult: SCAN_OPEN_PHONE:" + data.getData().toString());
                    cropPhoto(data.getData(), false);
                    break;
                // 拍照
                case REQUEST_TAKE_PHOTO:
                    Log.e(TAG, "onActivityResult: imgUri:REQUEST_TAKE_PHOTO:" + imgUri.toString());
                    cropPhoto(imgUri, true);
                    break;

            }
        }
    }


    private void cropPhoto(Uri uri, boolean fromCapture) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("scale", true);

        if (Build.MANUFACTURER.equals("HUAWEI")) {
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 指定裁剪完成以后的图片所保存的位置,pic info显示有延时
        if (fromCapture) {
            // 如果是使用拍照，那么原先的uri和最终目标的uri一致,注意这里的uri必须是Uri.fromFile生成的
            mCutUri = Uri.fromFile(imgFile);
        } else { // 从相册中选择，那么裁剪的图片保存在take_photo中
            String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
            String fileName = "photo_" + time;
            File mCutFile = new File(Environment.getExternalStorageDirectory() + "/take_photo/", fileName + ".jpeg");
            if (!mCutFile.getParentFile().exists()) {
                mCutFile.getParentFile().mkdirs();
            }
            mCutUri = Uri.fromFile(mCutFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(uri);
        sendBroadcast(intentBc);

        startActivityForResult(intent, REQUEST_CROP); //设置裁剪参数显示图片至ImageVie
    }

    public void uploadphoto(String localPath) {
        String bucket = "freshcyber-1257941117"; // 上传的 bucket 名称，region 为之前设置的 ap-guangzhou
        String cosPath = "saler/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + localPath.substring(localPath.lastIndexOf("/") + 1); // 上传到 COS 的对象地址
        upload(bucket, cosPath, localPath);
    }

    /**
     * 上传
     *
     * @params bucket  bucket 名称
     * @params cosPath 上传到 COS 的路径
     * @params localPath 本地文件路径
     */
    public void upload(String bucket, String cosPath, String localPath) {

        // 开始上传，并返回生成的 COSXMLUploadTask
        COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath,
                localPath, null);

        // 设置上传状态监听
        cosxmlUploadTask.setTransferStateListener(new TransferStateListener() {
            @Override
            public void onStateChanged(final TransferState state) {
                // TODO: 2018/10/22
            }
        });

        // 设置上传进度监听
        cosxmlUploadTask.setCosXmlProgressListener(new CosXmlProgressListener() {
            @Override
            public void onProgress(final long complete, final long target) {
                // TODO: 2018/10/22
            }
        });

        // 设置结果监听
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                // TODO: 2018/10/22
                Log.e("1111", "111");
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                // TODO: 2018/10/22
                ToastUtil.show("图片上传失败请重新上传!", 200);
                return;
            }
        });
    }


    private void initCosService() {
        String region = "ap-beijing";
        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setRegion(region)
                .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                .builder();

        String secretId = "AKIDVotkeVGXUVXJDjK1HJBqHQO9ex53aLyM"; //永久密钥 secretId
        String secretKey = "LRzgU5RwFMx4xxiDpDMFaj14ry0sV8X4"; //永久密钥 secretKey
        QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(secretId, secretKey, 300);

        cosXmlService = new CosXmlService(this, serviceConfig, credentialProvider);

        TransferConfig transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(cosXmlService, transferConfig);
    }
  public void  wholesalerprofilecreate(String uid,String SalerID,String LegalName,String legal_no,String corp_url,String licence_url,String legal_pic,String legal_back_pic,String op_pic,String op_back_pic ){
      ApiInterface.ApiFactory.createApi().wholesalerprofilecreate(uid,SalerID,LegalName,legal_no,corp_url,licence_url,legal_pic,legal_back_pic,op_pic,op_back_pic).enqueue(new Callback<WholeSalerEntity>() {
          @Override
          public void onResponse(Call<WholeSalerEntity> call, Response<WholeSalerEntity> response) {
              if (response.body().getFlag()==1){

                  startfinishActivity(UnderReviewActivity.class,null);
              }
          }

          @Override
          public void onFailure(Call<WholeSalerEntity> call, Throwable t) {

          }
      });
  }
}
