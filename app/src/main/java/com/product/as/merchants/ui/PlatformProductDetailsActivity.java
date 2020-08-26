package com.product.as.merchants.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iceteck.silicompressorr.VideoCompress;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.VideoAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GetPriceEntity;
import com.product.as.merchants.entity.GoodsAddEntity;
import com.product.as.merchants.entity.GoodsInfoEntity;
import com.product.as.merchants.entity.UpdateAmountEntity;
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
import com.product.as.merchants.view.FullyGridLayoutManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.meikoz.core.util.TimeUtils.getNowMills;
import static com.meikoz.core.util.TimeUtils.getNowString;

//产品详情
public class PlatformProductDetailsActivity extends BaseActivity implements MyHandler.OnHandlerListener {

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
    @Bind(R.id.Confirm_inventory)
    EditText Confirminventory;
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
    @Bind(R.id.price)
    EditText price;
    @Bind(R.id.originalprice)
    EditText originalprice;
    @Bind(R.id.rcyvideo)
    RecyclerView rcyvideo;
    @Bind(R.id.Confirm_upload)
    TextView Confirm_upload;
    private MyHandler myHandler;
    private TransferManager transferManager;
    private CosXmlService cosXmlService;

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
    String destPath = "";
    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    private static final int SCAN_OPEN_VIDEO = 3;// 视频
    private VideoAdapter videoAdapter;
    List<LocalMedia> videolist = new ArrayList<>();
    private int themeId;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_platform_product_details;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {

        myHandler = new MyHandler<>(this);
        myHandler.setOnHandlerListener(this);
        initCosService();

        ivBack.setOnClickListener(v -> {
            finish();
        });
        textback.setVisibility(View.VISIBLE);
        tvTitles.setText("编辑产品");
        themeId = R.style.picture_default_style;
        Original_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        FullyGridLayoutManager manager3 = new FullyGridLayoutManager(PlatformProductDetailsActivity.this, 4, GridLayoutManager.VERTICAL, false);
        rcyvideo.setLayoutManager(manager3);
        videoAdapter = new VideoAdapter(PlatformProductDetailsActivity.this, onAddVideoClickListener);
        videoAdapter.setList(videolist);
        videoAdapter.setSelectMax(1);
        rcyvideo.setAdapter(videoAdapter);


        videoAdapter.setOnItemClickListener((position, v) -> {
            if (videolist.size() > 0) {
                LocalMedia media = videolist.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {

                    case 2:
                        // 预览视频
                        PictureSelector.create(PlatformProductDetailsActivity.this)
                                .themeStyle(R.style.picture_default_style)
                                .externalPictureVideo(videolist.get(0).getPath());

                        break;
                }
            }
        });
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
                    PictureFileUtils.deleteCacheDirFile(PlatformProductDetailsActivity.this);
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
                    Confirminventory.setText(goodsInfoEntity.getAmount() + "");
                    detail.setText(goodsInfoEntity.getDetail());
                    cataId.setText(goodsInfoEntity.getCata_name());
                    id = goodsInfoEntity.getCata_id();
                    cata_code = goodsInfoEntity.getCata_code();
                    Original_price.setText(goodsInfoEntity.getString_balance() + "");
                    specs.setText(goodsInfoEntity.getUnit() + "");
                    PlaceOrigin.setText(goodsInfoEntity.getOrigin());
                    price.setText(goodsInfoEntity.getString_pro_price() + "");
                    originalprice.setText(goodsInfoEntity.getString_pro_oldprice() + "");


                    if (goodsInfoEntity.getIs_delivery() == 0) {
                        is_delivery = 0;
                        Distribution.setText("限北京六环内 >>>");
                    } else {
                        is_delivery = 1;
                        Distribution.setText("全国（新疆|西藏除外）>>>");
                    }
                    myHandler.sendEmptyMessageDelayed(1, 100);


                }
            }

            @Override
            public void onFailure(Call<GoodsInfoEntity> call, Throwable t) {

            }
        });


        Confirm_upload.setOnClickListener(v -> {
            if (videolist.size() == 0) {
                loadingDialog = new LoadingDialog(this, "请稍候…");
                loadingDialog.show();
                update_goodsvideo(getIntent().getExtras().getInt("products_id"), "");
            } else {

                loadingDialog = new LoadingDialog(this, "文件上传中,请稍候…");
                loadingDialog.show();

                destPath = outputDir + File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + ".mp4";
                VideoCompress.compressVideoLow(videolist.get(0).getPath(), destPath, new VideoCompress.CompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess() {

                        Log.i(TAG, "压缩后大小 = " + getFileSize(destPath));
                        uploadvideo(destPath);
                    }

                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onProgress(float percent) {
                        Log.i(TAG, String.valueOf(percent) + "%");
                    }
                });


            }
        });


    }


    public void update_goodsvideo(int products_id, String path) {
        ApiInterfaceTwo.ApiFactory.createApi().update_goodsvideo(products_id, path).enqueue(new Callback<UpdateAmountEntity>() {
            @Override
            public void onResponse(Call<UpdateAmountEntity> call, Response<UpdateAmountEntity> response) {
                if (response.body().getFlag() == 1) {
                    loadingDialog.dismiss();
                    ToastUtil.show(response.body().getMsg().toString(), 200);
                }
            }

            @Override
            public void onFailure(Call<UpdateAmountEntity> call, Throwable t) {

            }
        });

    }


    private VideoAdapter.onAddPicClickListener onAddVideoClickListener = new VideoAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(PlatformProductDetailsActivity.this)
                    .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .previewImage(false)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //   .compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(false)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(videolist)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .cropCompressQuality(90)//
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    .videoQuality(1)// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    .recordVideoSecond(60)//录制视频秒数 默认60s
                    .forResult(SCAN_OPEN_VIDEO);//结果回调onActivityResult code

        }
    };


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        cataId.setText(intent.getExtras().getString("cata_name"));
        id = intent.getExtras().getInt("ID");
        cata_code = intent.getExtras().getString("cata_code");
    }


    @OnClick({R.id.Freshrelease, R.id.cata_id, R.id.Commodityone, R.id.Commoditytwo, R.id.Commoditythree, R.id.Commodityfour, R.id.Detailsone, R.id.Detailstwo, R.id.Detailsthree, R.id.Detailsfour, R.id.Distribution, R.id.Get_configuration, R.id.Confirm_inventory_adjustment})
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
                    ToastUtil.show("请填写结算价!", 25);
                    return;
                }
                if (TextUtils.isEmpty(price.getText().toString())) {
                    ToastUtil.show("请填写售价!", 25);
                    return;
                }
                if (TextUtils.isEmpty(originalprice.getText().toString())) {
                    ToastUtil.show("请填写原价!", 25);
                    return;
                }

                if (is_delivery == -1) {
                    ToastUtil.show("请选择配送范围!", 25);
                    return;
                }
                if (TextUtils.isEmpty(PlaceOrigin.getText().toString())) {
                    ToastUtil.show("请填写产地!", 25);
                    return;
                }


                loadingDialog = new LoadingDialog(this, "请稍候…");
                loadingDialog.show();

                myHandler.sendEmptyMessageDelayed(0, 1000);

                break;
            case R.id.cata_id:
                startActivity(CommodityCategoryTwoActivity.class);
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
                deliveryPopuWindow = new DeliveryPopuWindow(this, v -> {
                    UiUtils.backgroundAlpha(PlatformProductDetailsActivity.this, 1.0f);
                    is_delivery = 1;
                    Distribution.setText("全国（新疆|西藏除外）>>>");
                    deliveryPopuWindow.dismiss();
                }, v -> {
                    UiUtils.backgroundAlpha(PlatformProductDetailsActivity.this, 1.0f);
                    is_delivery = 0;
                    Distribution.setText("限北京六环内 >>>");
                    deliveryPopuWindow.dismiss();
                });
                rootView = LayoutInflater.from(this)
                        .inflate(R.layout.activity_main, null);
                deliveryPopuWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.Get_configuration:
                get_price(getIntent().getExtras().getInt("products_id"));
                break;
            case R.id.Confirm_inventory_adjustment:
                update_amount(getIntent().getExtras().getInt("products_id"), Integer.parseInt(Confirminventory.getText().toString()));
                break;
        }
    }

    public void get_price(int products_id) {
        ApiInterfaceTwo.ApiFactory.createApi().get_price(products_id).enqueue(new Callback<GetPriceEntity>() {
            @Override
            public void onResponse(Call<GetPriceEntity> call, Response<GetPriceEntity> response) {
                if (response.body().getFlag() == 1) {
                    Original_price.setText(response.body().getData().getBalance());
                    price.setText(response.body().getData().getPro_price());
                    originalprice.setText(response.body().getData().getPro_oldprice());
                }
            }

            @Override
            public void onFailure(Call<GetPriceEntity> call, Throwable t) {

            }
        });
    }


    public void update_amount(int products_id, int amount) {
        ApiInterfaceTwo.ApiFactory.createApi().update_amount(products_id, amount).enqueue(new Callback<UpdateAmountEntity>() {
            @Override
            public void onResponse(Call<UpdateAmountEntity> call, Response<UpdateAmountEntity> response) {
                if (response.body().getFlag() == 1) {

                    Confirminventory.clearFocus();
                    Confirminventory.setFocusable(false);
                    ToastUtil.show(response.body().getMsg().toString(), 200);
                }
            }

            @Override
            public void onFailure(Call<UpdateAmountEntity> call, Throwable t) {

            }
        });
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
            if (Commoditypath4 != null) {
                slist = Commoditypath + "," + Commoditypath2 + "," + Commoditypath3 + "," + Commoditypath4;
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
            goodsupdate(getIntent().getExtras().getInt("products_id"), productName.getText().toString(), productName.getText().toString(), id, cata_code, String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), Original_price.getText().toString(), PlaceOrigin.getText().toString(), specs.getText().toString(), "0", Commoditypath, slist, slist2, detail.getText().toString(), is_delivery, price.getText().toString(), originalprice.getText().toString());
        }else  if (what == 1){


            List<String> picslist = Arrays.asList(goodsInfoEntity.getPics().split(","));
            if (picslist.size() == 1) {
                Commoditypath = picslist.get(0).toString();
                GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
            } else if (picslist.size() == 2) {
                Commoditypath = picslist.get(0).toString();
                Commoditypath2 = picslist.get(1).toString();
                GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
                GlideUtils.loadBigImgWithGlide(picslist.get(1).toString(), Commoditytwo);
            } else if (picslist.size() == 3) {
                Commoditypath = picslist.get(0).toString();
                Commoditypath2 = picslist.get(1).toString();
                Commoditypath3 = picslist.get(2).toString();
                GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
                GlideUtils.loadBigImgWithGlide(picslist.get(1).toString(), Commoditytwo);
                GlideUtils.loadBigImgWithGlide(picslist.get(2).toString(), Commoditythree);
            } else if (picslist.size() == 4) {
                Commoditypath = picslist.get(0).toString();
                Commoditypath2 = picslist.get(1).toString();
                Commoditypath3 = picslist.get(2).toString();
                Commoditypath4 = picslist.get(3).toString();
                GlideUtils.loadBigImgWithGlide(picslist.get(0).toString(), Commodityone);
                GlideUtils.loadBigImgWithGlide(picslist.get(1).toString(), Commoditytwo);
                GlideUtils.loadBigImgWithGlide(picslist.get(2).toString(), Commoditythree);
                GlideUtils.loadBigImgWithGlide(picslist.get(3).toString(), Commodityfour);
            }


            List<String> detaillist = Arrays.asList(goodsInfoEntity.getDetail_pics().split(","));
            if (detaillist.size() == 1) {
                Detailspath = detaillist.get(0).toString();
                GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
            } else if (detaillist.size() == 2) {
                Detailspath = detaillist.get(0).toString();
                Detailspath2 = detaillist.get(1).toString();
                GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
                GlideUtils.loadBigImgWithGlide(detaillist.get(1).toString(), Detailstwo);
            } else if (detaillist.size() == 3) {
                Detailspath = detaillist.get(0).toString();
                Detailspath2 = detaillist.get(1).toString();
                Detailspath3 = detaillist.get(2).toString();
                GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
                GlideUtils.loadBigImgWithGlide(detaillist.get(1).toString(), Detailstwo);
                GlideUtils.loadBigImgWithGlide(detaillist.get(2).toString(), Detailsthree);
            } else if (detaillist.size() == 4) {
                Detailspath = detaillist.get(0).toString();
                Detailspath2 = detaillist.get(1).toString();
                Detailspath3 = detaillist.get(2).toString();
                Detailspath4 = detaillist.get(3).toString();
                GlideUtils.loadBigImgWithGlide(detaillist.get(0).toString(), Detailsone);
                GlideUtils.loadBigImgWithGlide(detaillist.get(1).toString(), Detailstwo);
                GlideUtils.loadBigImgWithGlide(detaillist.get(2).toString(), Detailsthree);
                GlideUtils.loadBigImgWithGlide(detaillist.get(3).toString(), Detailsfour);
            }


            if (goodsInfoEntity.getDetail_video() != null && !TextUtils.isEmpty(goodsInfoEntity.getDetail_video())) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(goodsInfoEntity.getDetail_video());
                localMedia.setPictureType("video/mp4");
                localMedia.setMimeType(2);
                MediaPlayer mediaPlayer = new MediaPlayer();
                Uri uri= Uri.parse(goodsInfoEntity.getDetail_video());
                try {
                    mediaPlayer.setDataSource(context,uri);
                    mediaPlayer.prepare();
                    int time=  mediaPlayer.getDuration();
                    localMedia.setDuration(time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                videolist.add(localMedia);
                videoAdapter.setList(videolist);
                videoAdapter.notifyDataSetChanged();

            }

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
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());

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
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());

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
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());
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
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());
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
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());
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
                            Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + data.getData());
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


                case SCAN_OPEN_VIDEO:

                    videolist = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    for (LocalMedia media : videolist) {
                        Log.i(TAG, "压缩---->" + media.getCompressPath());
                        Log.i(TAG, "原图---->" + media.getPath());
                        Log.i(TAG, "裁剪---->" + media.getCutPath());
                        Log.i(TAG, "宽---->高-----》" + media.getWidth() + "---" + media.getHeight());
                    }
                    videoAdapter.setList(videolist);
                    videoAdapter.notifyDataSetChanged();
                    break;

            }


        }

    }


    public void goodsupdate(int products_id, String product_name, String short_name, int cata_id, String cata_code, String saler_id, String pro_oldprice, String specss, String unit, String weight, String icon, String pics, String detail_pics, String detail, int is_delivery, String price, String old_price) {
        ApiInterfaceTwo.ApiFactory.createApi().platform_update(products_id, product_name, short_name, cata_id, cata_code, saler_id, pro_oldprice, specss, unit, weight, icon, pics, detail_pics, detail, is_delivery, price, old_price).enqueue(new Callback<GoodsAddEntity>() {
            @Override
            public void onResponse(Call<GoodsAddEntity> call, Response<GoodsAddEntity> response) {
                if (response.body().getFlag() == 1) {

                        loadingDialog.dismiss();
                        ToastUtil.show("发布成功", 25);

                        switch (getIntent().getExtras().getInt("status")) {
                            case 0:
                                NoticeObserver.getInstance().notifyObservers(Constants.GOODSUPDATE, productName.getText().toString() + "&" + Original_price.getText().toString() + "&" + specs.getText().toString() + "&" + PlaceOrigin.getText().toString());
                                break;
                            case 1:
                                NoticeObserver.getInstance().notifyObservers(Constants.GOODSUPDATE2, productName.getText().toString() + "&" + Original_price.getText().toString() + "&" + specs.getText().toString() + "&" + PlaceOrigin.getText().toString());
                                break;
                            case 2:
                                NoticeObserver.getInstance().notifyObservers(Constants.GOODSUPDATE3, productName.getText().toString() + "&" + Original_price.getText().toString() + "&" + specs.getText().toString() + "&" + PlaceOrigin.getText().toString());
                                break;
                        }
                        finish();

                } else {
                    loadingDialog.dismiss();
                    ToastUtil.show(response.body().getMsg().toString(), 25);

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
        upload(bucket, cosPath, localPath, 1);
    }

    public void uploadvideo(String localPath) {
        String bucket = "freshcyber-1257941117"; // 上传的 bucket 名称，region 为之前设置的 ap-guangzhou
        String cosPath = "video/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowMills() + "/" + localPath.substring(localPath.lastIndexOf("/") + 1); // 上传到 COS 的对象地址
        upload(bucket, cosPath, localPath, 2);
    }


    /**
     * 上传
     *
     * @params bucket  bucket 名称
     * @params cosPath 上传到 COS 的路径
     * @params localPath 本地文件路径
     */
    public void upload(String bucket, String cosPath, String localPath, int type) {

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
                Log.e("CosXmlResult", result.accessUrl);

                // TODO: 2018/10/22
                if (type == 2) {
                    update_goodsvideo(getIntent().getExtras().getInt("products_id"), result.accessUrl);
                }
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                // TODO: 2018/10/22
                Log.e("上传失败,请重新上传", serviceException.toString());
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


    private Locale getLocale() {
        Configuration config = getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    private String getFileSize(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return "0 MB";
        } else {
            long size = f.length();
            return (size / 1024f) / 1024f + "MB";
        }
    }


    public static String getRingDuring(String mUri) {
        String duration = null;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();

        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            }

            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        Log.e("ryan", "duration " + duration);
        return duration;
    }
}


