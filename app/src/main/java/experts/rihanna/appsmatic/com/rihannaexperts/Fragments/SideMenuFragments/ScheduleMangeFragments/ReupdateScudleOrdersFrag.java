package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrdersResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.ExpertOrdersAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReupdateScudleOrdersFrag extends Fragment {


    private final String SOURCE="preview_schadule_orders";
    private RecyclerView ordersList;
    private TextView emptyFlag;
    private ImageView busyFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reupdate_scudle_orders, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyFlag=(TextView)view.findViewById(R.id.no_orders_day_flag);
        emptyFlag.setVisibility(View.INVISIBLE);
        busyFlag=(ImageView)view.findViewById(R.id.order_day_status_flag);


        //check language busy off
        if(SaveSharedPreference.getLangId(getContext()).equals("ar")){
            busyFlag.setImageResource(R.drawable.busyoff_ar);
        }else {
            busyFlag.setImageResource(R.drawable.busyoff_en);
        }


        //Get Orders List from Server with test id 53
        //Loading Dialog
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        mProgressDialog.show();
        Generator.createService(ExpertsApi.class).getFilterdOrders(SaveSharedPreference.getExpertId(getContext()), getArguments().get("date").toString()).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if(response.isSuccessful()){
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    if(response.body()!=null){
                        if (response.body().getOrders().isEmpty()){
                            //Empty Flag
                            emptyFlag.setVisibility(View.VISIBLE);
                            //Buizzy Flag
                            if(SaveSharedPreference.getLangId(getContext()).equals("ar")){
                                busyFlag.setImageResource(R.drawable.busyoff_ar);
                            }else {
                                busyFlag.setImageResource(R.drawable.busyoff_en);
                            }

                        }else {

                            //Empty Flag
                            emptyFlag.setVisibility(View.INVISIBLE);
                            //Buizzy Flag
                            if(SaveSharedPreference.getLangId(getContext()).equals("ar")){
                                busyFlag.setImageResource(R.drawable.busyon_ar);
                            }else {
                                busyFlag.setImageResource(R.drawable.busyon_en);
                            }
                            ordersList=(RecyclerView)view.findViewById(R.id.order_day_list);
                            ordersList.setAdapter(new ExpertOrdersAdb(response.body(),getContext(),SOURCE));
                            ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                    }else {
                        Toast.makeText(getContext(), "Null From Orders List", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Connection error From Orders List"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });








    }
}
