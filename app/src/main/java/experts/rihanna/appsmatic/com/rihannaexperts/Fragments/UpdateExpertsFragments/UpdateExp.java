package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.CustomFragmentPagerAdapter;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments.UpdateOutdoorServicesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments.PracticalExFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments.UpdateCertificateFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class UpdateExp extends Fragment {

    ViewPager p;
    PagerSlidingTabStrip tabsStrip;
    CustomFragmentPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_exp, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CustomFragmentPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PracticalExFrag(),getResources().getString(R.string.practexp));
        adapter.addFragment(new UpdateCertificateFrag(), getResources().getString(R.string.certificates));


        p=(ViewPager)view.findViewById(R.id.viewpager_presentcards3);
        tabsStrip = (PagerSlidingTabStrip)view.findViewById(R.id.update_expert_info_tabs);
        tabsStrip.setTextColor(Color.WHITE);

        p.setAdapter(adapter);
        tabsStrip.setViewPager(p);
        adapter.notifyDataSetChanged();
    }
}
