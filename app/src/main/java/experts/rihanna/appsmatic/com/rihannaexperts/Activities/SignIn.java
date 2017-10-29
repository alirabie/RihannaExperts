package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class SignIn extends AppCompatActivity {

    private TextView forgetPassBtn, createNewAccount, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
//*

    }
}
