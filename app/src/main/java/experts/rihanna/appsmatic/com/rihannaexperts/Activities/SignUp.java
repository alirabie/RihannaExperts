package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegPersonalInfo;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Utils;

public class SignUp extends AppCompatActivity {

    private TextView haveAcoountBtn;
    public static  String expertId="0";
    public static String expertFname="A";
    public static String expertLname="b";
    public static String expertPhoneNum="0";
    public static String expertEmail="";
    public static String password="";
    public static int infodone=0;
    public static int addressdone=0;
    public static int experincesdone=0;
    public static int certificatesdone=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }



        android.support.v4.app.FragmentManager fragmentManager = (SignUp.this).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.register_fm_contanier,new RegPersonalInfo());
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.commit();

        haveAcoountBtn=(TextView)findViewById(R.id.have_account_btn);
        haveAcoountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(SignUp.this, R.anim.alpha);
                haveAcoountBtn.clearAnimation();
                haveAcoountBtn.setAnimation(anim);
                startActivity(new Intent(SignUp.this, SignIn.class));
                SignUp.this.finish();
            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
