package com.techdatum.epanchayat_application

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.techdatum.epanchayat_application.webservices.InterNetReceiver


class EPanchayatApplication : MultiDexApplication(), InterNetReceiver.ConnectivityReceiverListener {
    var isConnected = false

    companion object {
        var epanchayatApplication: EPanchayatApplication = EPanchayatApplication()
        fun getInstance(): EPanchayatApplication {
            if (epanchayatApplication == null) {
                epanchayatApplication = EPanchayatApplication()
            }
            return epanchayatApplication
        }

        var isLayout = 0
    }

    override fun onCreate() {
        super.onCreate()
        epanchayatApplication = this
        MultiDex.install(this)
        isConnected = InterNetReceiver.isConnected
    }

    @SuppressLint("CheckResult")
    fun imageBind(image: ImageView, url: String?) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_launcher_background)
        requestOptions.error(R.drawable.ic_launcher_background)
        requestOptions.fitCenter()
        Glide.with(image.context)
            .load(url)
            .apply(requestOptions)
            .into(image)
    }

    override fun onNetworkConnectionChanged(Connected: Boolean) {
        isConnected = Connected
    }

}

