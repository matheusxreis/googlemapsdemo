package com.matheusxreis.googlemapsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.matheusxreis.googlemapsdemo.databinding.ActivityMapsBinding
import com.matheusxreis.googlemapsdemo.misc.CameraAndViewport
import com.matheusxreis.googlemapsdemo.misc.Markers
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

        val uberlandia1 = LatLng(-18.897164548406188, -48.267606783188036)
        val uberlandia2 = LatLng(-18.897620017795344, -48.26966083225863)
        val uberlandiaMarker1 = mMap
            .addMarker(
                MarkerOptions()
                    .position(uberlandia1)
                    .title("Marker in Uberlandia1")
                    .snippet("This is the first marker")
                    .flat(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

            )
        val uberlandiaMarker2 = mMap
            .addMarker(
                MarkerOptions()
                    .position(uberlandia2)
                    .title("Marker in Uberlandia2")
                    .snippet("This is the second marker")
                    .flat(true)
                    .zIndex(1f)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

            )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uberlandia1, 15f))
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        cameraAndViewport.setMapStyle(mMap, this@MapsActivity)
        lifecycleScope.launch {
            delay(4000)
            // mMap.animateCamera(
            //   CameraUpdateFactory.newCameraPosition(cameraAndViewport.cameraPosition), 2000, null
            //  )
        }
    }


}