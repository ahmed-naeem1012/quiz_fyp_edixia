package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class science_view_holder extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_view_holder);




        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.learnetic.com/icons_k12/?utm_term=interactive%20teaching&utm_campaign=Wyszukiwarka/ICONS/2022&utm_source=adwords&utm_medium=ppc&hsa_acc=6825301605&hsa_cam=16581851958&hsa_grp=134578918797&hsa_ad=588129821708&hsa_src=g&hsa_tgt=kwd-297801745193&hsa_kw=interactive%20teaching&hsa_mt=b&hsa_net=adwords&hsa_ver=3&gclid=EAIaIQobChMI2e7nnZOC-QIVpI9oCR1t1ABpEAAYASAAEgJtNvD_BwE");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}