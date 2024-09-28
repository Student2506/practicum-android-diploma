package ru.practicum.android.diploma.commonutils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return (capabilities != null
        && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
}
