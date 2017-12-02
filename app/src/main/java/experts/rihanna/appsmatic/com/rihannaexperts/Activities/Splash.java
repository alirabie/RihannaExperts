package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Utils;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setLang(R.layout.activity_splash);
        Window window = this.getWindow();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }



        //Check location permissions for Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }


        //Check GPS status
        final LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
            builder.setMessage("GPS OFF")
                    .setCancelable(false)
                    .setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Turn On GPS
                            Utils.turnLocationOn(Splash.this);

                        }
                    }).setIcon(android.R.drawable.alert_light_frame);
            AlertDialog alert = builder.create();
            alert.show();
        }

        //Splash Duration
        Thread timer = new Thread() {
            public void run() {
                try {

                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    //Check Login Saved Status
                    if(SaveSharedPreference.getUserName(Splash.this).equals("")&&SaveSharedPreference.getUserPassword(Splash.this).equals("")) {
                        Intent i = new Intent(Splash.this, SignIn.class);
                        startActivity(i);
                        Splash.this.finish();
                    }else {

                        final HashMap loginData = new HashMap();
                        loginData.put("Email", SaveSharedPreference.getUserName(Splash.this));
                        loginData.put("Password", SaveSharedPreference.getUserPassword(Splash.this));
                        Generator.createService(ExpertsApi.class).login(loginData).enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getCustomers() != null) {

                                        SaveSharedPreference.setExpertId(Splash.this, response.body().getCustomers().get(0).getId() + "");
                                        SaveSharedPreference.setCustomerInfo(Splash.this, response.body());
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsucsess), Toast.LENGTH_LONG).show();
                                        Log.e("Done : ", response.body().getCustomers().get(0).getId() + "");
                                        startActivity(new Intent(Splash.this, Home.class));
                                        Splash.this.finish();

                                    } else if (response.body().getErrors().getAccount() != null) {
                                        //Show Error
                                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(Splash.this);
                                        dialogBuilder
                                                .withTitle(getResources().getString(R.string.app_name))
                                                .withDialogColor(R.color.colorPrimary)
                                                .withTitleColor("#FFFFFF")
                                                .withDuration(700)                                          //def
                                                .withEffect(Effectstype.RotateBottom)
                                                .withMessage(response.body().getErrors().getAccount() + "")
                                                .show();
                                        startActivity(new Intent(Splash.this, SignIn.class));
                                        Splash.this.finish();
                                    }


                                } else {
                                    try {
                                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), "Connection Error From Login APi Login Failed " + t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                }
            }
        };
        timer.start();
    }





    // Change language method
    public  void setLang(int layout){
        String languageToLoad = SaveSharedPreference.getLangId(this);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(layout);
    }




}
