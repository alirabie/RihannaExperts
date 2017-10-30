package experts.rihanna.appsmatic.com.rihannaexperts.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import experts.rihanna.appsmatic.com.rihannaexperts.R;
import experts.rihanna.appsmatic.com.rihannaexperts.Utils;

public class RegPersonalInfo extends Fragment {

    private TextView next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next=(TextView)view.findViewById(R.id.next);



       //Go to next step Expert Address
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Check GPS status
                final LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("GPS OFF")
                            .setCancelable(false)
                            .setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //Turn On GPS
                                    Utils.turnLocationOn(getContext());

                                }
                            }).setIcon(android.R.drawable.alert_light_frame);
                    AlertDialog alert = builder.create();
                    alert.show();
                }


                //Go address info fragment
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                next.clearAnimation();
                next.setAnimation(anim);
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.register_fm_contanier, new RegAddressInfo());
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();


            }
        });




    }


    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }
}
