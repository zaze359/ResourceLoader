package com.zaze.resource.loader

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.zaze.nativelib.TestJni
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Example of a call to a native method
    }

    override fun onResume() {
        super.onResume()
        if (PermissionUtil.checkAndRequestUserPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                11
            )
        ) {
            load()
        }
    }

    private fun load() {
        sample_text.text = TestJni().stringFromJNI()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            load()
        } else {
            //
        }
    }
}
