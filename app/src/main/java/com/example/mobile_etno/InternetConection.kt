package com.example.mobile_etno

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.net.URL
import java.net.URLConnection

fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}
fun isConnectedToServer(): Boolean {
    return try {
        val myUrl = URL("http://192.168.137.1:8080")
        val connection: URLConnection = myUrl.openConnection()
        connection.connectTimeout = 15
        connection.connect()
        true
    } catch (e: Exception) {
        // Handle your exceptions
        false
    }
}