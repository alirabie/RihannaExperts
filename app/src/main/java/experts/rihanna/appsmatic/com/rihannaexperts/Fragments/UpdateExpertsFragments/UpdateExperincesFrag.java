package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegServices;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AccountMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class UpdateExperincesFrag extends Fragment {

    private TextView next;
    private BetterSpinner yearsCount;
    private List<String> years;
    private LinearLayout addCertBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_experinces, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        next=(TextView)view.findViewById(R.id.next_btn);
        addCertBtn=(LinearLayout)view.findViewById(R.id.add_cert_btn);

        years=new ArrayList<>();
        for(int i=1;i<=10;i++){
            years.add(i+"");
        }
        yearsCount=(BetterSpinner)view.findViewById(R.id.exper_years_count);
        yearsCount.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,years));


        //Save
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




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
