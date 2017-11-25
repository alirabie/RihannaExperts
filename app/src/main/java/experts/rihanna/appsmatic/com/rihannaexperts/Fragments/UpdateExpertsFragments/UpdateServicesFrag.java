package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Get.CertificatesList;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.ExpertServices.ResExpertServices;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.ExpertServicesAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AccountMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments.UpdateCertificateFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Utils;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateServicesFrag extends Fragment {

    private TextView next,emptyFlag;
    private LinearLayout subscribe_btn;
    private RecyclerView servicesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_services, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyFlag=(TextView)view.findViewById(R.id.empty_services_flag_frag);
        emptyFlag.setVisibility(View.INVISIBLE);

        //Setup Expert Services List
        Generator.createService(ExpertsApi.class).getExpertServices(SaveSharedPreference.getExpertId(getContext())).enqueue(new Callback<ResExpertServices>() {
            @Override
            public void onResponse(Call<ResExpertServices> call, Response<ResExpertServices> response) {
                if (response.isSuccessful()) {
                    if (response.body().getServices() != null) {
                        if (response.body().getServices().isEmpty()) {
                            emptyFlag.setVisibility(View.VISIBLE);
                        } else {
                            emptyFlag.setVisibility(View.INVISIBLE);
                            servicesList = (RecyclerView) view.findViewById(R.id.sevecices_frag_list);
                            servicesList.setAdapter(new ExpertServicesAdb(getContext(),response.body(),UpdateServicesFrag.this));
                            servicesList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                    }else {
                        Toast.makeText(getContext(),"Null From get Expert Services",Toast.LENGTH_SHORT).show();
                    }

                } else {

                    try {
                        Toast.makeText(getContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResExpertServices> call, Throwable t) {
                Toast.makeText(getContext(),"Connection Error From get Expert Services "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        //Subscribe Btn Action
        subscribe_btn=(LinearLayout)view.findViewById(R.id.subscribe_btn);
        subscribe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                subscribe_btn.clearAnimation();
                subscribe_btn.setAnimation(anim);
                //Inputs Service id / Is A / View / Context / Fragment
                Dialogs.fireSubscribeNewServiceDialog(getActivity(), subscribe_btn, SaveSharedPreference.getExpertId(getContext()), true, UpdateServicesFrag.this);
            }
        });







    }


}
