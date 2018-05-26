package com.aahl.hl_letter.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;

/**
 * @author : Mr.Hao
 * @project : HL_Letter
 * @date :  2018/4/27
 * @description :
 */

public class BaseApplication extends Application {

    private static final String APATCH_PATH = "/out.apatch"; // 下载下来的apatch的路径
    private static final String DIR = "apatch";//补丁文件夹
    private static final String TAG = "Application";
    private static PatchManager mPatchManager;
    private static BaseApplication sInstance;
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //1）初始化PatchManager
        mPatchManager = new PatchManager(this);
        mPatchManager.init("1.0");

        mPatchManager.loadPatch();
        // add patch at runtime
        try {
            // .apatch file path
            String patchFileString = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + APATCH_PATH;
            mPatchManager.addPatch(patchFileString);
            Log.d(TAG, "apatch:" + patchFileString + " added.");

            //复制且加载补丁成功后，删除下载的补丁
            File f = new File(this.getFilesDir(), DIR + APATCH_PATH);
            if (f.exists()) {
                boolean result = new File(patchFileString).delete();
                if (!result)
                    Log.e(TAG, patchFileString + " delete fail");
            }
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }

    }
    /**
     * 获取instance
     */
    public static BaseApplication getInstance() {
        return sInstance;
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static PatchManager getPatchManager() {
        return mPatchManager;
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }


}
