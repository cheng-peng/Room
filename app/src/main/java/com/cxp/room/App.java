package com.cxp.room;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * 文 件 名: App
 * 创 建 人: CXP
 * 创建日期: 2018-03-26 21:18
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //谷歌调试工具
        Stetho.initializeWithDefaults(this);
    }
}
