package com.techdatum.epanchayat_application.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.techdatum.epanchayat_application.R


class SplashScreen : Activity() {
    var permissionsRequired = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.SEND_SMS
    )
    private var permissionStatus: SharedPreferences? = null
    private val sentToSettings = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        permissionStatus =
            getSharedPreferences("permissionStatus", Context.MODE_PRIVATE)
        val prefs = getSharedPreferences(
            MY_PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val edit = prefs.edit()
        val flag = prefs.getBoolean("state", false)
        edit.commit()
        if (flag == false) {
            Log.e(TAG, "flag$flag")
        } else {
            Log.e(TAG, "noflag$flag")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkpermisn()
        } else {
            validateLogin()
        }
    }

    private fun checkpermisn() {
        if (ActivityCompat.checkSelfPermission(
                this@SplashScreen,
                permissionsRequired[0]
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this@SplashScreen,
                permissionsRequired[1]
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this@SplashScreen,
                permissionsRequired[2]
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this@SplashScreen,
                permissionsRequired[3]
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this@SplashScreen,
                permissionsRequired[4]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[0]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[1]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[2]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[3]
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[4]
                )
            ) { //Show Information about why you need the permission
                val builder =
                    AlertDialog.Builder(this@SplashScreen)
                builder.setTitle("Need Multiple Permissions")
                builder.setMessage("This app needs Phone,Camera,Calender and Location permissions.")
                builder.setPositiveButton(
                    "Grant"
                ) { dialog, which ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(
                        this@SplashScreen,
                        permissionsRequired,
                        PERMISSION_CALLBACK_CONSTANT
                    )
                    val i = Intent(this@SplashScreen, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, which -> dialog.cancel() }
                builder.show()
            } else if (permissionStatus!!.getBoolean(permissionsRequired[0], false)) {
                val i = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(i)
                finish()
            } else {
                ActivityCompat.requestPermissions(
                    this@SplashScreen,
                    permissionsRequired,
                    PERMISSION_CALLBACK_CONSTANT
                )
            }
            val editor = permissionStatus!!.edit()
            editor.putBoolean(permissionsRequired[0], true)
            editor.commit()
        } else {
            proceedAfterPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }
            if (allgranted) {
                proceedAfterPermission()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[0]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[1]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[2]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[3]
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashScreen,
                    permissionsRequired[4]
                )
            ) {
                val builder =
                    AlertDialog.Builder(this@SplashScreen)
                builder.setTitle("Need Multiple Permissions")
                builder.setMessage("This app needs Camera and Location permissions.")
                builder.setPositiveButton(
                    "Grant"
                ) { dialog, which ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(
                        this@SplashScreen,
                        permissionsRequired,
                        PERMISSION_CALLBACK_CONSTANT
                    )
                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, which -> dialog.cancel() }
                builder.show()
            } else {
                val i = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(
                    this@SplashScreen,
                    permissionsRequired[0]
                ) == PackageManager.PERMISSION_GRANTED
            ) { //Got Permission
                proceedAfterPermission()
            }
        }
    }

    private fun proceedAfterPermission() {
        validateLogin()
    }

    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(
                    this@SplashScreen,
                    permissionsRequired[0]
                ) == PackageManager.PERMISSION_GRANTED
            ) { //Got Permission
                proceedAfterPermission()
            }
        }
    }

    private fun validateLogin() {
        Handler().postDelayed({
            val i = Intent(this@SplashScreen, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 2000
        //permissions
        private const val PERMISSION_CALLBACK_CONSTANT = 100
        private const val REQUEST_PERMISSION_SETTING = 101
        private val TAG = SplashScreen::class.java.simpleName
        const val MY_PREFS_NAME = "MyPrefsFile"
    }
}