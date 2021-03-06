package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.SignIn;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.SignUp;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class RegPersonalInfo extends Fragment {

    private TextView next;
    //private TextView getActivationCodeBtn;
    private EditText fName;
    private EditText lName;
    private EditText name;
    private EditText eMail;
    private EditText phoneNum;
    private EditText password;
    private EditText rePassword;
    //private EditText activationCode;
   // private LinearLayout mobActivationConener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setup items
        fName=(EditText)view.findViewById(R.id.reg_input_fname);
        lName=(EditText)view.findViewById(R.id.reg_input_lname);
        eMail=(EditText)view.findViewById(R.id.reg_input_email);
        name=(EditText)view.findViewById(R.id.req_input_activity_name);
        phoneNum=(EditText)view.findViewById(R.id.reg_input_phone_num);
        password=(EditText)view.findViewById(R.id.reg_input_password);
        rePassword=(EditText)view.findViewById(R.id.reg_input_re_password);
        //activationCode=(EditText)view.findViewById(R.id.ver_code_input);
       // getActivationCodeBtn=(TextView)view.findViewById(R.id.send_ver_code_btn);
        //mobActivationConener=(LinearLayout)view.findViewById(R.id.linearLayout17);
        next=(TextView)view.findViewById(R.id.next_btn);

        //Hide Phone Verification
       //mobActivationConener.setVisibility(View.INVISIBLE);













       //Go to next step Expert Address
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                next.clearAnimation();
                next.setAnimation(anim);
                //Inputs Validations
                Pattern pPhone = Pattern.compile("^(009665|9665|\\+9665|05|5)([0-9]{8})$");
                Matcher mPhone = pPhone.matcher(phoneNum.getText().toString());
                Pattern p = Pattern.compile("^(.+)@(.+)$");
                Matcher m = p.matcher(eMail.getText().toString());
                if(fName.getText().toString().isEmpty()){
                    fName.setError(getResources().getString(R.string.fnameerr));
                }else if (lName.getText().toString().isEmpty()){
                    lName.setError(getResources().getString(R.string.lnameerr));
                }else if(name.getText().toString().isEmpty()){
                    name.setError(getResources().getString(R.string.nameerr));
                }else if(eMail.getText().toString().isEmpty()){
                    eMail.setError(getResources().getString(R.string.emailerr));
                }else if(phoneNum.getText().toString().isEmpty()){
                    phoneNum.setError(getResources().getString(R.string.phonenumerr));
                }else if(password.getText().toString().isEmpty()){
                    password.setError(getResources().getString(R.string.passworderr));
                }else if(rePassword.getText().toString().isEmpty()){
                    rePassword.setError(getResources().getString(R.string.repasserr));
                    /*
                }else if(activationCode.getText().toString().isEmpty()){
                    activationCode.setError(getResources().getString(R.string.activcodeerr));
                    */
                }else if(!password.getText().toString().equals(rePassword.getText().toString())){
                    password.setError(getResources().getString(R.string.passwordnotmatch));
                    rePassword.setError(getResources().getString(R.string.passwordnotmatch));
                }else if(!mPhone.matches()){
                    phoneNum.setError(getResources().getString(R.string.phonenotmatcherr));
                }else if(!m.matches()){
                    eMail.setError(getResources().getString(R.string.emailnotmatcherr));
                }else {

                    //Send Data to next frag
                    SignUp.expertFname = fName.getText().toString();
                    SignUp.expertLname = lName.getText().toString();
                    SignUp.expertEmail = eMail.getText().toString();
                    SignUp.nickname=name.getText().toString();
                    SignUp.expertPhoneNum ="966"+phoneNum.getText().toString().substring(phoneNum.getText().toString().indexOf("5"));
                    SignUp.password=password.getText().toString();
                    //Log.e("phone","966"+phoneNum.getText().toString().substring(phoneNum.getText().toString().indexOf("5")));
                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.register_fm_contanier, new RegAddressInfo());
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                }
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
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }
}
