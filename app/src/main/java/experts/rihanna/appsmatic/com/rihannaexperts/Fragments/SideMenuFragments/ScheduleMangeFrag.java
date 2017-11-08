package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.CustomFragmentPagerAdapter;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments.PreviewScheduleFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments.SetScheduleFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments.PracticalExFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments.UpdateCertificateFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.EventDecorator;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class ScheduleMangeFrag extends Fragment {

    ViewPager p;
    PagerSlidingTabStrip tabsStrip;
    CustomFragmentPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dates_table, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CustomFragmentPagerAdapter(getChildFragmentManager());


        adapter.addFragment(new PreviewScheduleFrag(),getResources().getString(R.string.showschadule));
        adapter.addFragment(new SetScheduleFrag(), getResources().getString(R.string.setschadule));


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
