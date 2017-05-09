package com.cgwx.yyfwptz.lixiang.testionic2callnative2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements CordovaInterface{
    protected CordovaPlugin activityResultCallback;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    protected int activityResultRequestCode;
    protected SystemWebView systemWebView;
    CordovaWebView cordovaWebView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                systemWebView.loadUrl("javascript:callJs(\"TT\")");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        systemWebView = (SystemWebView) findViewById(R.id.cordovaWebView);
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(this);
        cordovaWebView = new CordovaWebViewImpl(new SystemWebViewEngine(systemWebView));
        cordovaWebView.init(this, parser.getPluginEntries(), parser.getPreferences());
        systemWebView.loadUrl("file:///android_asset/www/index.html");
    }

    @Override
    public void startActivityForResult(CordovaPlugin cordovaPlugin, Intent intent, int i) {
        setActivityResultCallback(cordovaPlugin);
        try {
            startActivityForResult(intent, i);
        } catch (RuntimeException e) {
            activityResultCallback = null;
            throw e;
        }
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin cordovaPlugin) {
        if (activityResultCallback != null) {
            activityResultCallback.onActivityResult(activityResultRequestCode, Activity.RESULT_CANCELED, null);
        }
        this.activityResultCallback = cordovaPlugin;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Object onMessage(String s, Object o) {
        if ("exit".equals(s)) {
            super.finish();
        }
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    @Override
    public void requestPermission(CordovaPlugin cordovaPlugin, int i, String s) {

    }

    @Override
    public void requestPermissions(CordovaPlugin cordovaPlugin, int i, String[] strings) {

    }

    @Override
    public boolean hasPermission(String s) {
        return false;
    }
}

