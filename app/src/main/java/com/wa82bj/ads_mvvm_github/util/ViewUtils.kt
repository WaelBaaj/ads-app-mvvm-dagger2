package com.wa82bj.ads_mvvm_github.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.wa82bj.ads_mvvm_github.R


fun Context.toast(message : String) {

    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}

fun Context.onSNACK(view: View, message : String, action : String){

    val snackbar = Snackbar.make(view, message,
        Snackbar.LENGTH_LONG).setAction(action, null)
    snackbar.setActionTextColor(resources.getColor(R.color.white))
    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(resources.getColor(R.color.textColorPrimary))
    val textView =
        snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(resources.getColor(R.color.white))
    textView.textSize = 14f
    snackbar.show()
}

fun Context.goToLocation(lat : String,long : String){

    val strUri = "http://maps.google.com/maps?q=loc:$lat,$long (Label which you want)"
    val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri))

    intent.setClassName(
        "com.google.android.apps.maps",
        "com.google.android.maps.MapsActivity")

    startActivity(intent)
}

fun Context.callPhoneIntent(phone : String){

    val intent = Intent(Intent.ACTION_CALL, Uri.parse
        ("tel:" + phone))
    startActivity(intent)
}