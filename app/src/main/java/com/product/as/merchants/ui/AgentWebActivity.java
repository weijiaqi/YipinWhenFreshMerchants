package com.product.as.merchants.ui;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.tencent.cos.xml.common.Constants;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AgentWebActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.framcontent)
    FrameLayout framcontent;
    String TAG;
    WebSettings webSettings;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.close)
    ImageView close;
    WebView webView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_agent_web;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        TAG = AgentWebActivity.class.getSimpleName();

        ivBack.setOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        });
        close.setVisibility(View.VISIBLE);
        close.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("隐私条款");
        webView = new WebView(this);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl("https://www.fruits1688.com/page/policy.html");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                handler.proceed();// 接受所有网站的证书
            }

        });
        webView.setWebChromeClient(webChromeClient);
        framcontent.addView(webView);

    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键
                webView.goBack();   //后退
                return true;    //已处理
            } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            }
        }
        return false;

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
            //恢复pauseTimers状态
            webView.resumeTimers();
            webView.reload();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView.clearCache(true);
            this.deleteDatabase("webview.db");
            this.deleteDatabase("webviewCache.db");
            webView = null;
        }
        if (framcontent != null) {
            framcontent.removeAllViews();
        }
    }
}
