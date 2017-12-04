package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.SchdulesResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.SchdulesAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpertTimesFarg extends Fragment {


    RecyclerView timesList;
    TextView emptyFlag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expert_times_farg, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyFlag=(TextView)view.findViewById(R.id.no_times_flag);
        emptyFlag.setVisibility(View.INVISIBLE);



        //get data from server
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
        mProgressDialog.show();
        Generator.createService(ExpertsApi.class).getExpertSchadules(SaveSharedPreference.getExpertId(getContext())).enqueue(new Callback<SchdulesResponse>() {
            @Override
            public void onResponse(Call<SchdulesResponse> call, Response<SchdulesResponse> response) {
                if(response.isSuccessful()){
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    if(response.body().getDeliveryschedules()!=null){
                        if(response.body().getDeliveryschedules().isEmpty()){
                            emptyFlag.setVisibility(View.VISIBLE);
                        }else {
                            emptyFlag.setVisibility(View.INVISIBLE);
                            timesList=(RecyclerView)view.findViewById(R.id.times_list);
                            timesList.setAdapter(new SchdulesAdb(response.body(),ExpertTimesFarg.this,getContext()));
                            timesList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }

                    }else {
                        Toast.makeText(getContext(),"Null from times API",Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SchdulesResponse> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Connection Error from times API "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
