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
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.util.BoundingBox;

import java.util.ArrayList;

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


        IMapController mapController = map.getController();

        // Start the map on Israel
        //TODO: Map view only for israel
        GeoPoint startPoint = new GeoPoint(31.8124247,34.8594762);
        mapController.setCenter(startPoint);
        mapController.setZoom(10);

        // Add station's location
        // TODO: Dynamic function - add locations from DB
        ArrayList<OverlayItem> stations = new ArrayList<OverlayItem>();
        stations.add(new OverlayItem("Title", "Description", new GeoPoint(32.0728237835651, 34.79334675443333)));
        stations.add(new OverlayItem("Title", "Description", new GeoPoint(32.085160155331636, 34.79868415443362)));
        stations.add(new OverlayItem("Title", "Description", new GeoPoint(31.987942570365515, 34.75737162559562)));
        stations.add(new OverlayItem("Title", "Description", new GeoPoint(32.79312932825393, 34.957639232304736)));
        stations.add(new OverlayItem("Title", "Description", new GeoPoint(32.166803385081394, 34.81959750370291)));



        // Add the overlay icons with click listener
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(stations,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Navigation.findNavController(getView()).navigate(R.id.login_Fragment);
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, ctx);
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);




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