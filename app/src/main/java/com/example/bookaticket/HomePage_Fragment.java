package com.example.bookaticket;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookaticket.Model.Model;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class HomePage_Fragment extends Fragment {

    private Button logout;
    private MapView map;
    private MyLocationNewOverlay mLocationOverlay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page_, container, false);

        Context ctx = view.getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = (MapView) view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

//        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
//        this.mLocationOverlay.enableMyLocation();
//        map.getOverlays().add(this.mLocationOverlay);

        IMapController mapController = map.getController();

        GeoPoint startPoint = new GeoPoint(30.8124247,34.8594762);
        mapController.setCenter(startPoint);
        mapController.setZoom(9.5);
        



        logout = view.findViewById(R.id.logut_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance().logoutuser();
                Navigation.findNavController(view).navigate(R.id.login_Fragment);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }


}