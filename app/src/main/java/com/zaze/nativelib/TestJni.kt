package com.zaze.nativelib

import com.zaze.resource.loader.SystemCompat


class TestJni {
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            SystemCompat.loadLibrary("native-lib")
        }
    }
}
