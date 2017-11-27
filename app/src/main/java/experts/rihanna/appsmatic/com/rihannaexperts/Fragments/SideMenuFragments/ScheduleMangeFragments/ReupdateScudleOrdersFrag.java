package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class ReupdateScudleOrdersFrag extends Fragment {

    private final String SOURCE="preview_schadule_orders";

    ImageView busyFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reupdate_scudle_orders, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        busyFlag=(ImageView)view.findViewById(R.id.order_day_status_flag);


        //check language busy off
        if(SaveSharedPreference.getLangId(getContext()).equals("ar")){
            busyFlag.setImageResource(R.drawable.busyoff_ar);
        }else {
            busyFlag.setImageResource(R.drawable.busyoff_en);
        }



/*
        //check language busy on
        if(SaveSharedPreference.getLangId(getContext()).equals("ar")){
            busyFlag.setImageResource(R.drawable.busyon_ar);
        }else {
            busyFlag.setImageResource(R.drawable.busyon_en);
        }

*/





    }
}
