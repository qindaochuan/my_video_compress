package com.qianren3.my_video_compress;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class MyVideoCompressDelegate implements PluginRegistry.ActivityResultListener,PluginRegistry.RequestPermissionsResultListener{
    private final Activity activity;
    private Handler handler = null;
    public static MethodChannel.Result result = null;

    private static final int HANDLER_VIDEO_COMPRESS = 1;

    public MyVideoCompressDelegate(Activity activity) {
        this.activity = activity;
        this.handler = new MyHandler(activity,this);
    }

    public void videoCompress(MethodCall call, MethodChannel.Result result){
        System.out.println("Android call pickFile()");
        this.result = result;
        Message msg = new Message();
        msg.what = HANDLER_VIDEO_COMPRESS;
        handler.sendMessage(msg);
    }

    void saveStateBeforeResult() {

    }

    public static class MyHandler extends Handler{
        WeakReference<Activity> mActivity;
        WeakReference<MyVideoCompressDelegate> mInstance;

        MyHandler(Activity activity,MyVideoCompressDelegate intance){
            mActivity = new WeakReference<>(activity);
            mInstance = new WeakReference<>(intance);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyVideoCompressDelegate theInstance = mInstance.get();
            switch (msg.what){
                case HANDLER_VIDEO_COMPRESS:
                    theInstance.doVideoCompress();
                    break;
            }
        }
    }

    public void doVideoCompress(){
        System.out.println("doVideoCompress()");
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                Looper.prepare();

                Looper.loop();
            }
        }.start();
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        return false;
    }
}
