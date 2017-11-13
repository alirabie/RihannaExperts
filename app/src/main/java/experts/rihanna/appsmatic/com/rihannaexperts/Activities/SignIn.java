package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class SignIn extends AppCompatActivity {

    private TextView forgetPassBtn, createNewAccount, login;
    private EditText emailInput,passwordInput;

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


                    startActivity(new Intent(SignIn.this, Home.class));
                    SignIn.this.finish();

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
