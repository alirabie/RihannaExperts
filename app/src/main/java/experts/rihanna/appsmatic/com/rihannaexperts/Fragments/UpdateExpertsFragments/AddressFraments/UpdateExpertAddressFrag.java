package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.PUT.UpdateExp;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.BillingAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.Customer;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.UpdateEpert;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.Response.UpdateExpertResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.GPS.GPSTracker;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateExpertAddressFrag extends Fragment  {

    private TextView next;
    private GoogleMap mMap;
    private Marker marker;
    private MapView mapView;
    private GPSTracker gpsTracker;
    private String cityName;
    private String stateName;
    private String countryName;
    private Geocoder geocoder;
    private EditText addr1;
    private EditText city;
    public static Double lat=0.0;
    public static Double lang=0.0;
    BillingAddress billingAddress;
    experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.Customer customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_expert_address, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        next = (TextView) view.findViewById(R.id.next_btn);
        addr1=(EditText)view.findViewById(R.id.reg_loc_bulding_num);
        city=(EditText)view.findViewById(R.id.reg_loc_district);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        geocoder = new Geocoder(getContext(), Locale.getDefault());
        gpsTracker = new GPSTracker(getContext().getApplicationContext());



        //Get Profile Info
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
        mProgressDialog.show();
        Generator.createService(ExpertsApi.class).getProfile(SaveSharedPreference.getCustId(getContext())).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    if (response.body().getCustomers() != null) {
                        city.setText(response.body().getCustomers().get(0).getBillingAddress().getCity());
                        addr1.setText(response.body().getCustomers().get(0).getBillingAddress().getAddress1());


                        if(response.body().getCustomers().get(0).getLatitude()!=null){
                            lat = Double.parseDouble(response.body().getCustomers().get(0).getLatitude().toString());
                        }else {
                            lat=gpsTracker.getLatitude();
                        }

                        if(response.body().getCustomers().get(0).getLongtitude()!=null){
                            lang = Double.parseDouble(response.body().getCustomers().get(0).getLongtitude().toString());
                        }else {
                            lang = gpsTracker.getLongitude();
                        }


                        //Run Map
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                mMap = googleMap;
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

                                        List<Address> addresses = null;

                                        try {
                                            addresses = geocoder.getFromLocation(lat, lang, 1);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        if (addresses != null) {
                                            if (!addresses.isEmpty()) {
                                                cityName = addresses.get(0).getAddressLine(0);
                                                stateName = addresses.get(0).getAddressLine(1);
                                                countryName = addresses.get(0).getAddressLine(2);
                                                StringBuilder stringBuilder=new StringBuilder();
                                                if(cityName!=null){
                                                    stringBuilder.append(cityName+",");
                                                }else if(stateName!=null){
                                                    stringBuilder.append(stateName+",");
                                                }else if (countryName!=null){
                                                    stringBuilder.append(countryName);
                                                }

                                                addr1.setText(stringBuilder.toString());
                                            }
                                        }




                                    }
                                });
                            }
                        });




                        //Fill Adress from currunt
                        billingAddress = new BillingAddress();
                        customer = new Customer();
                        customer.setFirstName(response.body().getCustomers().get(0).getFirstName());
                        customer.setLastName(response.body().getCustomers().get(0).getLastName());
                        customer.setEmail(response.body().getCustomers().get(0).getEmail());
                        customer.setPhone("");
                        List<Integer> role_ids = new ArrayList<Integer>();
                        if(SaveSharedPreference.getCustomerInfo(getContext()).getCustomers().get(0).getCustomerRoleName().equals("Expert B")) {
                            role_ids.add(3);
                            role_ids.add(5);
                            role_ids.add(6);
                        }else {
                            role_ids.add(3);
                            role_ids.add(5);
                            role_ids.add(7);
                        }
                        customer.setRoleIds(role_ids);
                        billingAddress.setFirstName(response.body().getCustomers().get(0).getFirstName());
                        billingAddress.setLastName(response.body().getCustomers().get(0).getLastName());
                        billingAddress.setEmail(response.body().getCustomers().get(0).getEmail());
                        billingAddress.setEmail(response.body().getCustomers().get(0).getEmail());
                        billingAddress.setCountryId(69);
                        billingAddress.setStateProvinceId(40);
                        billingAddress.setCity(response.body().getCustomers().get(0).getBillingAddress().getCity());
                        billingAddress.setAddress1(response.body().getCustomers().get(0).getBillingAddress().getAddress1());
                        billingAddress.setAddress2(response.body().getCustomers().get(0).getBillingAddress().getAddress2());
                        billingAddress.setPhoneNumber(response.body().getCustomers().get(0).getBillingAddress().getPhoneNumber());
                        billingAddress.setZipPostalCode("10021");
                        customer.setVerificationcode("");

                    } else {
                        Toast.makeText(getContext(), "Null from Get Profile Info", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Connection Error from Get Profile Info " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

























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
                }else {


                    final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
                    mProgressDialog.show();


                    billingAddress.setAddress1(addr1.getText().toString());
                    billingAddress.setAddress2("");
                    billingAddress.setCity(city.getText().toString());
                    customer.setLatitude(lat+"");
                    customer.setLongtitude(lang+"");
                    customer.setBillingAddress(billingAddress);
                    customer.setVendorName(experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.UpdateExp.vendorName);

                    UpdateEpert updateEpert=new UpdateEpert();
                    updateEpert.setCustomer(customer);
                    Gson gson=new Gson();
                    Log.e("reg", gson.toJson(updateEpert));
                    Generator.createService(ExpertsApi.class).updateExpertInfo(updateEpert, SaveSharedPreference.getCustId(getContext())).enqueue(new Callback<UpdateExpertResponse>() {
                        @Override
                        public void onResponse(Call<UpdateExpertResponse> call, Response<UpdateExpertResponse> response) {
                            if (response.isSuccessful()) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response.body().getCustomers() != null) {
                                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Null from Update Expert Info", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<UpdateExpertResponse> call, Throwable t) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            Toast.makeText(getContext(), "Connection error from Update Expert Info " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });







                }




            }
        });

    }






    }

