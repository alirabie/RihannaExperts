package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.MangeOrders.Order;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.ExpertOrdersAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegPersonalInfo;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersFrag extends Fragment {

    private RecyclerView ordersList;
    private TextView emptyFlag;
    private final String SOURCE="main_orders_list";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyFlag=(TextView)view.findViewById(R.id.empty_orders_flag_frag);
        emptyFlag.setVisibility(View.INVISIBLE);


        //Get Orders List from Server

        //Loading Dialog
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        mProgressDialog.show();
        Generator.createService(ExpertsApi.class).getExpertOrders("53").enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    if(response.body()!=null){
                        if (response.body().isEmpty()){
                            emptyFlag.setVisibility(View.VISIBLE);
                        }else {
                            emptyFlag.setVisibility(View.INVISIBLE);
                            ordersList=(RecyclerView)view.findViewById(R.id.orders_frag_list);
                            ordersList.setAdapter(new ExpertOrdersAdb(response.body(),SOURCE,getContext()));
                            ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                    }else {
                        Toast.makeText(getContext(),"Null From Orders List",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    try {
                        Toast.makeText(getContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(getContext(),"Connection error From Orders List"+t.getMessage(),Toast.LENGTH_SHORT).show();
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
                    MainFrag mainFrag=new MainFrag();
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
