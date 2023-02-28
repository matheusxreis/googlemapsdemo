package com.matheusxreis.googlemapsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.matheusxreis.googlemapsdemo.databinding.ActivityMapsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

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

        when(item.itemId){
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
        return true
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val saoPaulo = LatLng(-23.618652,-46.6033463)
        val spMarker = mMap.addMarker(MarkerOptions().position(saoPaulo).title("Marker in SP"))

        spMarker!!.tag = "Restaurant"
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 15f))
       // mMap.moveCamera(CameraUpdateFactory.newCameraPosition(setCameraPosition()))
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        mMap.setPadding(0,0,300, 0)
        setMapStyle(mMap)

        mMap.setOnMarkerClickListener(this)

        lifecycleScope.launch {
            delay(4000)
        }

    }

    private fun setMapStyle(googleMap: GoogleMap){
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )
            if(!success){
                Log.d("Maps", "Failed to load style")
            }
        }catch(e:Exception){
            Log.d("Maps", e.toString())
        }
    }

    private fun setCameraPosition():CameraPosition {
        val saoPaulo = CameraPosition.Builder()
            .target(LatLng(-23.618652,-46.6033463))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        return saoPaulo
    }

    private fun defineBounds() = LatLngBounds(
            LatLng(-23.688309448566308, -46.5733864479026), // Southwest boundary
            LatLng(-23.051332786377262, -46.3431110800338) // Northeast boundary
    )

    private fun onMapClicked(){
        mMap.setOnMapClickListener {
            Toast.makeText(this@MapsActivity, "Single Click", Toast.LENGTH_LONG).show()
        }
    }
    private fun onMapLongClicked(){
        mMap.setOnMapLongClickListener {
            mMap.addMarker(MarkerOptions().position(it).title("New marker"))

            Toast.makeText(this@MapsActivity, "Long Click", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Toast.makeText(this@MapsActivity, marker.tag as String, Toast.LENGTH_LONG).show()

        /// return false means that the default behavior will occurs too
        //  return true means that we override the behavior for completely
        return true
    }
}