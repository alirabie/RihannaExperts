package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import experts.rihanna.appsmatic.com.rihannaexperts.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class UpdateCertificateFrag extends Fragment {

    private LinearLayout addCertBtn;

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
        //Add cert button action
        addCertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                addCertBtn.clearAnimation();
                addCertBtn.setAnimation(anim);

                Dialogs.fireAddCertDialog(getContext(), addCertBtn, 1);
                //Dialogs.fireUpdateCertDialog(getContext(),addCertBtn,1,2);

            }
        });

    }
}
