package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Splash;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class SettingsFrag extends Fragment {
    private BetterSpinner langSpinner;
    List<String> languas=new ArrayList<>();
    private TextView acceptBtn;
    private int langFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        languas.add(0, "عربي");
        languas.add(1, "English");
        langSpinner=(BetterSpinner)view.findViewById(R.id.lang_spinner);

        acceptBtn=(TextView)view.findViewById(R.id.accept_btn);



        //Lang selection
        ArrayAdapter<String> langListdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_expandable_list_item_1, languas);
        langSpinner.setAdapter(langListdapter);
        langSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Set Lang Flag
                switch (position) {
                    case 0:
                        langFlag = 1;
                        break;
                    case 1:
                        langFlag = 2;
                        break;

                }

            }
        });



        //Accept button action
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                acceptBtn.clearAnimation();
                acceptBtn.setAnimation(anim);
                //Save Lang Selection depended on lang flag
                switch (langFlag) {
                    case 0:
                        break;
                    case 1:
                        SaveSharedPreference.setLangId(getActivity().getApplicationContext(), "ar");
                        Home.changeLanguage(getContext(),"3",SaveSharedPreference.getCustId(getContext()));
                        break;
                    case 2:
                        SaveSharedPreference.setLangId(getActivity().getApplicationContext(), "en");
                        Home.changeLanguage(getContext(), "1", SaveSharedPreference.getCustId(getContext()));
                        break;
                }

                getActivity().finish();
                getContext().startActivity(new Intent(getContext(), Splash.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }

        });


    }





}
