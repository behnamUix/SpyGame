package com.behnamuix.spy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

fun Context.checkNet(): Boolean? {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = cm.activeNetwork ?: false
    val caps = cm.getNetworkCapabilities(nw as Network?) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

}