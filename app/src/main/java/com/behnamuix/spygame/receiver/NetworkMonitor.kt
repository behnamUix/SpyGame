package com.behnamuix.spygame.receiver

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow


class NetworkMonitor(context: Context) {
    val netOk = mutableStateOf(false)

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val netWorkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            netOk.value = true


        }

        override fun onLost(network: Network) {
            super.onLost(network)
            netOk.value = false
        }

    }

    fun register() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, netWorkCallBack)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(netWorkCallBack)
    }


}

