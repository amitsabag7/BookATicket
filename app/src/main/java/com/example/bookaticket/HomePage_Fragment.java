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
import com.example.bookaticket.Model.Station;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomePage_Fragment extends Fragment {

    private Button logout;
    private MapView map;
    private MyLocationNewOverlay mLocationOverlay;
    public List<Station> stations = new LinkedList<>();
    public ArrayList<OverlayItem> items = new ArrayList<>();

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

        // Get all station from DB and draw to map
        //TODO: Write to local DB
        Model.instance().getAllStations((list)-> {
            stations=list;
            stationsToOverlaysItems(items,stations);
            viewLocaionOnMap(ctx,items);
        });

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

    public void stationsToOverlaysItems (ArrayList<OverlayItem> items,List<Station> stations){
        GeoPoint p;
        for (Station st: stations) {
            p = new GeoPoint(st.getLocation().getLatitude(),st.getLocation().getLongitude());
            items.add(new OverlayItem(st.getId(),st.getName(),p));

        }
    }

    public void viewLocaionOnMap(Context ctx, ArrayList<OverlayItem> items) {
        // Add the overlay icons with click listener
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        HomePage_FragmentDirections.ActionHomePageFragmentToStationBookListFragment action = HomePage_FragmentDirections.actionHomePageFragmentToStationBookListFragment(item.getTitle(),item.getSnippet());
                        Navigation.findNavController(getView()).navigate(action);
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, ctx);
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);
    }


}