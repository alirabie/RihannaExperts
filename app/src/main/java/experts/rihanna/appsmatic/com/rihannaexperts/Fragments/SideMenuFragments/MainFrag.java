package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class MainFrag extends Fragment {


    ImageView orderAdmin,workTimeAdmin,accountAdmin,saleAdmin;
    Animation anim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //setup items
        orderAdmin=(ImageView)view.findViewById(R.id.order_mange_btn);
        workTimeAdmin=(ImageView)view.findViewById(R.id.work_table_btn);
        accountAdmin=(ImageView)view.findViewById(R.id.my_account_main_btn);
        saleAdmin=(ImageView)view.findViewById(R.id.sale_mange_btn);



        //Mange Orders Button Action
        orderAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                orderAdmin.clearAnimation();
                orderAdmin.setAnimation(anim1);

            }
        });


        //Mange Work times Button Action
        workTimeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                workTimeAdmin.clearAnimation();
                workTimeAdmin.setAnimation(anim2);

            }
        });


        //Mange Account Button Action
        accountAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim3 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                accountAdmin.clearAnimation();
                accountAdmin.setAnimation(anim3);

            }
        });


        //Mange Sale Button Action
        saleAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim4 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                saleAdmin.clearAnimation();
                saleAdmin.setAnimation(anim4);

            }
        });







    }
}
