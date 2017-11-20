package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.CertificatesAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Utils;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegCertificates extends Fragment {


    private LinearLayout addCertBtn;
    private RecyclerView certificateList;
    private TextView next;
    private TextView skip;
    private LinearLayout emptyFlag;
    String expertId="2";
    final int REGISTERATION_MODE=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg_certificates, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addCertBtn=(LinearLayout)view.findViewById(R.id.add_cert_button);
        certificateList=(RecyclerView)view.findViewById(R.id.certificates_list);
        next=(TextView)view.findViewById(R.id.next_btn);
        skip=(TextView)view.findViewById(R.id.skip_btn);
        emptyFlag=(LinearLayout)view.findViewById(R.id.click_to_add_cert_flag);
        emptyFlag.setVisibility(View.VISIBLE);




        //Loading Dialog
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        mProgressDialog.show();
        //Get Data from server
        Generator.createService(ExpertsApi.class).getExpertCertificates(expertId).enqueue(new Callback<CertificatesList>() {
            @Override
            public void onResponse(Call<CertificatesList> call, Response<CertificatesList> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getCertificates()!=null){
                        if(response.body().getCertificates().isEmpty()){
                            emptyFlag.setVisibility(View.VISIBLE);
                        }else {
                            emptyFlag.setVisibility(View.INVISIBLE);
                            certificateList.setAdapter(new CertificatesAdb(response.body(), getActivity(),RegCertificates.this,REGISTERATION_MODE));
                            certificateList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                    }else {
                        Toast.makeText(getActivity(),"Null from get certificates",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    try {
                        Toast.makeText(getActivity(),"Not success from get certificates "+response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CertificatesList> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Toast.makeText(getActivity(),"Connection error from get certificates "+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });




        //Add cert button action
        addCertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                addCertBtn.clearAnimation();
                addCertBtn.setAnimation(anim);
                Dialogs.fireAddCertDialog(getContext(), addCertBtn,Integer.parseInt(expertId),REGISTERATION_MODE,RegCertificates.this);
            }
        });








        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                next.clearAnimation();
                next.setAnimation(anim);

                //if data sent
                getActivity().finish();
            }
        });



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                skip.clearAnimation();
                skip.setAnimation(anim);

                //if skip this step
               getActivity().finish();

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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                    next.clearAnimation();
                    next.setAnimation(anim);
                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.register_fm_contanier, new RegExperience());
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });
    }
}
