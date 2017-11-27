package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class OrderInfoFrag extends Fragment {

    private TextView orderNumTv,dateTv,timeTv,customerName,customerPhone,customerAddress,emptyOrdersFlag,accept,decline;
    private LinearLayout outDoorFlag,orderConfirmedFlag,showOnmapBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Setup Items
        orderNumTv=(TextView)view.findViewById(R.id.order_info_num_and_category);
        dateTv=(TextView)view.findViewById(R.id.order_info_date);
        timeTv=(TextView)view.findViewById(R.id.order_info_time);
        customerName=(TextView)view.findViewById(R.id.order_info_cust_name);
        customerPhone=(TextView)view.findViewById(R.id.order_info_cust_phone);
        customerAddress=(TextView)view.findViewById(R.id.order_info_cust_address);
        emptyOrdersFlag=(TextView)view.findViewById(R.id.order_info_empty_list_flag);
        accept=(TextView)view.findViewById(R.id.accept_btn);
        decline=(TextView)view.findViewById(R.id.next_btn);
        showOnmapBtn=(LinearLayout)view.findViewById(R.id.order_info_show_on_map);

        Toast.makeText(getContext(),"Order Id "+getArguments().getInt("orderId"),Toast.LENGTH_SHORT).show();





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
                    OrdersFrag ordersFrag = new OrdersFrag();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentcontener, ordersFrag);
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                    //set title
                    Home.tittle.setText(getResources().getString(R.string.mangeorders));
                    return true;
                }
                return false;
            }
        });




    }

}
