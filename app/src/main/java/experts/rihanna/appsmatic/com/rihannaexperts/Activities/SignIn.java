package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity {

    private TextView forgetPassBtn, createNewAccount, login;
    private EditText emailInput,passwordInput;
    private CheckBox saveLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        //Setup items
        forgetPassBtn = (TextView) findViewById(R.id.forgetpassbtn);
        createNewAccount = (TextView) findViewById(R.id.create_account_btn);
        emailInput=(EditText)findViewById(R.id.email_input_login);
        passwordInput=(EditText)findViewById(R.id.password_input_login);
        login = (TextView) findViewById(R.id.login_btn);
        saveLoginData=(CheckBox)findViewById(R.id.remeberlogincheck);























        //Create new account action button
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(SignIn.this, R.anim.alpha);
                createNewAccount.clearAnimation();
                createNewAccount.setAnimation(anim);
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(SignIn.this, R.anim.alpha);
                login.clearAnimation();
                login.setAnimation(anim);

                Pattern p = Pattern.compile("^(.+)@(.+)$");
                Matcher m = p.matcher(emailInput.getText().toString());

                if(emailInput.getText().toString().isEmpty()){
                    emailInput.setError(getResources().getString(R.string.emailerr));
                }else if(passwordInput.getText().toString().isEmpty()){
                    passwordInput.setError(getResources().getString(R.string.passworderr));
                }else if(!m.matches()){
                    emailInput.setError(getResources().getString(R.string.emailnotmatcherr));
                }else {
                    //send data to server
                          //Loading Dialog
                    final ProgressDialog mProgressDialog = new ProgressDialog(SignIn.this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(getApplicationContext().getResources().getString(R.string.loading));
                    mProgressDialog.show();
                    final HashMap loginData = new HashMap();
                    loginData.put("Email", emailInput.getText().toString() + "");
                    loginData.put("Password", passwordInput.getText().toString() + "");
                    Generator.createService(ExpertsApi.class).login(loginData).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response.body().getCustomers() != null) {

                                    //Save Login Data
                                    if (saveLoginData.isChecked()) {
                                        SaveSharedPreference.setUserName(SignIn.this, loginData.get("Email").toString() + "", loginData.get("Password").toString() + "");
                                    } else {
                                        SaveSharedPreference.setUserName(SignIn.this, "", "");
                                    }


                                    //save expert Id
                                    SaveSharedPreference.setExpertId(SignIn.this,response.body().getCustomers().get(0).getId()+"",response.body().getCustomers().get(0).getExpertId()+"");

                                    SaveSharedPreference.setCustomerInfo(SignIn.this, response.body());

                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsucsess), Toast.LENGTH_LONG).show();

                                    Log.e("Done : ", SaveSharedPreference.getExpertId(getApplicationContext()));
                                    startActivity(new Intent(SignIn.this, Home.class));
                                    SignIn.this.finish();
                                } else if(response.body().getErrors().getAccount()!=null){
                                    //Show Error
                                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(SignIn.this);
                                    dialogBuilder
                                            .withTitle(getResources().getString(R.string.app_name))
                                            .withDialogColor(R.color.colorPrimary)
                                            .withTitleColor("#FFFFFF")
                                            .withDuration(700)                                          //def
                                            .withEffect(Effectstype.RotateBottom)
                                            .withMessage(response.body().getErrors().getAccount()+ "")
                                            .show();
                                }


                            } else {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                try {
                                    Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Connection Error From Login APi Login Failed " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }







            }
        });

        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(SignIn.this, R.anim.alpha);
                forgetPassBtn.clearAnimation();
                forgetPassBtn.setAnimation(anim);
                startActivity(new Intent(SignIn.this,RecoverPassword.class));
            }
        });


    }
}
