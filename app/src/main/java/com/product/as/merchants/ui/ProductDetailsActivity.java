package com.product.as.merchants.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsAddEntity;
import com.product.as.merchants.entity.GoodsInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.popuwindow.DeliveryPopuWindow;
import com.product.as.merchants.util.BitmapAndStringUtils;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.util.LoadingDialog;
import com.product.as.merchants.util.MyHandler;
import com.product.as.merchants.util.NoticeObserver;
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
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.meikoz.core.util.TimeUtils.getNowString;

//产品详情
public class ProductDetailsActivity extends BaseActivity implements MyHandler.OnHandlerListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.textback)
    TextView textback;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.product_name)
    EditText productName;
    @Bind(R.id.detail)
    EditText detail;
    @Bind(R.id.cata_id)
    TextView cataId;
    @Bind(R.id.specs)
    EditText specs;

    @Bind(R.id.Freshrelease)
    ImageView Freshrelease;
    @Bind(R.id.PlaceOrigin)
    EditText PlaceOrigin;
    @Bind(R.id.Original_price)
    EditText Original_price;


    private final static String TAG = ReleaseCommoditiesActivity.class.getSimpleName();
    LoadingDialog loadingDialog;
    @Bind(R.id.Commodityone)
    ImageView Commodityone;
    @Bind(R.id.Commoditytwo)
    ImageView Commoditytwo;
    @Bind(R.id.Commoditythree)
    ImageView Commoditythree;
    @Bind(R.id.Commodityfour)
    ImageView Commodityfour;
    @Bind(R.id.Detailsone)
    ImageView Detailsone;
    @Bind(R.id.Detailstwo)
    ImageView Detailstwo;
    @Bind(R.id.Detailsthree)
    ImageView Detailsthree;
    @Bind(R.id.Detailsfour)
    ImageView Detailsfour;
    @Bind(R.id.Distribution)
    TextView Distribution;
    private MyHandler myHandler;
    private TransferManager transferManager;
    private CosXmlService cosXmlService;
    private File imgFile;// 拍照保存的图片文件
    private Uri imgUri; // 拍照时返回的uri
    private Uri mCutUri;// 图片裁剪时返回的uri
    private int id;
    private String cata_code;
    private GoodsInfoEntity.DataBean goodsInfoEntity;
    private String slist;
    private String slist2;
    private String Commoditypath, Commoditypath2, Commoditypath3, Commoditypath4;
    private String Detailspath, Detailspath2, Detailspath3, Detailspath4;
    private int type;
    String path = null;
    private DeliveryPopuWindow deliveryPopuWindow;
    private int is_delivery = -1;
    View rootView;
    private static final int SCAN_OPEN_PHONE = 2;// 相册


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        initCosService();
        ivBack.setOnClickListener(v -> {
            finish();
        });
        textback.setVisibility(View.VISIBLE);
        tvTitles.setText("编辑产品");

        Original_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(ProductDetailsActivity.this);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

        ApiInterfaceTwo.ApiFactory.createApi().goodsinfo(getIntent().getExtras().getInt("products_id")).enqueue(new Callback<GoodsInfoEntity>() {
            @Override
            public void onResponse(Call<GoodsInfoEntity> call, Response<GoodsInfoEntity> response) {
                if (response.body().getFlag() == 1) {
                    goodsInfoEntity = response.body().getData();
                    productName.setText(goodsInfoEntity.getProduct_name());
                    productName.setSelection(productName.getText().length());
                    detail.setText(goodsInfoEntity.getDetail());
                    cataId.setText(goodsInfoEntity.getCata_name());
                    id = goodsInfoEntity.getCata_id();
                    cata_code = goodsInfoEntity.getCata_code();
                    Original_price.setText(goodsInfoEntity.getString_balance() + "");
                    specs.setText(goodsInfoEntity.getUnit() + "");
                    PlaceOrigin.setText(goodsInfoEntity.getOrigin());

                    if (goodsInfoEntity.getIs_delivery()==0){
                        is_delivery = 0;
                        Distribution.setText("限北京六环内 >>>");
                    }else {
                        is_delivery = 1;
                        Distribution.setText("全国（新疆|西藏除外）>>>");
                    }

                    List<String> picslist = Arrays.asList(goodsInfoEntity.getPics().split(","));
                    if (picslist.size() == 1) {
                        Commoditypath=picslist.get(0).toString();
                        GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
                    } else if (picslist.size() == 2) {
                        Commoditypath=picslist.get(0).toString();
                        Commoditypath2=picslist.get(1).toString();
                        GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
                        GlideUtils.loadBigImgWithGlide(picslist.get(1).toString(), Commoditytwo);
                    } else if (picslist.size() == 3) {
                        Commoditypath=picslist.get(0).toString();
                        Commoditypath2=picslist.get(1).toString();
                        Commoditypath3=picslist.get(2).toString();
                        GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
                        GlideUtils.loadBigImgWithGlide(picslist.get(1).toString(), Commoditytwo);
                        GlideUtils.loadBigImgWithGlide(picslist.get(2).toString(), Commoditythree);
                    }else if (picslist.size() == 4){
                        Commoditypath=picslist.get(0).toString();
                        Commoditypath2=picslist.get(1).toString();
                        Commoditypath3=picslist.get(2).toString();
                        Commoditypath4=picslist.get(3).toString();
                        GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
                        GlideUtils.loadBigImgWithGlide(picslist.get(1).toString(), Commoditytwo);
                        GlideUtils.loadBigImgWithGlide(picslist.get(2).toString(), Commoditythree);
                        GlideUtils.loadBigImgWithGlide(picslist.get(3).toString(), Commodityfour);
                    }


                    List<String> detaillist = Arrays.asList(goodsInfoEntity.getDetail_pics().split(","));
                    if (detaillist.size() == 1) {
                        Detailspath=detaillist.get(0).toString();
                        GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
                    } else if (detaillist.size() == 2) {
                        Detailspath=detaillist.get(0).toString();
                        Detailspath2=detaillist.get(1).toString();
                        GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
                        GlideUtils.loadBigImgWithGlide(detaillist.get(1).toString(), Detailstwo);
                    } else if (detaillist.size() == 3) {
                        Detailspath=detaillist.get(0).toString();
                        Detailspath2=detaillist.get(1).toString();
                        Detailspath3=detaillist.get(2).toString();
                        GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
                        GlideUtils.loadBigImgWithGlide(detaillist.get(1).toString(), Detailstwo);
                        GlideUtils.loadBigImgWithGlide(detaillist.get(2).toString(), Detailsthree);
                    } else if (detaillist.size() == 4) {
                        Detailspath=detaillist.get(0).toString();
                        Detailspath2=detaillist.get(1).toString();
                        Detailspath3=detaillist.get(2).toString();
                        Detailspath4=detaillist.get(3).toString();
                        GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
                        GlideUtils.loadBigImgWithGlide(detaillist.get(1).toString(), Detailstwo);
                        GlideUtils.loadBigImgWithGlide(detaillist.get(2).toString(), Detailsthree);
                        GlideUtils.loadBigImgWithGlide(detaillist.get(3).toString(), Detailsfour);
                    }
                }
            }

            @Override
            public void onFailure(Call<GoodsInfoEntity> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        cataId.setText(intent.getExtras().getString("cata_name"));
        id = intent.getExtras().getInt("ID");
        cata_code = intent.getExtras().getString("cata_code");
    }


    @OnClick({R.id.Freshrelease, R.id.cata_id, R.id.Commodityone, R.id.Commoditytwo, R.id.Commoditythree, R.id.Commodityfour, R.id.Detailsone, R.id.Detailstwo, R.id.Detailsthree, R.id.Detailsfour,R.id.Distribution})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.Freshrelease:
                if (TextUtils.isEmpty(productName.getText().toString())) {
                    ToastUtil.show("请输入标题!", 25);
                    return;
                }

                if (TextUtils.isEmpty(cataId.getText().toString())) {
                    ToastUtil.show("请选择产品分类!", 25);
                    return;
                }
                if (TextUtils.isEmpty(specs.getText().toString())) {
                    ToastUtil.show("请填写具体规格!", 25);
                    return;
                }
                if (TextUtils.isEmpty(Original_price.getText().toString())) {
                    ToastUtil.show("请填写原价!", 25);
                    return;
                }

                if (is_delivery==-1) {
                    ToastUtil.show("请选择配送范围!", 25);
                    return;
                }
                if (TextUtils.isEmpty(PlaceOrigin.getText().toString())) {
                    ToastUtil.show("请填写产地!", 25);
                    return;
                }


                loadingDialog = new LoadingDialog(this, "请稍候…");
                loadingDialog.show();
                myHandler = new MyHandler<>(this);
                myHandler.setOnHandlerListener(this);
                myHandler.sendEmptyMessageDelayed(0, 1000);

                break;
           case R.id.cata_id:
//                startActivity(CommodityCategoryTwoActivity.class);
               break;
            case R.id.Commodityone:
                type = 1;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Commoditytwo:
                type = 2;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Commoditythree:
                type = 3;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Commodityfour:
                type = 4;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Detailsone:
                type = 5;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Detailstwo:
                type = 6;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Detailsthree:
                type = 7;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Detailsfour:
                type = 8;
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    openGallery();
                }
                break;
            case R.id.Distribution:
//
//                deliveryPopuWindow = new DeliveryPopuWindow(this, v -> {
//                    UiUtils.backgroundAlpha(ProductDetailsActivity.this, 1.0f);
//                    is_delivery = 1;
//                    Distribution.setText("全国（新疆|西藏除外）>>>");
//                    deliveryPopuWindow.dismiss();
//                }, v -> {
//                    UiUtils.backgroundAlpha(ProductDetailsActivity.this, 1.0f);
//                    is_delivery = 0;
//                    Distribution.setText("限北京六环内 >>>");
//                    deliveryPopuWindow.dismiss();
//                });
//                rootView = LayoutInflater.from(this)
//                        .inflate(R.layout.activity_main, null);
//                deliveryPopuWindow.showAtLocation(rootView,
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }


    @Override
    public void handlerMessage(Message msg) {
        int what = msg.what;
        if (what == 0) {
            if (Commoditypath != null) {
                slist = Commoditypath;
            }
            if (Commoditypath2 != null) {
                slist = Commoditypath + "," + Commoditypath2;
            }
            if (Commoditypath3 != null) {
                slist = Commoditypath + "," + Commoditypath2 + "," + Commoditypath3;
            }
            if (Commoditypath4!=null){
                slist = Commoditypath + "," + Commoditypath2 + "," + Commoditypath3+","+Commoditypath4;
            }
            if (Detailspath != null) {
                slist2 = Detailspath;
            }
            if (Detailspath2 != null) {
                slist2 = Detailspath + "," + Detailspath2;
            }
            if (Detailspath3 != null) {
                slist2 = Detailspath + "," + Detailspath2 + "," + Detailspath3;
            }
            if (Detailspath4 != null) {
                slist2 = Detailspath + "," + Detailspath2 + "," + Detailspath3 + "," + Detailspath4;
            }
            goodsupdate(getIntent().getExtras().getInt("products_id"), productName.getText().toString(), productName.getText().toString(), id, cata_code, String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), Original_price.getText().toString(), PlaceOrigin.getText().toString(), specs.getText().toString(),"0",  Commoditypath, slist, slist2, detail.getText().toString(),is_delivery);
        }
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SCAN_OPEN_PHONE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_OPEN_PHONE:
                    switch (type) {
                        case 1:
                            Commodityone.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" +data.getData());

                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Commoditypath = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;

                            uploadphoto(path);
                            break;
                        case 2:
                            Commoditytwo.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" +data.getData());

                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());

                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Commoditypath2 = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;

                            uploadphoto(path);
                            break;
                        case 3:
                            Commoditythree.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" +data.getData());
                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Commoditypath3 = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;
                            uploadphoto(path);
                            break;
                        case 4:
                            Commodityfour.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" +data.getData());
                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Commoditypath4 = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;
                            uploadphoto(path);
                            break;
                        case 5:
                            Detailsone.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" +data.getData());
                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Detailspath = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;
                            uploadphoto(path);
                            break;
                        case 6:
                            Detailstwo.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());
                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Detailspath2 = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;
                            uploadphoto(path);
                            break;
                        case 7:
                            Detailsthree.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());
                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Detailspath3 = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;
                            uploadphoto(path);
                            break;
                        case 8:
                            Detailsfour.setImageURI(data.getData());
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" +data.getData());
                            try {
                                path = BitmapAndStringUtils.getPath(this, data.getData());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Detailspath4 = "https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + path.substring(path.lastIndexOf("/") + 1); // 上传到 COS 的对象地址;
                            uploadphoto(path);
                            break;
                    }
                    break;

            }
        }
    }




    public void goodsupdate(int products_id, String product_name, String short_name, int cata_id, String cata_code, String saler_id, String pro_oldprice, String specss, String unit, String weight,  String icon, String pics, String detail_pics, String detail,int is_delivery) {
        ApiInterfaceTwo.ApiFactory.createApi().goodsupdate(products_id, product_name, short_name, cata_id, cata_code, saler_id, pro_oldprice, specss, unit, weight, icon, pics, detail_pics, detail,is_delivery).enqueue(new Callback<GoodsAddEntity>() {
            @Override
            public void onResponse(Call<GoodsAddEntity> call, Response<GoodsAddEntity> response) {
                if (response.body().getFlag() == 1) {
                    if (response.body().getFlag() == 1) {
                        loadingDialog.dismiss();
                        ToastUtil.show("发布成功", 25);
                        NoticeObserver.getInstance().notifyObservers(Constants.GOODSUPDATE,productName.getText().toString()+"&"+Original_price.getText().toString()+"&"+specs.getText().toString()+"&"+PlaceOrigin.getText().toString());
                        finish();
                    } else {
                        loadingDialog.dismiss();
                        ToastUtil.show(response.body().getMsg().toString(), 25);

                    }
                }
            }

            @Override
            public void onFailure(Call<GoodsAddEntity> call, Throwable t) {

            }
        });
    }


    public void uploadphoto(String localPath) {
        String bucket = "freshcyber-1257941117"; // 上传的 bucket 名称，region 为之前设置的 ap-guangzhou
        String cosPath = "product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + localPath.substring(localPath.lastIndexOf("/") + 1); // 上传到 COS 的对象地址
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
        cosxmlUploadTask.setTransferStateListener(state -> {
            // TODO: 2018/10/22
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
                Log.e("图片上传失败,请重新上传", serviceException.toString());
                loadingDialog.dismiss();
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


}

