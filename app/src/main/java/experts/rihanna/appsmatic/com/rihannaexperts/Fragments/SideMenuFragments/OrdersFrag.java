package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrdersResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.ExpertOrdersAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
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
        emptyFlag = (TextView) view.findViewById(R.id.empty_orders_flag_frag);
        emptyFlag.setVisibility(View.INVISIBLE);


        //Get Orders List from Server with test id 53

        if (getArguments() == null) {


            //Loading Dialog
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
            mProgressDialog.show();

            Generator.createService(ExpertsApi.class).getExpertOrders(SaveSharedPreference.getExpertId(getContext())).enqueue(new Callback<OrdersResponse>() {
                @Override
                public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                    if (response.isSuccessful()) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        if (response.body() != null) {
                            if (response.body().getOrders().isEmpty()) {
                                emptyFlag.setVisibility(View.VISIBLE);
                            } else {
                                emptyFlag.setVisibility(View.INVISIBLE);
                                ordersList = (RecyclerView) view.findViewById(R.id.orders_frag_list);
                                ordersList.setAdapter(new ExpertOrdersAdb(response.body(), getContext(), SOURCE));
                                ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
                            }
                        } else {
                            Toast.makeText(getContext(), "Null From Orders List", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        try {
                            Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrdersResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Connection error From Orders List" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else {

            //Loading Dialog
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
            mProgressDialog.show();
            Generator.createService(ExpertsApi.class).getFilterdOrders(SaveSharedPreference.getExpertId(getContext()), getArguments().getString("today")).enqueue(new Callback<OrdersResponse>() {
                @Override
                public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                    if (response.isSuccessful()) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        if (response.body() != null) {
                            if (response.body().getOrders().isEmpty()) {
                                emptyFlag.setVisibility(View.VISIBLE);
                            } else {
                                emptyFlag.setVisibility(View.INVISIBLE);
                                ordersList = (RecyclerView) view.findViewById(R.id.orders_frag_list);
                                ordersList.setAdapter(new ExpertOrdersAdb(response.body(), getContext(), SOURCE));
                                ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
                            }
                        } else {
                            Toast.makeText(getContext(), "Null From Orders List", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        try {
                            Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrdersResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Connection error From Orders List" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


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
