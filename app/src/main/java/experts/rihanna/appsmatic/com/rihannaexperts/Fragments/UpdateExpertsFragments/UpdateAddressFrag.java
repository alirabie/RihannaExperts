package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.CustomFragmentPagerAdapter;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegExperience;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AccountMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments.UpdateExpertAddressFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments.UpdateOutdoorServicesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.GPS.GPSTracker;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class UpdateAddressFrag extends Fragment {



    ViewPager p;
    PagerSlidingTabStrip tabsStrip;
    CustomFragmentPagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_address, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CustomFragmentPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new UpdateExpertAddressFrag(),getResources().getString(R.string.expaddress));
        adapter.addFragment(new UpdateOutdoorServicesFrag(), getResources().getString(R.string.outdoraddress));


        p=(ViewPager)view.findViewById(R.id.viewpager_presentcards);
        tabsStrip = (PagerSlidingTabStrip)view.findViewById(R.id.update_expert_info_tabs);
        tabsStrip.setTextColor(Color.WHITE);

        p.setAdapter(adapter);
        tabsStrip.setViewPager(p);
        adapter.notifyDataSetChanged();

    }



}
