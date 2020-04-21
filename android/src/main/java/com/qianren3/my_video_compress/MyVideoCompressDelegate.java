package com.qianren3.my_video_compress;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.vincent.videocompressor.VideoCompress;

import java.lang.ref.WeakReference;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class MyVideoCompressDelegate implements PluginRegistry.ActivityResultListener,PluginRegistry.RequestPermissionsResultListener{
    private final Activity activity;
    private Handler handler = null;
    public static MethodChannel.Result result = null;

    private static final int HANDLER_VIDEO_COMPRESS = 1;

    private String srcPath = null;

    public MyVideoCompressDelegate(Activity activity) {
        this.activity = activity;
        this.handler = new MyHandler(activity,this);
    }

    public void videoCompress(MethodCall call, MethodChannel.Result result){
        System.out.println("Android call videoCompress(...)");
        srcPath = call.argument("srcPath");
        System.out.println("srcPath = " + srcPath);
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
        int dot = srcPath.lastIndexOf('.');
        final String destPath = srcPath.substring(0,dot) + "_compress" + srcPath.substring(dot);
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                VideoCompress.compressVideoLow(srcPath, destPath, new VideoCompress.CompressListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess() {
                        Handler thisHandler = new Handler(Looper.getMainLooper());
                        thisHandler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                result.success(destPath);
                            }
                        });
                    }

                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onProgress(float percent) {

                    }
                });
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
