package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
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

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.ExpertExpertise;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.Expertise;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.PostExperinces;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.ResPost;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.SignUp;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegExperience extends Fragment {

    private TextView next;
    private BetterSpinner yearsCount;
    private List<String> years;
    private TextView skip;
    private EditText aboutExpert;
    String expeirincesYears="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experience, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next=(TextView)view.findViewById(R.id.next_btn);
        skip=(TextView)view.findViewById(R.id.skip_btn);
        aboutExpert=(EditText)view.findViewById(R.id.about_expert_input);


        years=new ArrayList<>();
        for(int i=1;i<=30;i++){
            years.add(i+"");
        }
        yearsCount=(BetterSpinner)view.findViewById(R.id.exper_years_count);
        yearsCount.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, years));
        yearsCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expeirincesYears=years.get(position);
                Log.e("dccccccccc",expeirincesYears);
            }
        });


        //Go to next step Expert Experiences and certificates
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                next.clearAnimation();
                next.setAnimation(anim);


                //Input Validations
                if(aboutExpert.getText().toString().isEmpty()){
                    aboutExpert.setError(getResources().getString(R.string.aboutexperterrr));
                }else if (yearsCount.getText().toString().isEmpty()){
                    yearsCount.setError(getResources().getString(R.string.experyearscounterr));
                }else {

                    ExpertExpertise expertExpertise=new ExpertExpertise();
                    Expertise expertise=new Expertise();
                    PostExperinces postExperinces =new PostExperinces();
                    List<Expertise>expertises=new ArrayList<Expertise>();
                    expertise.setAboutExpert(aboutExpert.getText().toString());
                    expertise.setYearsOfExperience(Integer.parseInt(expeirincesYears));
                    expertise.setExpertId(Integer.parseInt(SignUp.expertId));
                    expertises.add(expertise);
                    expertExpertise.setExpertId(Integer.parseInt(SignUp.expertId));
                    expertExpertise.setExpertise(expertises);
                    postExperinces.setExpertExpertise(expertExpertise);

                    //Loading Dialog
                    final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                    mProgressDialog.show();

                    Generator.createService(ExpertsApi.class).addExperinces(postExperinces).enqueue(new Callback<ResPost>() {
                        @Override
                        public void onResponse(Call<ResPost> call, Response<ResPost> response) {
                            if (response.isSuccessful()) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response.body().getStatus().equals("ok")) {
                                    Toast.makeText(getContext(),response.body().getStatus()+getResources().getString(R.string.sucsess),Toast.LENGTH_SHORT).show();
                                    SignUp.experincesdone=1;
                                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.register_fm_contanier, new RegCertificates());
                                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                    fragmentTransaction.commit();
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
        });











        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if skip this step
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                skip.clearAnimation();
                skip.setAnimation(anim);
                SignUp.experincesdone=0;
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.register_fm_contanier, new RegCertificates());
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
                    if(SignUp.addressdone==0) {
                        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                        next.clearAnimation();
                        next.setAnimation(anim);
                        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.register_fm_contanier, new RegAddressInfo());
                        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                        fragmentTransaction.commit();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
