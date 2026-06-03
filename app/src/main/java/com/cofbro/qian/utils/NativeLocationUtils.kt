package com.cofbro.qian.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.coroutines.resume

object NativeLocationUtils {

    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    suspend fun getCurrentLocation(
        context: Context,
        onSuccess: (Double, Double, String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!hasLocationPermission(context)) {
            onError("无定位权限")
            return
        }

        try {
            val amapResult = requestAmapLocation(context)
            if (amapResult != null) {
                onSuccess(amapResult.first, amapResult.second, amapResult.third)
                return
            }
        } catch (e: Exception) {
            DebugLogCollector.d("Location", "高德定位失败, 回退到原生定位: ${e.message}")
        }

        fallbackToNativeLocation(context, onSuccess, onError)
    }

    private suspend fun requestAmapLocation(context: Context): Triple<Double, Double, String>? {
        return suspendCancellableCoroutine { cont ->
            try {
                com.amap.api.location.AMapLocationClient.updatePrivacyAgree(context, true)
                com.amap.api.location.AMapLocationClient.updatePrivacyShow(context, true, true)
                // 使用加密存储的Key,不从Manifest读取明文
                val apiKey = AmapKeyStore.getApiKey()
                com.amap.api.location.AMapLocationClient.setApiKey(apiKey)
                DebugLogCollector.d("Location", "高德Key设置: ${apiKey.take(4)}****${apiKey.takeLast(4)} 长度=${apiKey.length}")
                val mLocationClient = com.amap.api.location.AMapLocationClient(context)
                val mLocationOption = com.amap.api.location.AMapLocationClientOption()
                mLocationOption.locationMode = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                mLocationOption.isOnceLocation = true
                mLocationOption.isLocationCacheEnable = true
                mLocationOption.httpTimeOut = 15000
                mLocationClient.setLocationListener { amapLocation ->
                    mLocationClient.stopLocation()
                    mLocationClient.onDestroy()
                    if (amapLocation != null && amapLocation.errorCode == 0) {
                        DebugLogCollector.d("Location", "高德定位成功: lat=${amapLocation.latitude}, lon=${amapLocation.longitude}, 精度=${amapLocation.accuracy}m, 地址=${amapLocation.address}")
                        val address = amapLocation.country + " " + amapLocation.address
                        val bdLating = AmapUtils.mapPointGdTurnBaiDu(amapLocation.longitude, amapLocation.latitude)
                        if (cont.isActive) cont.resume(Triple(bdLating.latitude, bdLating.longitude, address))
                    } else {
                        val errCode = amapLocation?.errorCode ?: -1
                        val errMsg = amapLocation?.errorInfo ?: "null"
                        DebugLogCollector.w("Location", "高德定位失败: errorCode=$errCode, errorInfo=$errMsg")
                        if (cont.isActive) cont.resume(null)
                    }
                }
                mLocationClient.setLocationOption(mLocationOption)
                mLocationClient.startLocation()

                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    if (cont.isActive) {
                        try { mLocationClient.stopLocation(); mLocationClient.onDestroy() } catch (_: Exception) {}
                        cont.resume(null)
                    }
                }, 16000)

                cont.invokeOnCancellation {
                    try { mLocationClient.stopLocation(); mLocationClient.onDestroy() } catch (_: Exception) {}
                }
            } catch (e: Exception) {
                if (cont.isActive) cont.resume(null)
            }
        }
    }

    private suspend fun fallbackToNativeLocation(
        context: Context,
        onSuccess: (Double, Double, String) -> Unit,
        onError: (String) -> Unit
    ) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val lastLocation = getLastKnownLocation(locationManager, context)
        if (lastLocation != null) {
            val bdLating = wgs84ToBd09(lastLocation.longitude, lastLocation.latitude)
            val address = reverseGeocode(context, lastLocation.latitude, lastLocation.longitude)
            onSuccess(bdLating.latitude, bdLating.longitude, address)
            return
        }

        try {
            val location = requestFreshLocation(locationManager, context)
            if (location != null) {
                val bdLating = wgs84ToBd09(location.longitude, location.latitude)
                val address = reverseGeocode(context, location.latitude, location.longitude)
                onSuccess(bdLating.latitude, bdLating.longitude, address)
            } else {
                onError("无法获取定位")
            }
        } catch (e: Exception) {
            onError("定位失败: ${e.message}")
        }
    }

    @Suppress("MissingPermission")
    private fun getLastKnownLocation(locationManager: LocationManager, context: Context): Location? {
        var bestLocation: Location? = null
        val providers = locationManager.getProviders(true)
        for (provider in providers) {
            val location = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                bestLocation = location
            }
        }
        return bestLocation
    }

    @Suppress("MissingPermission")
    private suspend fun requestFreshLocation(
        locationManager: LocationManager,
        context: Context
    ): Location? = suspendCancellableCoroutine { cont ->
        val providers = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER
        )

        var resolved = false

        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if (!resolved) {
                    resolved = true
                    try {
                        locationManager.removeUpdates(this)
                    } catch (_: Exception) {}
                    cont.resume(location)
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {
                if (!resolved) {
                    val nextProvider = providers.firstOrNull {
                        it != provider && locationManager.isProviderEnabled(it)
                    }
                    if (nextProvider == null) {
                        resolved = true
                        try { locationManager.removeUpdates(this) } catch (_: Exception) {}
                        cont.resume(null)
                    }
                }
            }
        }

        var requestedAny = false
        for (provider in providers) {
            if (locationManager.isProviderEnabled(provider)) {
                try {
                    locationManager.requestLocationUpdates(
                        provider, 0L, 0f, listener
                    )
                    requestedAny = true
                } catch (_: Exception) {}
            }
        }

        if (!requestedAny) {
            resolved = true
            cont.resume(null)
        }

        cont.invokeOnCancellation {
            try {
                locationManager.removeUpdates(listener)
            } catch (_: Exception) {}
        }

        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            if (!resolved) {
                resolved = true
                try { locationManager.removeUpdates(listener) } catch (_: Exception) {}
                val lastLoc = getLastKnownLocation(locationManager, context)
                cont.resume(lastLoc)
            }
        }, 10000)
    }

    private fun reverseGeocode(context: Context, lat: Double, lon: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.CHINA)
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val addr = addresses[0]
                val sb = StringBuilder()
                if (!addr.countryName.isNullOrEmpty()) sb.append(addr.countryName).append(" ")
                if (!addr.locality.isNullOrEmpty()) sb.append(addr.locality)
                if (!addr.subLocality.isNullOrEmpty()) sb.append(addr.subLocality)
                if (!addr.thoroughfare.isNullOrEmpty()) sb.append(addr.thoroughfare)
                sb.toString()
            } else {
                "中国"
            }
        } catch (_: Exception) {
            "中国"
        }
    }

    fun wgs84ToGcJ02(lon: Double, lat: Double): AmapUtils.BDLating {
        val pi = 3.14159265358979324
        val a = 6378245.0
        val ee = 0.00669342162296594323
        if (outOfChina(lon, lat)) {
            return AmapUtils.BDLating(lat, lon)
        }
        var dLat = transformLat(lon - 105.0, lat - 35.0)
        var dLon = transformLon(lon - 105.0, lat - 35.0)
        val radLat = lat / 180.0 * pi
        var magic = sin(radLat)
        magic = 1 - ee * magic * magic
        val sqrtMagic = sqrt(magic)
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi)
        dLon = (dLon * 180.0) / (a / sqrtMagic * cos(radLat) * pi)
        return AmapUtils.BDLating(lat + dLat, lon + dLon)
    }

    fun wgs84ToBd09(lon: Double, lat: Double): AmapUtils.BDLating {
        val gcj = wgs84ToGcJ02(lon, lat)
        return AmapUtils.mapPointGdTurnBaiDu(gcj.longitude, gcj.latitude)
    }

    private fun outOfChina(lon: Double, lat: Double): Boolean {
        return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271
    }

    private fun transformLat(x: Double, y: Double): Double {
        var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * sqrt(abs(x))
        ret += (20.0 * sin(6.0 * x * 3.14159265358979324) + 20.0 * sin(2.0 * x * 3.14159265358979324)) * 2.0 / 3.0
        ret += (20.0 * sin(y * 3.14159265358979324) + 40.0 * sin(y / 3.0 * 3.14159265358979324)) * 2.0 / 3.0
        ret += (160.0 * sin(y / 12.0 * 3.14159265358979324) + 320 * sin(y * 3.14159265358979324 / 30.0)) * 2.0 / 3.0
        return ret
    }

    private fun transformLon(x: Double, y: Double): Double {
        var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * sqrt(abs(x))
        ret += (20.0 * sin(6.0 * x * 3.14159265358979324) + 20.0 * sin(2.0 * x * 3.14159265358979324)) * 2.0 / 3.0
        ret += (20.0 * sin(x * 3.14159265358979324) + 40.0 * sin(x / 3.0 * 3.14159265358979324)) * 2.0 / 3.0
        ret += (150.0 * sin(x / 12.0 * 3.14159265358979324) + 300.0 * sin(x / 30.0 * 3.14159265358979324)) * 2.0 / 3.0
        return ret
    }

    private fun abs(x: Double): Double = kotlin.math.abs(x)
}
