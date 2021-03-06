package com.imake.runtimepermissionwrapper

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.imake.runtimepermissionwrapper.util.PermissionPojo
import com.imake.runtimepermissionwrapper.util.RunPermissionCallbackResult
import com.imake.runtimepermissionwrapper.util.RunTimePermissionWrapper
import kotlinx.android.synthetic.main.activity_main.*

class RuntimePermissionWithWrapperActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback,RunPermissionCallbackResult{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runTimePermissionBtn.setOnClickListener(View.OnClickListener {
            // Permission is missing and must be requested.
            val permissionHashMap = HashMap<Int, String>()
            permissionHashMap[0] = Manifest.permission.CAMERA
            RunTimePermissionWrapper().requestPermission(permissionHashMap, this, this)
        })
        }

    override fun permissionCallbackResult(permissionResultHashMap: HashMap<Int, PermissionPojo>) {
        for ((requestCode, permissionString) in permissionResultHashMap) {
            println("******************${requestCode}, $permissionString*****************")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        println("******************onRequestPermissionsResult MainActivity ")
    }
}
