package com.imake.runtimepermissionwrapper

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlin.collections.HashMap


class RunTimePermissionWrapper : ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var appCompatActivityWrapper: AppCompatActivity
    private lateinit var runPermissionCallbackResult: RunPermissionCallbackResult
    private lateinit var context: Context
    private var permissionHashMapTemp = HashMap<Int, String>()
    private val permissionResultHashMap = HashMap<Int, PermissionPojo>()


    /**
     * Requests the [android.Manifest.permission.CAMERA] permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    fun requestPermission(
        permissionHashMap: HashMap<Int, String>,
        listener: RunPermissionCallbackResult, appCompatActivity: AppCompatActivity
    ) {
        appCompatActivityWrapper = appCompatActivity
        runPermissionCallbackResult = listener
        //@Argument PermissionHashMap is HashMap key is requestCode and value is permission string
        //Check current SDk is greater then M, For request permission

        this.permissionHashMapTemp = permissionHashMap

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //Check permission it's granted or not,
            // Before to check shouldShowRequestPermissionRationaleCompat and requestPermissionsCompat
            for ((requestCode, permissionString) in permissionHashMap) {
                if (appCompatActivity.checkSelfPermissionCompat(permissionString) == PackageManager.PERMISSION_GRANTED) {
                    //PermissionPojo is consist of Two field:
                    //1. permissionGrantedOrNor by default 1, 0 is Permission is granted, 1 is not granted.
                    //2. permissionName
                    val permissionObj = PermissionPojo()
                    permissionObj.permissionGranted = 0
                    permissionObj.permissionName = permissionString
                    permissionResultHashMap[requestCode] = permissionObj
                }
            }
            val permissionsArray: Array<String?> = arrayOfNulls<String>(permissionHashMapTemp.size)
            for (x in 0 until permissionHashMapTemp.size) {
                permissionsArray[x] = permissionHashMapTemp[x]
            }
            appCompatActivity.requestPermissionsCompat(
                permissionsArray
                , 0
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        println("******************onRequestPermissionsResult RunTimePermissionWrapper ")
        if (requestCode == 0) {
            for (permissionsIndex in permissions.indices) {
                // Request for camera permission.
                if (grantResults[permissionsIndex] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted. Start camera preview Activity.
                    for ((requestCodeHm, permissionObj) in permissionResultHashMap) {
                        if (permissionObj.permissionName == permissions[permissionsIndex])
                            permissionObj.permissionGranted = 0
                        permissionResultHashMap[requestCodeHm] = permissionObj
                    }
                } else {
                    // Permission request was denied.
                    for ((requestCodeHm, permissionObj) in permissionResultHashMap) {
                        if (permissionObj.permissionName == permissions[permissionsIndex])
                            permissionObj.permissionGranted = 1
                        permissionResultHashMap[requestCodeHm] = permissionObj
                    }
                }
            }
        }
        runPermissionCallbackResult.permissionCallbackResult(permissionResultHashMap)
    }
}

