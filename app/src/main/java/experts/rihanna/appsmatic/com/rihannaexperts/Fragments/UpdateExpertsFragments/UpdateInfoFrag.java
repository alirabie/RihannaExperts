package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExpertRegistartaion.Customer;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.BillingAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.UpdateEpert;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.Response.UpdateExpertResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegAddressInfo;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AccountMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateInfoFrag extends Fragment {
    private TextView next;
    private TextView getActivationCodeBtn;
    private EditText fName;
    private EditText lName;
    private EditText eMail;
    private EditText phoneNum;
    private EditText password;
    private EditText rePassword;
    private EditText activationCode;
    private LinearLayout mobActivationConener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        next=(TextView)view.findViewById(R.id.next_btn);



        //Setup items
        fName=(EditText)view.findViewById(R.id.reg_input_fname);
        lName=(EditText)view.findViewById(R.id.reg_input_lname);
        eMail=(EditText)view.findViewById(R.id.reg_input_email);
        phoneNum=(EditText)view.findViewById(R.id.reg_input_phone_num);
        password=(EditText)view.findViewById(R.id.reg_input_password);
        rePassword=(EditText)view.findViewById(R.id.reg_input_re_password);
        activationCode=(EditText)view.findViewById(R.id.ver_code_input);
        getActivationCodeBtn=(TextView)view.findViewById(R.id.send_ver_code_btn);
        mobActivationConener=(LinearLayout)view.findViewById(R.id.linearLayout17);
        next=(TextView)view.findViewById(R.id.next_btn);

        //Hide Phone Verification
        mobActivationConener.setVisibility(View.INVISIBLE);


        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
        mProgressDialog.show();
        //Get Profile Info
        Generator.createService(ExpertsApi.class).getProfile(SaveSharedPreference.getExpertId(getContext())).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    if (response.body().getCustomers() != null) {
                        fName.setText(response.body().getCustomers().get(0).getFirstName());
                        fName.setTextColor(getResources().getColor(R.color.colorAccent));
                        lName.setText(response.body().getCustomers().get(0).getLastName());
                        lName.setTextColor(getResources().getColor(R.color.colorAccent));
                        eMail.setText(response.body().getCustomers().get(0).getEmail());
                        eMail.setTextColor(getResources().getColor(R.color.colorAccent));
                        phoneNum.setText(response.body().getCustomers().get(0).getBillingAddress().getPhoneNumber() + "");
                        phoneNum.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        Toast.makeText(getContext(),"Null from Get Profile Info",Toast.LENGTH_SHORT).show();
                    }
                } else {
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Connection Error from Get Profile Info "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });




        //Update info
            next.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View v) {


                    Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                    next.clearAnimation();
                    next.setAnimation(anim);

                    //Inputs Validations
                    Pattern pPhone = Pattern.compile("\\(?([0-9]{4})\\)?([ .-]?)([0-9]{4})\\2([0-9]{4})");
                    Matcher mPhone = pPhone.matcher(phoneNum.getText().toString());
                    Pattern p = Pattern.compile("^(.+)@(.+)$");
                    Matcher m = p.matcher(eMail.getText().toString());
                    if (fName.getText().toString().isEmpty()) {
                        fName.setError(getResources().getString(R.string.fnameerr));
                    } else if (lName.getText().toString().isEmpty()) {
                        lName.setError(getResources().getString(R.string.lnameerr));
                    } else if (eMail.getText().toString().isEmpty()) {
                        eMail.setError(getResources().getString(R.string.emailerr));
                    } else if (phoneNum.getText().toString().isEmpty()) {
                        phoneNum.setError(getResources().getString(R.string.phonenumerr));
                    } else if (password.getText().toString().isEmpty()) {
                        password.setError(getResources().getString(R.string.passworderr));
                    } else if (rePassword.getText().toString().isEmpty()) {
                        rePassword.setError(getResources().getString(R.string.repasserr));
                    } /*else if (activationCode.getText().toString().isEmpty()) {
                        activationCode.setError(getResources().getString(R.string.activcodeerr));
                    }*/ else if (!password.getText().toString().equals(rePassword.getText().toString())) {
                        password.setError(getResources().getString(R.string.passwordnotmatch));
                        rePassword.setError(getResources().getString(R.string.passwordnotmatch));
                    } else if (!mPhone.matches()) {
                        phoneNum.setError(getResources().getString(R.string.phonenotmatcherr));
                    } else if (!m.matches()) {
                        eMail.setError(getResources().getString(R.string.emailnotmatcherr));
                    } else {


                        //Send Data
                        BillingAddress billingAddress=new BillingAddress();
                        experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.Customer customer=new experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT.Customer();
                        UpdateEpert updateEpert=new UpdateEpert();
                        customer.setFirstName(fName.getText().toString());
                        customer.setLastName(lName.getText().toString());
                        customer.setEmail(eMail.getText().toString());
                        customer.setPhone("");
                        customer.setPassword(password.getText().toString());
                        List<Integer>role_ids=new ArrayList<Integer>();
                        role_ids.add(3);
                        customer.setRoleIds(role_ids);
                        billingAddress.setFirstName(fName.getText().toString());
                        billingAddress.setLastName(lName.getText().toString());
                        billingAddress.setEmail(eMail.getText().toString());
                        billingAddress.setPhoneNumber(phoneNum.getText().toString());
                        billingAddress.setAddress1("21 West 52nd Street");
                        billingAddress.setCompany("Appsmatic Ltd");
                        billingAddress.setCountryId(1);
                        billingAddress.setStateProvinceId(40);
                        billingAddress.setCity("New York");
                        billingAddress.setZipPostalCode("10021");
                        customer.setBillingAddress(billingAddress);
                        customer.setVerificationcode("");
                        updateEpert.setCustomer(customer);

                        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
                        mProgressDialog.show();
                        Generator.createService(ExpertsApi.class).updateExpertInfo(updateEpert,SaveSharedPreference.getExpertId(getContext())).enqueue(new Callback<UpdateExpertResponse>() {
                            @Override
                            public void onResponse(Call<UpdateExpertResponse> call, Response<UpdateExpertResponse> response) {
                                if (response.isSuccessful()) {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    if (response.body().getCustomers() != null) {
                                        Toast.makeText(getContext(),getContext().getResources().getString(R.string.updated),Toast.LENGTH_SHORT).show();
                                        //Reload Fragment
                                        android.support.v4.app.FragmentManager fragmentManager2 = ((FragmentActivity) getContext()).getSupportFragmentManager();
                                        fragmentManager2.beginTransaction().detach(UpdateInfoFrag.this).attach(UpdateInfoFrag.this).commit();
                                    } else {
                                        Toast.makeText(getContext(),"Null from Update Expert Info",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
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
                            public void onFailure(Call<UpdateExpertResponse> call, Throwable t) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                Toast.makeText(getContext(),"Connection error from Update Expert Info "+t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });



                        Toast.makeText(getActivity(), "Good Validations", Toast.LENGTH_SHORT).show();


                    }
                }


            });

    }

}
