package experts.rihanna.appsmatic.com.rihannaexperts.Fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.GPS.GPSTracker;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class RegAddressInfo extends Fragment implements OnMapReadyCallback {

    private TextView next;
    private GoogleMap mMap;
    private Double lat,lang;
    private Marker marker;
    private MapView mapView;
    private GPSTracker gpsTracker;
    private String cityName;
    private String stateName;
    private String countryName;
    private Geocoder geocoder;
    private EditText location;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next=(TextView)view.findViewById(R.id.next);
        location=(EditText)view.findViewById(R.id.reg_loc_location);
        mapView=(MapView)view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        gpsTracker=new GPSTracker(getContext().getApplicationContext());



        //Go to next step Expert Services
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                next.clearAnimation();
                next.setAnimation(anim);

                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.register_fm_contanier, new RegExperience());
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
            }
        });





    }


    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                    next.clearAnimation();
                    next.setAnimation(anim);
                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.register_fm_contanier, new RegPersonalInfo());
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });




    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lat=gpsTracker.getLatitude();
        lang=gpsTracker.getLongitude();
        LatLng currentLocation=new LatLng(lat,lang);
        marker=mMap.addMarker(new MarkerOptions().position(currentLocation).title("Location"));
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

                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(lat, lang, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(addresses!=null) {
                    if (!addresses.isEmpty()) {
                        cityName = addresses.get(0).getAddressLine(0);
                        stateName = addresses.get(0).getAddressLine(1);
                        countryName = addresses.get(0).getAddressLine(2);
                    }
                }


                location.setText(cityName + "," + stateName + "," + countryName);

            }
        });

    }
}
