package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AccountMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Utils;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class UpdateServicesFrag extends Fragment {

    private TextView next;
    private LinearLayout subscribe_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_services, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribe_btn=(LinearLayout)view.findViewById(R.id.subscribe_btn);
        subscribe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                subscribe_btn.clearAnimation();
                subscribe_btn.setAnimation(anim);
                Dialogs.fireSubscribeNewServiceDialog(getActivity(),subscribe_btn,"20");
            }
        });







    }


}
