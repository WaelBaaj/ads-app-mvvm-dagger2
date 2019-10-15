package com.wa82bj.ads_mvvm_github.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.checkPhonePermission() : Boolean {
    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this as Activity,
                Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE),
                42)
            return false
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE),
                42)
            return false
        }
    } else {

        return true
    }

}