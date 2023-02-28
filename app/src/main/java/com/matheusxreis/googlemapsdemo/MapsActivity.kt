package com.matheusxreis.googlemapsdemo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.DrawableContainer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.matheusxreis.googlemapsdemo.databinding.ActivityMapsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val cameraAndViewport: CameraAndViewport by lazy {
        CameraAndViewport()
    }
    private val markers: Markers by lazy {
        Markers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_types_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cameraAndViewport.setMapType(item.itemId, mMap)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val saoPaulo = LatLng(-23.618652, -46.6033463)
        val spMarker = mMap
            .addMarker(
                MarkerOptions()
                    .position(saoPaulo)
                    .title("Marker in SP")
                    .flat(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

            )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 15f))
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        cameraAndViewport.setMapStyle(mMap, this@MapsActivity)
        lifecycleScope.launch {
            delay(4000)
            mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraAndViewport.cameraPosition), 2000, null
            )
        }
    }


}