package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments;

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

import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.CertificatesAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Utils;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


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

        if(Utils.getExpertCertificates(getActivity(), SaveSharedPreference.getExpertId(getActivity()))!=null) {
            if (Utils.getExpertCertificates(getActivity(),SaveSharedPreference.getExpertId(getActivity())).getCertificates().isEmpty()) {
                emptyFlag.setVisibility(View.VISIBLE);
            } else {
                emptyFlag.setVisibility(View.INVISIBLE);
                certificateList.setAdapter(new CertificatesAdb(Utils.getExpertCertificates(getActivity(), SaveSharedPreference.getExpertId(getActivity())), getActivity(),UPDATE_MODE));
                certificateList.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

        }else {
            Toast.makeText(getActivity(), "Null from get certificates", Toast.LENGTH_SHORT).show();
        }




        //Add cert button action
        addCertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                addCertBtn.clearAnimation();
                addCertBtn.setAnimation(anim);
                Dialogs.fireAddCertDialog(getContext(), addCertBtn, Integer.parseInt(SaveSharedPreference.getExpertId(getActivity())),UPDATE_MODE);
                //Dialogs.fireUpdateCertDialog(getContext(),addCertBtn,1,2);

            }
        });

    }
}
