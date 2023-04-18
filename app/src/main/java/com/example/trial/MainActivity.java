
package com.example.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    static Context context;
    static ViewGroup l;
   // static EditText et;
    static int v;
    static File filesdir;
    static ImageButton forward;
    static ImageButton backward;
    static  ImageButton home;
    static PlayList playList;


    MyWebView webViews[]=new MyWebView[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);



        Intent i=getIntent();
        String demoi=i.getStringExtra("d");
        String sn=i.getStringExtra("s");



        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        context =getApplicationContext();
        l=findViewById(R.id.wv);
      //  et=(EditText)findViewById(R.id.t) ;
        forward=(ImageButton) findViewById(R.id.n);
        backward=(ImageButton)findViewById(R.id.b);
        home=(ImageButton)findViewById(R.id.h);
        filesdir=getFilesDir();





        playList=new PlayList(demoi,sn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playList.save();
                Intent intent=new Intent(getApplicationContext(),Home.class);
;              startActivity(intent);
               // onDestroy();
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       playList. save();

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
       playList.save();
        try{
            webViews[v].webView.destroy();}
        catch (Exception e){}

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        playList.save();
        try{
            webViews[v].webView.destroy();}
        catch (Exception e){}
    }

}

class MyWebView extends MainActivity
{

    WebView webView;
    ProgressBar progressBar;
    String url;
    boolean isLoaded;
    int index;
    RelativeLayout specificlayout;
   // EditText e;
    ViewGroup parentLayout;
    ImageButton btb;


    MyWebView(String myUrl,int i1,ImageButton btn)
    {

       // this.e=MainActivity.et;
        this.parentLayout=MainActivity.l;
        this.specificlayout=new RelativeLayout(MainActivity.context);
        this.specificlayout.setBackgroundColor(Color.BLACK);
        this.webView=new WebView(MainActivity.context);
        this.webView.setBackgroundColor(Color.BLACK);
        this.progressBar=new ProgressBar(MainActivity.context);
        this.url=myUrl;
        this.webView=setWebView(myUrl,this.webView);
        this.index=i1;
        this.isLoaded=false;
        this.btb=btn;
        allocate();
    }

    public WebView setWebView(String u,WebView webView)
    {

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {




                webView.evaluateJavascript("(function() {" +
                        "var v=document.getElementsByTagName(\"iframe\").length;" +
                        " return v; })();", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        if(Integer.parseInt(value)<1)
                        {
                            parentLayout.removeView(specificlayout);
                            specificlayout=null;



                            if(v==index)
                                btb.performClick();
                         //   e.setText(value + " " + webView.getOriginalUrl() + (webView==null)+"  ");
                        }
                    }
                });



                webView.evaluateJavascript("var e=document.getElementsByTagName(\"html\")[0];" +
                        "var e1 = document.getElementsByTagName(\"iframe\")[0];" +
                        " e.innerHTML=e1.outerHTML;" +
                        "", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {


                        webView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
                isLoaded=true;




            }
        });

        webView.loadUrl(url);
        progressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);



        return webView;
    }

    public void allocate()
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);

        specificlayout.addView(webView,params);
        specificlayout.addView(progressBar,params);
        float s=0.2f;
        progressBar.setScaleX(s);
        progressBar.setScaleY(s);

        parentLayout.addView(specificlayout);
        specificlayout.setVisibility(View.INVISIBLE);


    }
}

class PlayList extends MainActivity
{

    ImageButton forward;
    ImageButton backward;
    ImageButton home;
    String url[];
    Context context;
    ViewGroup layout;
  //  EditText editText;
    MyWebView webViews[];
    File filesdir;
    String sname;

