package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ManageOrdersFragments.OrdersFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class MainFrag extends Fragment {


    ImageView orderAdmin,workTimeAdmin,accountAdmin,saleAdmin;
    Animation anim;
    LinearLayout mainPanel;


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
        mainPanel=(LinearLayout)view.findViewById(R.id.main_contener);




        //Animate home buttons
        Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        mainPanel.clearAnimation();
        mainPanel.setAnimation(anim1);


        //Mange Orders Button Action
        orderAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                orderAdmin.clearAnimation();
                orderAdmin.setAnimation(anim1);

                OrdersFrag mainFrag=new OrdersFrag();
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, mainFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                Home.tittle.setText(getResources().getString(R.string.mangeorders));

            }
        });


        //Mange Work times Button Action
        workTimeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                workTimeAdmin.clearAnimation();
                workTimeAdmin.setAnimation(anim2);

                ScheduleMangeFrag scheduleMangeFrag =new ScheduleMangeFrag();
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, scheduleMangeFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                Home.tittle.setText(getResources().getString(R.string.datestable));

            }
        });


        //Mange Account Button Action
        accountAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim3 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                accountAdmin.clearAnimation();
                accountAdmin.setAnimation(anim3);

                AccountMangeFrag accountMange =new AccountMangeFrag();
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener,accountMange);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                Home.tittle.setText(getResources().getString(R.string.accountmange));

            }
        });


        //Mange Sale Button Action
        saleAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim4 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                saleAdmin.clearAnimation();
                saleAdmin.setAnimation(anim4);

                //if expert class B not allow to modify price
                if(SaveSharedPreference.getCustomerInfo(getContext()).getCustomers().get(0).getCustomerRoleName().equals("Expert B")){
                    final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(getContext());
                    dialogBuildercard
                            .withDuration(700)//def
                            .withEffect(Effectstype.Slidetop)
                            .withDialogColor(getResources().getColor(R.color.colorPrimary))
                            .withTitleColor(Color.BLACK)
                            .withTitle(getResources().getString(R.string.app_name))
                            .withMessage(getResources().getString(R.string.discountaccess))
                            .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                            .show();
                }else {
                    SaleMangeFrag saleMangeFrag=new SaleMangeFrag();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentcontener, saleMangeFrag);
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                    //set title
                    Home.tittle.setText(getResources().getString(R.string.mangeoffers));
                }


            }
        });







    }
}
