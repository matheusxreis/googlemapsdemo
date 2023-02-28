package com.matheusxreis.googlemapsdemo.misc

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.matheusxreis.googlemapsdemo.R

class CameraAndViewport {

    val cameraPosition = CameraPosition.Builder()
            .target(LatLng(-23.618652, -46.6033463))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()

    fun setMapType(id:Int, mMap: GoogleMap){
        when (id) {
            R.id.normal_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            R.id.hybrid_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
            R.id.satellite_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
            R.id.terrain_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
            R.id.none_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NONE
            }
        }

    }

    fun setMapStyle(googleMap: GoogleMap, context: Context) {
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.d("Maps", "Failed to load style")
            }
        } catch (e: Exception) {
            Log.d("Maps", e.toString())
        }
    }

}