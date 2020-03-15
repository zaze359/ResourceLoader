package com.zaze.resource.loader

import android.app.Application
import android.content.Context

/**
 * Description :
 *
 * @author : ZAZE
 * @version : 2020-03-15 - 17:55
 */
class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SystemCompat.attach(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}