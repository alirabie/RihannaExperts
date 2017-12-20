package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.messages.internal.Update;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Delete.ResDelete;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.DELETE.DeleteExp;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.GET.GetExperinces;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.ExpertExpertise;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.Expertise;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.PostExperinces;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.ResPost;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.PUT.Experience;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.PUT.UpdateExp;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.SignUp;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegCertificates;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PracticalExFrag extends Fragment {

    private TextView next,clr;
    private BetterSpinner yearsCount;
    private List<String> years;
    private EditText aboutExpert;
    String flag="";
    String expeirincesYears="";
    int expId=0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_experinces, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        next=(TextView)view.findViewById(R.id.next_btn);
        clr=(TextView)view.findViewById(R.id.clr_exp);
        aboutExpert=(EditText)view.findViewById(R.id.about_expert_input);



        years=new ArrayList<>();
        for(int i=1;i<=30;i++){
            years.add(i+"");
        }
        yearsCount=(BetterSpinner)view.findViewById(R.id.exper_years_count);
        yearsCount.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,years));


        //Get Experiences Data
        Generator.createService(ExpertsApi.class).getExperinces(SaveSharedPreference.getExpertId(getContext())).enqueue(new Callback<GetExperinces>() {
            @Override
            public void onResponse(Call<GetExperinces> call, Response<GetExperinces> response) {
                if (response.isSuccessful()) {
                    if(response.body()!=null) {
                        if(!response.body().getExperiences().isEmpty()) {
                            flag="update";
                            aboutExpert.setText(response.body().getExperiences().get(0).getAboutExpert());
                            yearsCount.setText(response.body().getExperiences().get(0).getYearsOfExperience() + "");
                            expId = response.body().getExperiences().get(0).getId();
                        }else {
                            flag="add";
                        }
                    }
                }else {
                    try {
                        Toast.makeText(getContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetExperinces> call, Throwable t) {
                Toast.makeText(getContext(),"Connection error from get Exp"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });





        yearsCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expeirincesYears = years.get(position);
                Log.e("dccccccccc", expeirincesYears);
            }
        });





        //Save
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                next.clearAnimation();
                next.setAnimation(anim);
                //Input Validations
                if (aboutExpert.getText().toString().isEmpty()) {
                    aboutExpert.setError(getResources().getString(R.string.aboutexperterrr));
                } else if (yearsCount.getText().toString().isEmpty()) {
                    yearsCount.setError(getResources().getString(R.string.experyearscounterr));
                } else {

                    //Loading Dialog
                    final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                    mProgressDialog.show();


                    //Check operation mode add or update
                    if (flag.equals("update")) {
                        Experience experience = new Experience();
                        UpdateExp updateExp = new UpdateExp();
                        experience.setExpertId(Integer.parseInt(SaveSharedPreference.getExpertId(getContext())));
                        experience.setYearsOfExperience(Integer.parseInt(yearsCount.getText().toString()));
                        experience.setAboutExpert(aboutExpert.getText().toString());
                        experience.setId(expId);
                        updateExp.setExperience(experience);
                        Generator.createService(ExpertsApi.class).updateExp(updateExp).enqueue(new Callback<UpdateExp>() {
                            @Override
                            public void onResponse(Call<UpdateExp> call, Response<UpdateExp> response) {
                                if (response.isSuccessful()) {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    if (response.body() != null) {

                                        Toast.makeText(getContext(), getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
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
                            public void onFailure(Call<UpdateExp> call, Throwable t) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                Toast.makeText(getContext(), "Connection Error from Update Exp " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if(flag.equals("add")) {
                        ExpertExpertise expertExpertise=new ExpertExpertise();
                        Expertise expertise=new Expertise();
                        PostExperinces postExperinces =new PostExperinces();
                        List<Expertise>expertises=new ArrayList<Expertise>();
                        expertise.setAboutExpert(aboutExpert.getText().toString());
                        expertise.setYearsOfExperience(Integer.parseInt(expeirincesYears));
                        expertise.setExpertId(Integer.parseInt(SaveSharedPreference.getExpertId(getContext())));
                        expertises.add(expertise);
                        expertExpertise.setExpertId(Integer.parseInt(SaveSharedPreference.getExpertId(getContext())));
                        expertExpertise.setExpertise(expertises);
                        postExperinces.setExpertExpertise(expertExpertise);
                        Generator.createService(ExpertsApi.class).addExperinces(postExperinces).enqueue(new Callback<ResPost>() {
                            @Override
                            public void onResponse(Call<ResPost> call, Response<ResPost> response) {
                                if (response.isSuccessful()) {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    if (response.body().getStatus().equals("ok")) {
                                        Toast.makeText(getContext(),response.body().getStatus()+getResources().getString(R.string.sucsess),Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(),response.body().getErrorMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    try {
                                        Toast.makeText(getActivity(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResPost> call, Throwable t) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                Toast.makeText(getActivity(),"Error from experiences API"+t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }

            }
        });


        //Clear Experiences Data
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                clr.clearAnimation();
                clr.setAnimation(anim);

                //Loading Dialog
                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                mProgressDialog.show();
                if(flag.equals("add")){
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                   return;
                }else if(flag.equals("update")){
                    try {
                        experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.DELETE.Experience experience = new experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.DELETE.Experience();
                        DeleteExp deleteExp = new DeleteExp();
                        experience.setId(expId);
                        experience.setAboutExpert(aboutExpert.getText().toString());
                        experience.setYearsOfExperience(Integer.parseInt(yearsCount.getText().toString()));
                        experience.setExpertId(Integer.parseInt(SaveSharedPreference.getExpertId(getContext())));
                        deleteExp.setExperience(experience);
                    Generator.createService(ExpertsApi.class).deleteExp(deleteExp).enqueue(new Callback<ResDelete>() {
                        @Override
                        public void onResponse(Call<ResDelete> call, Response<ResDelete> response) {
                            if (response.isSuccessful()) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response.body().getStatus().equals("ok")) {
                                    aboutExpert.setText("");
                                    yearsCount.setText("");
                                    Toast.makeText(getContext(), response.body().getStatus() + getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                try {
                                    Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResDelete> call, Throwable t) {

                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    }catch (Exception e){
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                    }


                }





            }
        });




    }

}
