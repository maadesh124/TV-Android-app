package com.example.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class News extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        // Hide the navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        ImageButton home=(ImageButton)findViewById(R.id.h);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Home.class);
                startActivity(intent);

                finish();
            }
        });



        ProgressBar progressBar=(ProgressBar)findViewById(R.id.prog);
        progressBar.setScaleX(0.2f);
        progressBar.setScaleY(0.2f);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();

        FrameLayout r=(FrameLayout) findViewById(R.id.rl) ;
        WebView webView=(WebView)findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
       r.setBackgroundColor(Color.BLACK);
        webView.setWebViewClient(new WebViewClient() {

                              @Override
                                     public void onPageFinished(WebView view, String url) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        webView.setVisibility(View.VISIBLE);

                                     }
                                 });


                Intent intent=getIntent();

        String videoId = intent.getStringExtra("id");
        String url = "https://www.youtube.com/embed/" + videoId + "?autoplay=1&rel=0&modestbranding=1&showinfo=0&controls=1&fs=0";
        webView.loadUrl(url);
        webView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

    }
}
