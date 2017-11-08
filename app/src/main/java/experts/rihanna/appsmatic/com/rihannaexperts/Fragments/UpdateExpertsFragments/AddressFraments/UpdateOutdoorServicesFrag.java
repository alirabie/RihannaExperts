package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments;

import android.content.Context;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.GPS.GPSTracker;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class UpdateOutdoorServicesFrag extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double lat, lang;
    private Marker marker;
    private MapView mapView;
    private GPSTracker gpsTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_outdoor_services, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        gpsTracker = new GPSTracker(getContext().getApplicationContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        lat = gpsTracker.getLatitude();
        lang = gpsTracker.getLongitude();
        LatLng currentLocation = new LatLng(lat, lang);
        marker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Location"));
        float zoomLevel = (float) 16.0; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(latLng.latitude, latLng.longitude))
                        .draggable(true).visible(true).title("Location det"));
                lat = latLng.latitude;
                lang = latLng.longitude;
                Toast.makeText(getContext(), lat + " " + lang + "", Toast.LENGTH_SHORT).show();
            }
        });











    }
}
