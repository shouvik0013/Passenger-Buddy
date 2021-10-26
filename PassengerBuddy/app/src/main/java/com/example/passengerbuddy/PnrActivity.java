package com.example.passengerbuddy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PnrActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E3EB9")));
        webView = (WebView) findViewById(R.id.webViewActivityPnr);
        webView.loadUrl(ServerLinks.pnrLink);
    }


}
