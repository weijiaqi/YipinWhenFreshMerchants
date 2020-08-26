package com.product.as.merchants.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.meikoz.core.base.BaseFragment;
import com.meikoz.core.util.TimeUtils;
import com.printer.command.EscCommand;
import com.printer.command.LabelCommand;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.OrderManagerAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.bluetooth.BluetoothListActivity;
import com.product.as.merchants.bluetooth.DeviceConnFactoryManager;
import com.product.as.merchants.bluetooth.PrinterCommand;
import com.product.as.merchants.bluetooth.ThreadPool;

import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.model.Constants;

import com.product.as.merchants.pager.BasePager;

import com.product.as.merchants.pager.PurchaselistPager;
import com.product.as.merchants.pager.ShippedPager;
import com.product.as.merchants.pager.OrderHasEndPager;


import com.product.as.merchants.pager.ShipperEndPager;

import com.product.as.merchants.ui.AccountAssetsActivity;
import com.product.as.merchants.ui.BindingAlipayActivity;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;

import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.UiUtils;
import com.product.as.merchants.view.NoScrollViewPager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;
import static com.product.as.merchants.bluetooth.DeviceConnFactoryManager.ACTION_QUERY_PRINTER_STATE;
import static com.product.as.merchants.bluetooth.DeviceConnFactoryManager.CONN_STATE_FAILED;


// 订单管理
public class OrderManagementFragment extends BaseFragment implements ViewPager.OnPageChangeListener, NoticeObserver.Observer {
    @Bind(R.id.sequencing_tab)
    TabLayout mTabLayout;
    @Bind(R.id.meal_viewpager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    private List<BasePager> mBasePagers;
    private Class[] aClass;

    /**
     * 蓝牙所需权限
     */
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH
    };

    /**
     * 未授予的权限
     */
    private ArrayList<String> per = new ArrayList<>();

    /**
     * 蓝牙请求码
     */
    public static final int BLUETOOTH_REQUEST_CODE = 0x002;

    private ThreadPool threadPool;//线程

    /**
     * 判断打印机所使用指令是否是ESC指令
     */
    private int id = 0;

    /**
     * 打印机是否连接
     */
    private static final int CONN_PRINTER = 0x003;
    /**
     * 使用打印机指令错误
     */
    private static final int PRINTER_COMMAND_ERROR = 0x004;

    /**
     * 连接状态断开
     */
    private static final int CONN_STATE_DISCONN = 0x005;
    /**
     * 权限请求码
     */
    private static final int REQUEST_CODE = 0x001;
    private List<OrderProcureEntity.DataBean> listBeanList;

    OrderProcureEntity.DataBean dataBean;

    @Override
    protected int getLayoutResource() {
        return R.layout.order_management_fragment;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) throws IOException {
        NoticeObserver.getInstance().addObserver(this);
        checkPermission();
        requestPermission();
        ivBack.setVisibility(View.GONE);
        tvTitles.setText("订单管理");
//        ivRight.setBackgroundResource(R.mipmap.sweepcode);
        mTabLayout.setSelectedTabIndicatorHeight(0);
        aClass = new Class[]{ShippedPager.class, ShipperEndPager.class, OrderHasEndPager.class, PurchaselistPager.class};

        mBasePagers = new ArrayList<>();
        for (Class aClass1 : aClass) {
            BasePager basePager = createPager(aClass1);
            mBasePagers.add(basePager);
        }

        //----------------------------------------设置标题------------------------------------------
        String[] strings = UiUtils.getStringArray(R.array.order_tables);
        for (String string : strings) {
            mTabLayout.addTab(mTabLayout.newTab().setText(string));
        }

        OrderManagerAdapter mAdapter = new OrderManagerAdapter(mBasePagers);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        mViewPager.addOnPageChangeListener(this);


    }


