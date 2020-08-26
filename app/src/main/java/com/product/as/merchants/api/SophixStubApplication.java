//package com.product.as.merchants.api;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.taobao.sophix.PatchStatus;
//import com.taobao.sophix.SophixApplication;
//import com.taobao.sophix.SophixEntry;
//import com.taobao.sophix.SophixManager;
//import com.taobao.sophix.listener.PatchLoadStatusListener;
//
//
//public class SophixStubApplication extends SophixApplication {
//    private final String TAG = "SophixStubApplication";
//
//
//    @SophixEntry(App.class)
//    static class RealApplicationStub {
//    }
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
////         如果需要使用MultiDex，需要在此处调用。
////         MultiDex.install(this);
//        initSophix();
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
//        /** 补丁在后台发布之后, 并不会主动下行推送到客户端, 客户端通过调用queryAndLoadNewPatch方法查询后台补丁是否可用*/
//        SophixManager.getInstance().queryAndLoadNewPatch();
//    }
//
//
//    private void initSophix() {
//        String appVersion = "0.0.0";
//        try {
//            appVersion = this.getPackageManager()
//                    .getPackageInfo(this.getPackageName(), 0)
//                    .versionName;
//        } catch (Exception e) {
//        }
//        final SophixManager instance = SophixManager.getInstance();
//        instance.setContext(this)
//                .setAppVersion(appVersion)
//                .setSecretMetaData("27613010-1", "c85cb7c63b878a6eb50cae204653cddb", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCwwFrSfOHWJna7D6XLRbg6lWLNyvcSIoS2fINS1qw5FjcVgd/m0Rh5NKKgfi4Yk7dIRhogDpHBKHzrB5SYYseGM6o8S703qziCU1NiohJodgJY94/WZuqjjSoPRHY8DhUZnE8M4G4+NZraE/42Qwtm2QkJW6Nvg+uH4uE+MJ0h2y1TUgGJGljy7rx3Nfuky6B5JETW9AUflE/7AsaS9dRcKi+VblBnrg7Ri+/NAieCmkmFvyD3AemAnSlTCsbC0rsN3TMkCvQIxt/QSy+IVNkrjGTvoVPdu8AlMhuxUfhrQqJ/ReQCkUs+Sf6858AY0Uv8NGPtTz5Me09BAWk2bxxzAgMBAAECggEBAJqunE0dMV8CpmGqngO47jOGzMMg3vyl7NLvQNZcPLhw8qAF2vbbAroAEFq4Uor7qyi7AN4RIgmS70+YgLijqc2OpWrDNcLkOz4CwjTNdaKptAA8F0shIjMjtS1LUtXX1Stmmd/BAXm729sCKDVAk1fclIX2Bp/vKU7whQKx9LHM9BkcMQG9DlNT+YxK72nJkj2jUrxpFLUn0V651hFWD/fpI0joTE/3gc5iPd3tX1VLAIpnJhCo61FDydF+FiiPGk/YY5fr4cpERBKRIl1S7ZKevEZww+RvF14TQQaeGcQ8mUAv5dbuVgBBNqm7Z2b8bgl8K2DpzwB/Kbbr5q41jrECgYEA1augNm1jLgpNM5uo33SbeIqmW3A/k9N8dH/wN9Vve473TtWo06H9l0B4XYaEXoVqMOKIMSUbDCUEj7Kj56blmOljgzHJ+IGokJ01pZdh7Wjt4Q8VJs/XHakLh7RvWElP9gdfYAM7JWQC+i3m29srEKVDne8iqKYWwMur8wBDxdUCgYEA08Rd5ZPBvKZB3mm2Nh40Z7Ww8h/DP1vERXgBUOOEgIkrgf9g1rqDdUOW+dY/GA/BK357N6pXgX98l1p30QmH8RfeBUBgYVAeajxaml1oB8sG7jFQhjGvoIHDVN/ZfdDPBYC98337uNMKhjXZXdoPcOburj3/PFe+vHoKQyqblScCgYEAkbnIvfYi026xMoZySVyFAFRHm0tWASMW2Xm8QsgtjW53Guw7hYMQqkyNCvRIaRLC1u5ZBnORG9pPXxXuFXrNUlmyxfJ6FiOP7Hlb8kDpx/ptarZXra3Iv/Df7UDRQ7xUba6fjKMHfrvUD/6oWcADggnEsP08fRsxn3IJJuVCU7UCgYB1dM8XzRKCDj0unbeg4LP0agRQ52kmNUHxvhQMx4TwviRRoFIYB69uVSTiQgEQ2XwtIaQ6RdLvUsTKNjWKzhyGjtRoWUKb9xJHnl4qjHBtEiEv8E5XgCuHfTHYW6iIbk7FIJk6+r+pzfRiaG+97HrwBkaQgnaVA54ZcB9NDc7ptQKBgG8re5Ilst3et3YYIdCmrPa7PMZygsLRY5o7TTzIjhvVkPwWBvmfVdOc9DI8K/2CNdmpdQtbpTLTHH97Gyd1VKWCZ08fHtVkRjLMv1WBXFGFkcL5rDinppuaoraOQm3AGRX2vgfjP8QXnume1vb53QSYfw2UjJg5D4cNmnlTbjkF")
//                .setEnableDebug(true)
//                .setEnableFullLog()
//                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
//                    @Override
//                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
//                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
//                            Log.e(TAG, "表明补丁加载成功");
//                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
//                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
//                            Log.e(TAG, "表明新补丁生效需要重启. 开发者可提示用户或者强制重启");
//                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
//                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
//                            SophixManager.getInstance().cleanPatches();
//                            Log.e(TAG, "内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载");
//                        } else {
//                            // 其它错误信息, 查看PatchStatus类说明
//                            Log.e(TAG, code + "");
//                            Log.e(TAG, "其它错误信息, 查看PatchStatus类说明");
//                        }
//                    }
//                }).initialize();
//    }
//}
//
