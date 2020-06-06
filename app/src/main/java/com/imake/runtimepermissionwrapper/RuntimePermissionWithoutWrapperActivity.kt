package com.imake.runtimepermissionwrapper

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class RuntimePermissionWithoutWrapperActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_CODE: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runTimePermissionBtn.setOnClickListener(View.OnClickListener {
            if (isPermissionGranted(Manifest.permission.SEND_SMS)) {
                Toast.makeText(
                    this,
                    "Requested runtime permission is already enable.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                requestRuntimePermission(Manifest.permission.SEND_SMS, MY_PERMISSIONS_REQUEST_CODE)
            }

        })
    }

    private fun requestRuntimePermission(permission: String, requestCode: Int) {
        // Show permission rationale and request
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(
                this,
                "Request permission promote with NeverAskAgain option.",
                Toast.LENGTH_SHORT
            ).show()

            showPermissionReasonAndRequestDialog(
                "Permission",
                "Hi, we will request SEND SMS permission. " +
                        "This is required for authenticating your device, " +
                        "please grant it.",
                permission,
                requestCode
            )

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.
            Toast.makeText(this, "First Time request permission promote.", Toast.LENGTH_SHORT)
                .show()
            ActivityCompat.requestPermissions(
                this, arrayOf(permission),
                requestCode
            )

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

    }

    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            this, permission
        ) == PackageManager.PERMISSION_GRANTED


    private fun showPermissionReasonAndRequestDialog(
        title: String,
        message: String,
        permission: String,
        requestCode: Int
    ) {
        val dialogForMessage = Dialog(this)
        dialogForMessage.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogForMessage.setContentView(R.layout.alert_for_runtime_permission)
        dialogForMessage.setCancelable(false)
        val titleTv = dialogForMessage.findViewById<TextView>(R.id.title)
        val m = dialogForMessage.findViewById<TextView>(R.id.message)
        m.text = message
        titleTv.text = title
        val okBtn =
            dialogForMessage.findViewById<Button>(R.id.okBtn)
        val cancelBtn =
            dialogForMessage.findViewById<Button>(R.id.cancelBtn)
        dialogForMessage.show()
        okBtn.setOnClickListener {
            dialogForMessage.dismiss()
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                requestCode
            )
        }
        cancelBtn.setOnClickListener { dialogForMessage.dismiss() }
    }

    @SuppressLint("ResourceType")
    private fun navigationToAppSettingDialog(title: String, message: String) {
        val dialogForMessage = Dialog(this)
        dialogForMessage.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogForMessage.setContentView(R.layout.alert_for_runtime_permission)
        dialogForMessage.setCancelable(false)
        val titleTv = dialogForMessage.findViewById<TextView>(R.id.title)
        val m = dialogForMessage.findViewById<TextView>(R.id.message)
        val iconImageView = dialogForMessage.findViewById<ImageView>(R.id.iconImageView)
        iconImageView.setImageDrawable(getDrawable(R.drawable.ic_settings))
        m.text = message
        titleTv.text = title
        val okBtn = dialogForMessage.findViewById<Button>(R.id.okBtn)
        val cancelBtn = dialogForMessage.findViewById<Button>(R.id.cancelBtn)
        dialogForMessage.show()

        okBtn.setOnClickListener {
            dialogForMessage.dismiss()
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        cancelBtn.setOnClickListener { dialogForMessage.dismiss() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // It's not our expect permission
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) return
        if (isPermissionGranted(permissions[0])) {
            // Success case: Get the permission
            // Do something and return
            Toast.makeText(this, "Run time permission granted Successfully", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (isUserCheckNeverAskAgain(permissions[0])) {
            // NeverAskAgain case - Never Ask Again has been checked
            // Do something and return
            Toast.makeText(
                this,
                "NeverAskAgain is Active, Permission dialog won't Show.",
                Toast.LENGTH_SHORT
            ).show()
            navigationToAppSettingDialog(
                "Permission",
                "App permission is required for functionality, Enable all permission from app setting."
            )
            return
        }
        Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isUserCheckNeverAskAgain(s: String) =
        !ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            s
        )
}

