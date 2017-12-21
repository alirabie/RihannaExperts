package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Districts.Districts;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.States.ResStates;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegCertificates;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments.ExpertTimesFarg;
import experts.rihanna.appsmatic.com.rihannaexperts.GPS.GPSTracker;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateOutdoorServicesFrag extends Fragment {


    private TextView next,emptyFlag;
    private TextView addAddress_btn;
    private BetterSpinner cities,nabourhods;

    private static List<String>statesIds;
    private static List<String>statesNames;
    private static List<String> districtsIds;
    private static List<String> districtsNames;
    private static final String SAUDI_ID="69";


    private String stateKey;
    private String statusid;

    private String nabourhodkey;
    private String nabourhodId;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_outdoor_services, container, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addAddress_btn=(TextView)view.findViewById(R.id.add_address_btn);

        cities =(BetterSpinner)view.findViewById(R.id.city_spinner);
        nabourhods=(BetterSpinner)view.findViewById(R.id.nabourhod_spinner);
        cities.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item));
        nabourhods.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item));



        //Setup Spinners
        Generator.createService(ExpertsApi.class).getStates(SAUDI_ID+"").enqueue(new Callback<ResStates>() {
            @Override
            public void onResponse(Call<ResStates> call, Response<ResStates> response) {
                if (response.isSuccessful()) {
                    statesNames = new ArrayList<String>();
                    statesIds = new ArrayList<String>();
                    //fill names and ids to spinner list from response
                    for (int i = 0; i < response.body().getStates().size(); i++) {
                        statesNames.add(response.body().getStates().get(i).getName());
                        statesIds.add(response.body().getStates().get(i).getId());
                    }
                    cities.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, statesNames));
                    cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            statusid = statesIds.get(position);
                            Generator.createService(ExpertsApi.class).getDestrics("Saudi Arabia", statesNames.get(position)).enqueue(new Callback<Districts>() {
                                @Override
                                public void onResponse(Call<Districts> call, Response<Districts> response) {
                                    if (response.isSuccessful()) {
                                        districtsNames = new ArrayList<String>();
                                        districtsIds = new ArrayList<String>();
                                        //fill names and ids to spinner list from response
                                        for (int i = 0; i < response.body().getDistricts().size(); i++) {
                                            districtsNames.add(response.body().getDistricts().get(i).getName());
                                            districtsIds.add(response.body().getDistricts().get(i).getId());
                                        }

                                        nabourhods.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, districtsNames));
                                        nabourhods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                nabourhodId = districtsIds.get(position);
                                            }
                                        });


                                    }
                                }

                                @Override
                                public void onFailure(Call<Districts> call, Throwable t) {

                                }
                            });


                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<ResStates> call, Throwable t) {

            }
        });




        //Add Outdoor Address Frag
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout6, new OutdoorAdressesFrag());
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.commit();
















        addAddress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                addAddress_btn.clearAnimation();
                addAddress_btn.setAnimation(anim);

                Toast.makeText(getContext(), "StateId : " + statusid + " districtId : " + nabourhodId, Toast.LENGTH_SHORT).show();


                //Invoke Add outdoor address method


                //Reload Fragment
                cities.setText("");
                nabourhods.setText("");

                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout6, new OutdoorAdressesFrag());
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();

            }
        });
    }


}