    //这里用反射的原因就是方便创建对象
    private <T extends BasePager> T createPager(Class<T> pager) {
        Constructor<T> constructor;
        T page = null;
        try {
            constructor = pager.getConstructor(Context.class);
            page = constructor.newInstance(getActivity());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (BasePager mBasePager : mBasePagers) {
            mBasePager.onDestroy();
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DeviceConnFactoryManager.closeAllPort();
        if (threadPool != null) {
            threadPool.stopThreadPool();
            threadPool = null;
        }
        NoticeObserver.getInstance().removeObserver(this);
    }

    @Override
    public <T> void update(int what, T t) {
        if (what == Constants.Chuandi) {

            if (SharedPreferencesUtils.getInt(Constants.UPLOADPICTURE, 0) == 0) {
                startActivityForResult(new Intent(mContext, BluetoothListActivity.class), BLUETOOTH_REQUEST_CODE);
            } else {
                DialogUtils.getInstance().showSimpleDialog(mContext, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                    utils.setCancelable(false);
                    Button cofim = contentView.findViewById(R.id.submit);
                    Button exit = contentView.findViewById(R.id.cancel);
                    TextView content = contentView.findViewById(R.id.content);
                    content.setText("确定进行打印?");
                    exit.setOnClickListener(v2 -> {
                        utils.close();
                    });

                    cofim.setOnClickListener(v2 -> {
                        listBeanList = (List<OrderProcureEntity.DataBean>) t;
                        if (listBeanList.size() > 0 && listBeanList.size() != 1) {
                            new Thread(() -> printLabel3()).start();

                        } else {
                            if (listBeanList.size() == 1) {
                                new Thread(() -> printLabel()).start();
                            } else {
                                ToastUtil.show("未查找到水果订单!", 200);
                            }
                        }
                        utils.close();
                    });
                });
            }
        }  else if (what == Constants.Chuandi3) {

            if (SharedPreferencesUtils.getInt(Constants.UPLOADPICTURE, 0) == 0) {
                startActivityForResult(new Intent(mContext, BluetoothListActivity.class), BLUETOOTH_REQUEST_CODE);
            } else {

                DialogUtils.getInstance().showSimpleDialog(mContext, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                    utils.setCancelable(false);
                    Button cofim = contentView.findViewById(R.id.submit);
                    Button exit = contentView.findViewById(R.id.cancel);
                    TextView content = contentView.findViewById(R.id.content);
                    content.setText("确定进行打印?");
                    exit.setOnClickListener(v2 -> {
                        utils.close();
                    });
                    cofim.setOnClickListener(v2 -> {
                        listBeanList = (List<OrderProcureEntity.DataBean>) t;

                        if (listBeanList.size() > 0 && listBeanList.size() != 1) {
                            new Thread(() -> printLabel3()).start();
                        } else {
                            if (listBeanList.size() == 1) {
                                new Thread(() -> printLabel()).start();
                            } else {
                                ToastUtil.show("未查找到蔬菜订单!", 200);
                            }
                        }
                        utils.close();
                    });
                });
            }
        } else if (what == Constants.Chuandi4) {

            if (SharedPreferencesUtils.getInt(Constants.UPLOADPICTURE, 0) == 0) {
                startActivityForResult(new Intent(mContext, BluetoothListActivity.class), BLUETOOTH_REQUEST_CODE);
            } else {

                DialogUtils.getInstance().showSimpleDialog(mContext, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                    utils.setCancelable(false);
                    Button cofim = contentView.findViewById(R.id.submit);
                    Button exit = contentView.findViewById(R.id.cancel);
                    TextView content = contentView.findViewById(R.id.content);
                    content.setText("确定进行打印?");
                    exit.setOnClickListener(v2 -> {
                        utils.close();
                    });

                    cofim.setOnClickListener(v2 -> {
                        dataBean = (OrderProcureEntity.DataBean) t;
                        new Thread(() -> printLabel2()).start();
                        utils.close();
                    });
                });


            }
        }
    }


    private void checkPermission() {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mContext, permission)) {
                per.add(permission);
            }
        }
    }

    private void requestPermission() {
        if (per.size() > 0) {
            String[] p = new String[per.size()];
            ActivityCompat.requestPermissions(getActivity(), per.toArray(p), REQUEST_CODE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //蓝牙连接
            if (requestCode == BLUETOOTH_REQUEST_CODE) {
                closePort();
                //获取蓝牙mac地址
                String macAddress = data.getStringExtra(BluetoothListActivity.EXTRA_DEVICE_ADDRESS);
                //初始化DeviceConnFactoryManager 并设置信息
                new DeviceConnFactoryManager.Build()
                        //设置标识符
                        .setId(id)
                        //设置连接方式
                        .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                        //设置连接的蓝牙mac地址
                        .setMacAddress(macAddress)
                        .build();
                //配置完信息，就可以打开端口连接了
                Log.i("TAG", "onActivityResult: 连接蓝牙" + id);
                threadPool = ThreadPool.getInstantiation();
                threadPool.addTask(new Runnable() {
                    @Override
                    public void run() {
                        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].openPort();
                    }
                });
            }
        }
    }

    /**
     * 重新连接回收上次连接的对象，避免内存泄漏
     */
    private void closePort() {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] != null && DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].mPort != null) {
           try {
               DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].reader.cancel();
               DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].mPort.closePort();
               DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].mPort = null;
           }catch (Exception e){

           }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
         * 注册接收连接状态的广播
         */
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_QUERY_PRINTER_STATE);
        filter.addAction(DeviceConnFactoryManager.ACTION_CONN_STATE);
        mContext.registerReceiver(receiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mContext.unregisterReceiver(receiver);
    }

    /**
     * 连接状态的广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DeviceConnFactoryManager.ACTION_CONN_STATE.equals(action)) {
                int state = intent.getIntExtra(DeviceConnFactoryManager.STATE, -1);
                int deviceId = intent.getIntExtra(DeviceConnFactoryManager.DEVICE_ID, -1);
                switch (state) {
                    case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
                        if (id == deviceId)
                            Toast.makeText(mContext, "未连接", Toast.LENGTH_SHORT).show();
                        break;
                    case DeviceConnFactoryManager.CONN_STATE_CONNECTING:
                        break;
                    case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
                        Toast.makeText(mContext, "已连接", Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtils.putInt(Constants.UPLOADPICTURE, 1);
                        break;
                    case CONN_STATE_FAILED:
                        Toast.makeText(mContext, "连接失败！重试或重启打印机试试", Toast.LENGTH_SHORT).show();
                        break;
                }
                /* Usb连接断开、蓝牙连接断开广播 */
            } else if (ACTION_USB_DEVICE_DETACHED.equals(action)) {
                mHandler.obtainMessage(CONN_STATE_DISCONN).sendToTarget();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONN_STATE_DISCONN:
                    if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] != null || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
                        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].closePort(id);
                        Toast.makeText(mContext, "成功断开连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PRINTER_COMMAND_ERROR:

                    break;
                case CONN_PRINTER:
                    startActivityForResult(new Intent(mContext, BluetoothListActivity.class), BLUETOOTH_REQUEST_CODE);
                    Toast.makeText(mContext, "请先连接打印机", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void printLabel3() {
        Log.i("TAG", "准备打印");
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(() -> {
            //先判断打印机是否连接
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                    !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
                mHandler.obtainMessage(CONN_PRINTER).sendToTarget();
                return;
            }
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.TSC) {
                Log.i("TAG", "开始打印");
                for (int i = 0; i < listBeanList.size(); i++) {
                    for (int j = 0; j < listBeanList.get(i).getCount(); j++) {
                        LabelCommand tsc = new LabelCommand();
                        tsc.addSize(80, 50); // 设置标签尺寸，按照实际尺寸设置
                        tsc.addGap(1); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
                        tsc.addDirection(LabelCommand.DIRECTION.FORWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
                        tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);//开启带Response的打印，用于连续打印
                        tsc.addReference(0, 0);// 设置原点坐标
                        tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
                        tsc.addCls();// 清除打印缓冲区

                        // 绘制简体中文
                        tsc.addText(50, 45, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "品名: " + listBeanList.get(i).getProduct_name());
                        tsc.addText(50, 85, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "规格: " + listBeanList.get(i).getUnit());
                        tsc.addText(50, 125, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "售价: " + listBeanList.get(i).getPro_pic() + "");

                        tsc.addText(50, 165, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "产地：" + listBeanList.get(i).getOrigin());
                        tsc.addText(50, 205, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "供应商：" + "北京宏丰永正商贸有限公司");
                        tsc.addText(50, 245, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "时间：" + TimeUtils.millisDateString(System.currentTimeMillis()));
                        tsc.addText(140, 305, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "聚鲜台客服：" + "400-8271-368");
                        tsc.addText(140, 345, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                                "果蔬生鲜一站式采购团购平台");
                        // 绘制图片
                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.greendao);
                        tsc.addBitmap(50, 290, LabelCommand.BITMAP_MODE.OVERWRITE, 80, b);

                        tsc.addPrint(1, 1); // 打印标签
                        tsc.addSound(2, 100); // 打印标签后 蜂鸣器响

                        /* 发送数据 */
                        Vector<Byte> data = tsc.getCommand();
                        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null) {
                            ToastUtil.show("sendLabel: 打印机为空", 200);
                            return;
                        }
                        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(data);
                    }
                }


            } else {
                mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget();
            }
        });
    }


    public void printLabel() {
        Log.i("TAG", "准备打印");
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(() -> {
            //先判断打印机是否连接
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                    !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
                mHandler.obtainMessage(CONN_PRINTER).sendToTarget();
                return;
            }
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.TSC) {
                Log.i("TAG", "开始打印");
                sendLabel();
            } else {
                mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget();
            }
        });
    }


    private void sendLabel() {
        LabelCommand tsc = new LabelCommand();
        tsc.addSize(80, 50); // 设置标签尺寸，按照实际尺寸设置
        tsc.addGap(1); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
        tsc.addDirection(LabelCommand.DIRECTION.FORWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
        tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);//开启带Response的打印，用于连续打印
        tsc.addReference(0, 0);// 设置原点坐标
        tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
        tsc.addCls();// 清除打印缓冲区
        for (int i = 0; i < listBeanList.get(0).getCount(); i++) {
            // 绘制简体中文
            tsc.addText(50, 45, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "品名: " + listBeanList.get(0).getProduct_name());
            tsc.addText(50, 85, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "规格: " + listBeanList.get(0).getUnit());
            tsc.addText(50, 125, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "售价: " + listBeanList.get(0).getPro_pic() + "");

            tsc.addText(50, 165, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "产地：" + listBeanList.get(0).getOrigin());
            tsc.addText(50, 205, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "供应商：" + "北京宏丰永正商贸有限公司");
            tsc.addText(50, 245, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "时间：" + TimeUtils.millisDateString(System.currentTimeMillis()));
            tsc.addText(140, 305, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "聚鲜台客服：" + "400-8271-368");
            tsc.addText(140, 345, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "果蔬生鲜一站式采购团购平台");
            // 绘制图片
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.greendao);
            tsc.addBitmap(50, 290, LabelCommand.BITMAP_MODE.OVERWRITE, 80, b);

            tsc.addPrint(1, 1); // 打印标签
            tsc.addSound(2, 100); // 打印标签后 蜂鸣器响

            /* 发送数据 */
            Vector<Byte> data = tsc.getCommand();
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null) {
                ToastUtil.show("sendLabel: 打印机为空", 200);
                return;
            }
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(data);
        }
    }


    public void printLabel2() {
        Log.i("TAG", "准备打印");
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(() -> {
            //先判断打印机是否连接
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                    !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
                mHandler.obtainMessage(CONN_PRINTER).sendToTarget();
                return;
            }
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.TSC) {
                Log.i("TAG", "开始打印");
                sendLabel1();
            } else {
                mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget();
            }
        });
    }


    private void sendLabel1() {
        LabelCommand tsc = new LabelCommand();
        tsc.addSize(80, 50); // 设置标签尺寸，按照实际尺寸设置
        tsc.addGap(1); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
        tsc.addDirection(LabelCommand.DIRECTION.FORWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
        tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);//开启带Response的打印，用于连续打印
        tsc.addReference(0, 0);// 设置原点坐标
        tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
        tsc.addCls();// 清除打印缓冲区

        for (int i = 0; i < dataBean.getCount(); i++) {
            // 绘制简体中文
            tsc.addText(50, 45, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "品名: " + dataBean.getProduct_name());
            tsc.addText(50, 85, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "规格: " + dataBean.getUnit());
            tsc.addText(50, 125, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "售价: " + dataBean.getPro_pic() + "");

            tsc.addText(50, 165, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "产地：" + dataBean.getOrigin());
            tsc.addText(50, 205, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "供应商：" + "北京宏丰永正商贸有限公司");
            tsc.addText(50, 245, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "时间：" + TimeUtils.millisDateString(System.currentTimeMillis()));
            tsc.addText(140, 305, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "聚鲜台客服：" + "400-8271-368");
            tsc.addText(140, 345, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "果蔬生鲜一站式采购团购平台");
            // 绘制图片
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.greendao);
            tsc.addBitmap(50, 290, LabelCommand.BITMAP_MODE.OVERWRITE, 80, b);

            tsc.addPrint(1, 1); // 打印标签
            tsc.addSound(2, 100); // 打印标签后 蜂鸣器响

            /* 发送数据 */
            Vector<Byte> data = tsc.getCommand();
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null) {
                ToastUtil.show("sendLabel: 打印机为空", 200);
                return;
            }
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(data);
        }
    }
}
