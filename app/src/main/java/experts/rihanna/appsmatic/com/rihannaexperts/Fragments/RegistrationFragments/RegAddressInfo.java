package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Address.Customer;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Address.PostAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExpertRegistartaion.ResExpertRegister;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.BillingAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.UpdateEpert;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.Response.UpdateExpertResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.SignUp;
import experts.rihanna.appsmatic.com.rihannaexperts.GPS.GPSTracker;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegAddressInfo extends Fragment implements OnMapReadyCallback {

    private TextView next;
    private TextView skip;
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
    private EditText addr1;
    private EditText city;
    private BillingAddress billingAddress;
    private  experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.Customer customer;
    UpdateEpert updateEpert;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next=(TextView)view.findViewById(R.id.next_btn);
        location=(EditText)view.findViewById(R.id.reg_loc_location);
        mapView=(MapView) view.findViewById(R.id.map);
        addr1=(EditText)view.findViewById(R.id.reg_loc_bulding_num);
        city=(EditText)view.findViewById(R.id.reg_loc_district);
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

                if(city.getText().toString().isEmpty()){
                    city.setError(getResources().getString(R.string.cityreq));
                }else if(addr1.getText().toString().isEmpty()){
                    addr1.setError(getResources().getString(R.string.addr1req));
                }else if(location.getText().toString().isEmpty()){
                    location.setError(getResources().getString(R.string.addr2req));
                }else {
                    PostAddress postAddress=new PostAddress();
                    Customer customer=new Customer();
                    experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Address.BillingAddress billingAddress=new experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Address.BillingAddress();
                    billingAddress.setFirstName(SignUp.expertFname);
                    billingAddress.setLastName(SignUp.expertLname);
                    billingAddress.setPhoneNumber(SignUp.expertPhoneNum);
                    billingAddress.setEmail(SignUp.expertEmail);
                    billingAddress.setAddress1(addr1.getText().toString());
                    billingAddress.setAddress2(location.getText().toString());
                    billingAddress.setCity(city.getText().toString());
                    billingAddress.setCountryId(69);
                    billingAddress.setStateProvinceId(40);
                    billingAddress.setZipPostalCode("10021");
                    List<Integer>role_ids=new ArrayList<Integer>();
                    role_ids.add(3);
                    customer.setRoleIds(role_ids);
                    customer.setBillingAddress(billingAddress);
                    customer.setFirstName(SignUp.expertFname);
                    customer.setLastName(SignUp.expertLname);
                    customer.setEmail(SignUp.expertEmail);
                    customer.setPhone(SignUp.expertPhoneNum);
                    customer.setPassword(SignUp.password);
                    postAddress.setCustomer(customer);


                    final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
                    mProgressDialog.show();

                    Gson gson=new Gson();
                    Log.e("reg",gson.toJson(postAddress));
                    Generator.createService(ExpertsApi.class).registerNewExpert(postAddress).enqueue(new Callback<ResExpertRegister>() {
                        @Override
                        public void onResponse(Call<ResExpertRegister> call, Response<ResExpertRegister> response) {
                            if (response.isSuccessful()) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response.body() != null) {
                                    //Registration success
                                    Toast.makeText(getContext(),getResources().getString(R.string.registersucsess)+" "+response.body().getName(), Toast.LENGTH_SHORT).show();
                                    SignUp.expertId = response.body().getId()+"";
                                    Gson gson=new Gson();
                                    Log.e("regResponce",gson.toJson(response.body()));
                                    SignUp.addressdone=1;
                                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.register_fm_contanier, new RegExperience());
                                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                    fragmentTransaction.commit();
                                    Log.e("Id : ", SignUp.expertId);
                                } else {
                                    Toast.makeText(getContext(), "Null from expert registration", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                try {
                                    Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<ResExpertRegister> call, Throwable t) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            Toast.makeText(getContext(), "Connection error from expert registration" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

    }


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

                Log.e("lat ",lat+"  "+lang+"");
                try {
                    addresses = geocoder.getFromLocation(lat, lang, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("lat ", e.getMessage());
                }

                if(addresses!=null) {
                    if (!addresses.isEmpty()) {
                        cityName = addresses.get(0).getLocality();
                        stateName = addresses.get(0).getAddressLine(0);
                        countryName=addresses.get(0).getCountryName();

                        StringBuilder stringBuilder=new StringBuilder();
                        if(cityName!=null){
                            stringBuilder.append(cityName+",");
                        }else if(stateName!=null){
                            stringBuilder.append(stateName+",");
                        }else if (countryName!=null){
                            stringBuilder.append(countryName);
                        }
                        location.setText(stringBuilder.toString());
                        location.setText(stringBuilder.toString());
                    }
                }




            }
        });

    }
}
