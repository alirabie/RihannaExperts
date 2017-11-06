package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.CustomFragmentPagerAdapter;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegAddressInfo;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegExperience;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegPersonalInfo;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegServices;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.UpdateAddressFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.UpdateExperincesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.UpdateInfoFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.UpdateServicesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class AccountMangeFrag extends Fragment {

    ViewPager p;
    PagerSlidingTabStrip tabsStrip;
    CustomFragmentPagerAdapter adapter;
    UpdateAddressFrag updateAddressFrag;
    UpdateExperincesFrag updateExperincesFrag;
    UpdateInfoFrag updateInfoFrag;
    UpdateServicesFrag updateServicesFrag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_mange, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        adapter = new CustomFragmentPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new UpdateInfoFrag(),getResources().getString(R.string.info));
        adapter.addFragment(new UpdateAddressFrag(), getResources().getString(R.string.address));
        adapter.addFragment(new UpdateExperincesFrag(), getResources().getString(R.string.exp));
        adapter.addFragment(new UpdateServicesFrag(), getResources().getString(R.string.services));

        p=(ViewPager)view.findViewById(R.id.viewpager_presentcards);
        tabsStrip = (PagerSlidingTabStrip)view.findViewById(R.id.update_expert_info_tabs);
        tabsStrip.setTextColor(Color.WHITE);

        p.setAdapter(adapter);
        tabsStrip.setViewPager(p);
        adapter.notifyDataSetChanged();




    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    MainFrag mainFrag = new MainFrag();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentcontener, mainFrag);
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                    //set title
                    Home.tittle.setText(getResources().getString(R.string.hometitle));
                    return true;
                }
                return false;
            }
        });


    }


}
