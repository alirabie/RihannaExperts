package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegAddressInfo;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AccountMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.R;


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
        next=(TextView)view.findViewById(R.id.next_btn);


        //Go to next step Expert Address
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                next.clearAnimation();
                next.setAnimation(anim);

                //Inputs Validations
                Pattern pPhone= Pattern.compile("\\(?([0-9]{4})\\)?([ .-]?)([0-9]{4})\\2([0-9]{4})");
                Matcher mPhone=pPhone.matcher(phoneNum.getText().toString());
                Pattern p = Pattern.compile("^(.+)@(.+)$");
                Matcher m = p.matcher(eMail.getText().toString());
                if(fName.getText().toString().isEmpty()){
                    fName.setError(getResources().getString(R.string.fnameerr));
                }else if (lName.getText().toString().isEmpty()){
                    lName.setError(getResources().getString(R.string.lnameerr));
                }else if(eMail.getText().toString().isEmpty()){
                    eMail.setError(getResources().getString(R.string.emailerr));
                }else if(phoneNum.getText().toString().isEmpty()){
                    phoneNum.setError(getResources().getString(R.string.phonenumerr));
                }else if(password.getText().toString().isEmpty()){
                    password.setError(getResources().getString(R.string.passworderr));
                }else if(rePassword.getText().toString().isEmpty()){
                    rePassword.setError(getResources().getString(R.string.repasserr));
                }else if(activationCode.getText().toString().isEmpty()){
                    activationCode.setError(getResources().getString(R.string.activcodeerr));
                }else if(!password.getText().toString().equals(rePassword.getText().toString())){
                    password.setError(getResources().getString(R.string.passwordnotmatch));
                    rePassword.setError(getResources().getString(R.string.passwordnotmatch));
                }else if(!mPhone.matches()){
                    phoneNum.setError(getResources().getString(R.string.phonenotmatcherr));
                }else if(!m.matches()){
                    eMail.setError(getResources().getString(R.string.emailnotmatcherr));
                }else {


                    //Send Data

                    Toast.makeText(getActivity(), "Good Validations", Toast.LENGTH_SHORT).show();



                }
            }


        });

    }

}
