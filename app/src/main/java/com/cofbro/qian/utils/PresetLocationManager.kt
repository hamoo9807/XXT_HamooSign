package com.cofbro.qian.utils

import android.content.Context
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.io.File
import java.io.FileWriter

object PresetLocationManager {
    data class PresetLocation(
        val name: String,
        val address: String,
        val latitude: Double,
        val longitude: Double
    )

    fun loadAll(context: Context): List<PresetLocation> {
        val json = Downloader.acquire(context, Constants.RecycleJson.PRESET_LOCATION_DATA)
            .safeParseToJson()
        val array = json.getJSONArrayExt("locations")
        val result = mutableListOf<PresetLocation>()
        for (i in 0 until array.size) {
            val item = array.getJSONObject(i)
            result.add(
                PresetLocation(
                    name = item.getString("name") ?: "",
                    address = item.getString("address") ?: "",
                    latitude = item.getDouble("latitude") ?: 0.0,
                    longitude = item.getDouble("longitude") ?: 0.0
                )
            )
        }
        return result
    }

    fun add(context: Context, location: PresetLocation) {
        val json = Downloader.acquire(context, Constants.RecycleJson.PRESET_LOCATION_DATA)
            .safeParseToJson()
        val path = context.filesDir.path + File.separatorChar + Constants.RecycleJson.PRESET_LOCATION_DATA
        val file = File(path)
        val array: JSONArray
        if (!file.exists()) {
            file.createNewFile()
            array = JSONArray()
        } else {
            array = json.getJSONArrayExt("locations")
        }
        val item = JSONObject()
        item["name"] = location.name
        item["address"] = location.address
        item["latitude"] = location.latitude
        item["longitude"] = location.longitude
        array.add(item)
        val root = JSONObject()
        root["locations"] = array
        root["size"] = array.size
        writeJson(context, root)
    }

    fun remove(context: Context, index: Int) {
        val json = Downloader.acquire(context, Constants.RecycleJson.PRESET_LOCATION_DATA)
            .safeParseToJson()
        val array = json.getJSONArrayExt("locations")
        if (index in 0 until array.size) {
            array.remove(index)
            val root = JSONObject()
            root["locations"] = array
            root["size"] = array.size
            writeJson(context, root)
        }
    }

    fun saveAll(context: Context, locations: List<PresetLocation>) {
        val array = JSONArray()
        locations.forEach { loc ->
            val item = JSONObject()
            item["name"] = loc.name
            item["address"] = loc.address
            item["latitude"] = loc.latitude
            item["longitude"] = loc.longitude
            array.add(item)
        }
        val root = JSONObject()
        root["locations"] = array
        root["size"] = array.size
        writeJson(context, root)
    }

    private fun writeJson(context: Context, json: JSONObject) {
        try {
            val fileWriter = FileWriter(context.filesDir.path + File.separatorChar + Constants.RecycleJson.PRESET_LOCATION_DATA)
            json.writeJSONString(fileWriter)
            fileWriter.close()
        } catch (_: Exception) {
        }
    }
}
