package net.openid.appauthdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import net.openid.appauth.RedirectUriReceiverActivity;

public class WebViewBrower extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);//让浏览器支持javascript
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Uri uri = request.getUrl();
                    if (null != uri && uri.getScheme().equals("tuhuscm")
                        && uri.getPath().equals("/login/result")
                        && uri.getHost().equals("com.tuhu.scm")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setClass(WebViewBrower.this, RedirectUriReceiverActivity.class);
                        startActivity(intent);
                    } else {
                        if (null != uri)
                            webView.loadUrl(uri.toString());
                    }
                }
                return true;
            }
        });
        Intent data = getIntent();
        Uri wd = data.getData();
        if (wd != null)
            webView.loadUrl(wd.toString());
    }
}
