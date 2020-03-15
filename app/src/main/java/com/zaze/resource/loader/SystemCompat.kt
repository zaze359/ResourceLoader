package com.zaze.resource.loader

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File

/**
 * Description :
 *
 * @author : ZAZE
 * @version : 2020-03-15 - 18:01
 */
object SystemCompat {
    const val TAG = "SystemCompat"
    private lateinit var application: Application
    fun attach(application: Application) {
        this.application = application
    }

    fun loadLibrary(soLib: String) {
        try {
            System.loadLibrary(soLib)
        } catch (e: Throwable) {
            Log.w(TAG, "System.loadLibrary error", e)
            loadSoInApk(soLib)
        }
    }

    /**
     * 加载APK中的SO
     */
    private fun loadSoInApk(soLib: String) {
        getCpuABI().forEach {
            try {
                val soInData = File("${application.filesDir}/$soLib.so")
                if(!soInData.exists()) {
                    Log.i(TAG, "data下不存在对应so, 尝试从sdcard拷贝")
                    // TODO 此处先跳过 将自身apk从data下拷贝到sdcard 并解压，获取所有so，直接模拟
                    File("${Environment.getExternalStorageDirectory()}/zaze/assets/$it/lib$soLib.so").let {soInSd ->
                        if(soInSd.exists()) {
                            Log.i(TAG, "sdcard下存在对应so, 开始拷贝到data下: ${soInData.absolutePath}")
                            soInSd.copyTo(soInData, true)
                        }
                    }
                }
                if (soInData.exists()) {
                    Log.i(TAG, "loadSoInApk")
                    System.load(soInData.absolutePath)
                    return@forEach
                }
            } catch (e: Throwable) {
                Log.w(TAG, "loadSoInApk error", e)
            }
        }
    }

    private fun getCpuABI(): Set<String> {
        return HashSet<String>().apply {
            add(Build.CPU_ABI)
            add(Build.CPU_ABI2)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addAll(Build.SUPPORTED_ABIS)
            }
        }
    }
}