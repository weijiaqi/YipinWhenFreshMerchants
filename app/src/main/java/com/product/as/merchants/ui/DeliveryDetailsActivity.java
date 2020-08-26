package com.product.as.merchants.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.Joiner;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.meikoz.core.base.BaseActivity;
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.GridSmallImageAdapter;
import com.product.as.merchants.api.ApiInterfaceThree;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.entity.OrderUpdateEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.LoadingDialog;
import com.product.as.merchants.util.MyHandler;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
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
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.meikoz.core.util.TimeUtils.getNowString;

//确认送达详情
public class DeliveryDetailsActivity extends BaseActivity implements MyHandler.OnHandlerListener {

    @Bind(R.id.rcysmall)
    RecyclerView rcysmall;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.Ordernumber)
    TextView Ordernumber;
    @Bind(R.id.Addressee)
    TextView Addressee;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.dates)
    TextView dates;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.Confirmation)
    TextView Confirmation;
    private GridSmallImageAdapter adapter2;
    List<LocalMedia> selectsmallList = new ArrayList<>();
    private int themeId;
    LoadingDialog loadingDialog;
    private MyHandler myHandler;
    private final static String TAG = DeliveryDetailsActivity.class.getSimpleName();
    private List<String> list = new ArrayList<>();
    private TransferManager transferManager;
    private CosXmlService cosXmlService;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_delivery_details;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        initCosService();
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("上传凭证");
        Ordernumber.setText("订单号: " + getIntent().getExtras().getString("group_no"));
        Addressee.setText("收件人: " + getIntent().getExtras().getString("rec_name"));
        address.setText("地址: " + getIntent().getExtras().getString("address"));
        dates.setText("时间: " + TimeUtils.getNowString());
        number.setText("商品数: " + getIntent().getExtras().getString("number"));
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(DeliveryDetailsActivity.this, 4, GridLayoutManager.VERTICAL, false);
        rcysmall.setLayoutManager(manager2);
        adapter2 = new GridSmallImageAdapter(DeliveryDetailsActivity.this, onAddSmallPicClickListener);
        adapter2.setList(selectsmallList);
        adapter2.setSelectMax(4);
        rcysmall.setAdapter(adapter2);

        themeId = R.style.picture_default_style;
        adapter2.setOnItemClickListener((position, v) -> {
            if (selectsmallList.size() > 0) {
                LocalMedia media = selectsmallList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create(DeliveryDetailsActivity.this).themeStyle(themeId).openExternalPreview(position, selectsmallList);
                        break;
                }
            }
        });
    }

    private GridSmallImageAdapter.onAddPicClickListener onAddSmallPicClickListener = new GridSmallImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {

            PictureSelector.create(DeliveryDetailsActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(4)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(selectsmallList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .cropCompressQuality(90)//
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    // .recordVideoSecond(15)//录制视频秒数 默认60s
                    .forResult(PictureConfig.READ_EXTERNAL_STORAGE);//结果回调onActivityResult code
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case PictureConfig.READ_EXTERNAL_STORAGE:
                    // 图片选择结果回调
                    selectsmallList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    for (LocalMedia media : selectsmallList) {
                        Log.i(TAG, "压缩---->" + media.getCompressPath());
                        Log.i(TAG, "原图---->" + media.getPath());
                        Log.i(TAG, "裁剪---->" + media.getCutPath());
                        Log.i(TAG, "宽---->高-----》" + media.getWidth() + "---" + media.getHeight());
                    }
                    adapter2.setList(selectsmallList);
                    adapter2.notifyDataSetChanged();
                    break;
            }
        }
    }


    @OnClick(R.id.Confirmation)
    public void onclik(View view) {
        switch (view.getId()) {
            case R.id.Confirmation:
                if (selectsmallList.size() == 0) {
                    ToastUtil.show("请上传凭证!", 25);
                    return;
                }
                DialogUtils.getInstance().showSimpleDialog(context, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                    utils.setCancelable(false);
                    Button cofim = contentView.findViewById(R.id.submit);
                    Button exit = contentView.findViewById(R.id.cancel);
                    TextView content = contentView.findViewById(R.id.content);
                    content.setText("是否确认收货?");
                    exit.setOnClickListener(v2 -> {

                        utils.close();
                    });
                    cofim.setOnClickListener(v2 -> {
                        loadingDialog = new LoadingDialog(this, "请稍候…");
                        loadingDialog.show();
                        myHandler = new MyHandler<>(this);
                        myHandler.setOnHandlerListener(this);
                        myHandler.sendEmptyMessageDelayed(0, 1000);
                        utils.close();
                    });
                });


                break;
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        int what = msg.what;
        if (what == 0) {
            if (list.size() > 0) {
                list.clear();
            }
            String bucket = "freshcyber-1257941117"; // 上传的 bucket 名称，region 为之前设置的 ap-guangzhou

            for (int i = 0; i < selectsmallList.size(); i++) {
                upload(bucket, "product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + selectsmallList.get(i).getPath().substring(selectsmallList.get(i).getPath().lastIndexOf("/") + 1), selectsmallList.get(i).getPath());
                list.add("https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/" + SharedPreferencesUtils.getSp(Constants.UID, "") + "/" + getNowString() + "/" + selectsmallList.get(i).getPath().substring(selectsmallList.get(i).getPath().lastIndexOf("/") + 1));
            }

            add_order_voucher(getIntent().getExtras().getString("group_no"), Joiner.on(",").join(list), getIntent().getExtras().getString("sale_id"), String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")));

        }
    }

    public void add_order_voucher(String groupno, String voucher, String saler_id, String uid) {
        ApiInterfaceTwo.ApiFactory.createApi().add_order_voucher(groupno, voucher, saler_id, uid).enqueue(new Callback<OrderUpdateEntity>() {
            @Override
            public void onResponse(Call<OrderUpdateEntity> call, Response<OrderUpdateEntity> response) {
                if (response.body().getFlag() == 1) {
                    orderensure(getIntent().getExtras().getString("sale_id"), getIntent().getExtras().getString("group_no"));
                }else {
                    loadingDialog.dismiss();
                    ToastUtil.show(response.body().getMsg().toString(),200);
                }
            }

            @Override
            public void onFailure(Call<OrderUpdateEntity> call, Throwable t) {
                loadingDialog.dismiss();
            }
        });
    }

    public void orderensure(String sid, String group_no) {
        ApiInterfaceThree.ApiFactory.createApi().orderensure(sid, group_no).enqueue(new Callback<OrderEnsureEntity>() {
            @Override
            public void onResponse(Call<OrderEnsureEntity> call, Response<OrderEnsureEntity> response) {
                if (response.body().getFlag() == 1) {
                    loadingDialog.dismiss();
                    ToastUtil.show(response.body().getMsg(), 200);
                    NoticeObserver.getInstance().notifyObservers(Constants.RENSURE);
                    finish();
                }else {
                    loadingDialog.dismiss();
                    ToastUtil.show(response.body().getMsg(), 200);
                }
            }

            @Override
            public void onFailure(Call<OrderEnsureEntity> call, Throwable t) {
                loadingDialog.dismiss();
            }
        });
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
