package com.matheusxreis.googlemapsdemo.misc

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.matheusxreis.googlemapsdemo.R

class CustomInfoWindowAdapter(
    context: Context
):InfoWindowAdapter {

    private val contentView = (context as Activity).layoutInflater.inflate(R.layout.custom_info_window, null)

    override fun getInfoContents(marker: Marker): View? {
        renderViews(marker, contentView)
        return contentView
    }

    override fun getInfoWindow(marker: Marker): View? {
        renderViews(marker, contentView)
        return contentView
    }


    private fun renderViews(marker: Marker, contentView: View){
        val title = marker.title
        val description = marker.snippet

        val titleTextView = contentView.findViewById<TextView>(R.id.titleTv)
        val descriptionTextView = contentView.findViewById<TextView>(R.id.description)

        titleTextView.text = title
        descriptionTextView.text = description

    }
}