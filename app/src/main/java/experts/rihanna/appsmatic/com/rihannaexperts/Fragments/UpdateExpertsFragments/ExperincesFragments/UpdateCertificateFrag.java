package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Get.CertificatesList;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.UpdateCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.CertificatesAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Utils;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateCertificateFrag extends Fragment {

    private LinearLayout addCertBtn;
    private RecyclerView certificateList;
    private LinearLayout emptyFlag;
    final int UPDATE_MODE=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_certificate, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addCertBtn=(LinearLayout)view.findViewById(R.id.updte_add_cert_btn);
        certificateList=(RecyclerView)view.findViewById(R.id.update_cert_list);
        emptyFlag=(LinearLayout)view.findViewById(R.id.click_add_cert_update_flag);
        emptyFlag.setVisibility(View.VISIBLE);

        //Loading Dialog
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        mProgressDialog.show();
        //Get Data from server
        Generator.createService(ExpertsApi.class).getExpertCertificates(SaveSharedPreference.getExpertId(getActivity())).enqueue(new Callback<CertificatesList>() {
            @Override
            public void onResponse(Call<CertificatesList> call, Response<CertificatesList> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getCertificates() != null) {
                        if (response.body().getCertificates().isEmpty()) {
                            emptyFlag.setVisibility(View.VISIBLE);
                        } else {
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            mLayoutManager.setReverseLayout(true);
                            mLayoutManager.setStackFromEnd(true);
                            emptyFlag.setVisibility(View.INVISIBLE);
                            certificateList.setAdapter(new CertificatesAdb(response.body(), getActivity(),UpdateCertificateFrag.this, UPDATE_MODE));
                            certificateList.setLayoutManager(mLayoutManager);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Null from get certificates", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        Toast.makeText(getActivity(), "Not success from get certificates " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CertificatesList> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Toast.makeText(getActivity(), "Connection error from get certificates " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });





        emptyFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                emptyFlag.clearAnimation();
                emptyFlag.setAnimation(anim);
                Dialogs.fireAddCertDialog(getContext(), addCertBtn, Integer.parseInt(SaveSharedPreference.getExpertId(getActivity())), UPDATE_MODE, UpdateCertificateFrag.this);
            }
        });

        //Add cert button action
        addCertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                addCertBtn.clearAnimation();
                addCertBtn.setAnimation(anim);
                Dialogs.fireAddCertDialog(getContext(), addCertBtn, Integer.parseInt(SaveSharedPreference.getExpertId(getActivity())),UPDATE_MODE, UpdateCertificateFrag.this);
                //Dialogs.fireUpdateCertDialog(getContext(),addCertBtn,1,2);

            }
        });

    }
}
