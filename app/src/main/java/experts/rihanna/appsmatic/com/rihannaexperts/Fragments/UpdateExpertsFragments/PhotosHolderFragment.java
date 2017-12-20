package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

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

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.IndoorServicesCntroal.IndoorGetRes;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Get.GetExpertPhotos;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.ExpertPhotosAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotosHolderFragment extends Fragment {
    private RecyclerView photosList;
    private TextView emptyFlag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos_holder, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photosList = (RecyclerView) view.findViewById(R.id.image_list);
        emptyFlag = (TextView) view.findViewById(R.id.empty_pics_flag);
        emptyFlag.setVisibility(View.INVISIBLE);

        //Get Photos
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
        mProgressDialog.show();
        Generator.createService(ExpertsApi.class).getExpertPhotos(SaveSharedPreference.getExpertId(getContext())).enqueue(new Callback<GetExpertPhotos>() {
            @Override
            public void onResponse(Call<GetExpertPhotos> call, Response<GetExpertPhotos> response) {
                if(response.isSuccessful()){
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    if(response.body().getCustomers()!=null){
                        if(response.body().getCustomers().get(0).getImages().isEmpty()){
                            emptyFlag.setVisibility(View.VISIBLE);
                        }else {
                            emptyFlag.setVisibility(View.INVISIBLE);
                            photosList.setAdapter(new ExpertPhotosAdb(getContext(), response.body()));
                            photosList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }

                    }else {
                        Toast.makeText(getContext(),"Null From Get Expert Photos Api",Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetExpertPhotos> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Connection Error From Get Expert Photos Api "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });










    }
}