    public  PlayList(String demo,String s)
    {
        // super();
        this.filesdir=MainActivity.filesdir;
        this.url=getLinks(demo);
        int i=Arrays.asList(url).indexOf(readHashMap("h.json").get(s));
       if(i!=-1)
            this.v=i;
        else
            this.v=0;


       // this.v=0;
        this.sname=s;
     //   this.editText=MainActivity.et;
        this.context=MainActivity.context;
        this.layout=l;
        this.forward=MainActivity.forward;
        this.backward=MainActivity.backward;
        this.home=MainActivity.home;
        webViews=new MyWebView[url.length];
        action();
    }

    void action()
    {

        webViews[v]=new MyWebView(url[v],v,forward);
        webViews[v].specificlayout.setVisibility(View.VISIBLE);
        try {
            webViews[v + 1] = new MyWebView(url[v + 1],(v+1),forward);
        }catch (Exception e){}
        try {
            webViews[v - 1] = new MyWebView(url[v - 1],(v-1),backward);
        }catch (Exception e){}

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v >= 0 && v <= (url.length-2))
                {
                    if(v!=-1 && webViews[v]!=null)
                    {
                        layout.removeView(webViews[v].specificlayout);
                        webViews[v]=null;
                        System.gc();

                    }

                    if (v < (url.length-1))
                        v++;



                     try
                     {   webViews[v].specificlayout.setVisibility(View.VISIBLE);
                     }

                    catch(Exception e)
                    {

                        webViews[v] = new MyWebView(url[v],v,forward);
                        webViews[v].specificlayout.setVisibility(View.VISIBLE);
                    }

                    try {

                        webViews[v + 1] = new MyWebView(url[v + 1],(v+1),forward);

                    } catch (Exception e) {
                    }
                }
            }
        });



        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v >= 1 && v <= (url.length-1)) {


                    if(v!=-1 && webViews[v]!=null)
                    {
                        layout.removeView(webViews[v].specificlayout);
                        webViews[v]=null;
                        System.gc();
                    }
                    if (v >= 1)
                        v--;

                    try
                    {
                        webViews[v].specificlayout.setVisibility(View.VISIBLE);
                    }
                    catch(Exception e)
                    {

                        webViews[v] = new MyWebView(url[v],v,backward);
                        webViews[v].specificlayout.setVisibility(View.VISIBLE);
                    }

                    try {

                        webViews[v-1] = new MyWebView(url[v-1],(v-1),backward);

                    } catch (Exception e) {
                    }

                }
            }
        });





    }

    public void save()
    {
        HashMap<String,String> hashMap=new HashMap<String, String>();
        hashMap=readHashMap("h.json");
        hashMap.put(sname,url[v]);
        writeHashMap(hashMap,"h.json");
//        editText.setText(readHashMap("h.json").toString());
    }

    public void writeHashMap(HashMap hashMap,String fileName)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filesdir, fileName), hashMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap readHashMap(String fileName)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> h=new HashMap<String,Object>();
        try {
            h = objectMapper.readValue(new File(filesdir, fileName), new TypeReference<HashMap<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return h;
    }

    public String[] getLinks(String example)
    {


        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(example);
        int index=-1,findex=-1;
        if(matcher.find())
        {
            index=matcher.start();
            // System.out.println(example.charAt(index+2)+"  "+example.charAt(index+5)+" "+example.charAt(index+10));
            if(example.charAt(index+2)=='-'
                    && example.charAt(index+5)=='-' && example.charAt(index+10)=='-')
            {
                findex=index;
            }
        }

        String bf =example.substring(0,findex);
        String af =example.substring(findex+10);
        LocalDate endDate=LocalDate.now();
        LocalDate startDate=endDate.minusDays(15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Object> datesInRange = IntStream.rangeClosed(0, (int) ChronoUnit.DAYS.between(startDate, endDate))
                .mapToObj(i -> bf+formatter.
                        format(startDate.plusDays(i))+af)

                .collect(Collectors.toList());
        String[] stringArray = datesInRange.stream()
                .map(Object::toString)
                .toArray(String[]::new);
        return stringArray;
    }
}

