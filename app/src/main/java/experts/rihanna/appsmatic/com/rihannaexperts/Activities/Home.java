package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.Order;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrdersResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.ExpertOrdersAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AboutAppFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.AccountMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ManageOrdersFragments.OrdersFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.SaleMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.SettingsFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity  {

    private LinearLayout sideMenuButtons;
    public static Typeface face;
    DrawerLayout drawer;
    private ImageView homeSide,profileSide,latestOffersSide,schaduleTable,dayTimes,ordersListSide,settingsSide,abutAppSide, exitLoginSide;
    public static TextView tittle;
    public static int ordersCount=0;
    public static NotificationManager manager;
    static int notId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setOrdersCount(Home.this);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        tittle=(TextView)findViewById(R.id.filtertitle);


        Log.e("xx",SaveSharedPreference.getCustId(getApplicationContext())+" "+SaveSharedPreference.getExpertId(getApplicationContext()));







        //set main fragment on app start
        MainFrag mainFrag=new MainFrag();
        android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentcontener, mainFrag);
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.commit();
        //set title
        tittle.setText(getResources().getString(R.string.hometitle));



        //Open Orders from Notifications
        if (getIntent().getStringExtra("target")!= null) {
            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (getIntent().getStringExtra("target").equals("orders")) {
                OrdersFrag ordersFrag=new OrdersFrag();
                android.support.v4.app.FragmentManager fragmentManager3 = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.fragmentcontener, ordersFrag);
                fragmentTransaction3.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction3.commit();
                //set title
                tittle.setText(getResources().getString(R.string.mangeorders));
            }

        }










        //Setup Side Menu Items
        homeSide=(ImageView)findViewById(R.id.home_side_button);
        profileSide=(ImageView)findViewById(R.id.profile_side_button);
        latestOffersSide=(ImageView)findViewById(R.id.offers_side_button);
        schaduleTable=(ImageView)findViewById(R.id.schadule_table_side_button);
        dayTimes=(ImageView)findViewById(R.id.day_times_side_button);
        ordersListSide=(ImageView)findViewById(R.id.orders_side_button);
        settingsSide=(ImageView)findViewById(R.id.settings_side_buttons);
        abutAppSide=(ImageView)findViewById(R.id.aboutapp_side_buttons);
        exitLoginSide=(ImageView)findViewById(R.id.logout_side_button);




        //check language
        if(SaveSharedPreference.getLangId(this).equals("ar")){
            homeSide.setImageResource(R.drawable.home);
            profileSide.setImageResource(R.drawable.profile);
            latestOffersSide.setImageResource(R.drawable.sale);
            schaduleTable.setImageResource(R.drawable.table);
            dayTimes.setImageResource(R.drawable.date);
            ordersListSide.setImageResource(R.drawable.orders);
            settingsSide.setImageResource(R.drawable.settings);
            abutAppSide.setImageResource(R.drawable.about);
            exitLoginSide.setImageResource(R.drawable.logout);
        }else {
            homeSide.setImageResource(R.drawable.home_en);
            profileSide.setImageResource(R.drawable.profile_en);
            latestOffersSide.setImageResource(R.drawable.sale_en);
            ordersListSide.setImageResource(R.drawable.orders_en);
            schaduleTable.setImageResource(R.drawable.table_en);
            dayTimes.setImageResource(R.drawable.date_en);
            settingsSide.setImageResource(R.drawable.settings_en);
            abutAppSide.setImageResource(R.drawable.aboutus_en);
            exitLoginSide.setImageResource(R.drawable.signout_en);
        }



        //Action Side menu buttons ********************************** :

        //Home button
        homeSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                homeSide.clearAnimation();
                homeSide.setAnimation(anim);
                MainFrag mainFrag=new MainFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, mainFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                tittle.setText(getResources().getString(R.string.hometitle));
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //Profile button
        profileSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                profileSide.clearAnimation();
                profileSide.setAnimation(anim);
                AccountMangeFrag accountMangeFrag=new AccountMangeFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, accountMangeFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                tittle.setText(getResources().getString(R.string.accountmange));
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //Offers button
        latestOffersSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                latestOffersSide.clearAnimation();
                latestOffersSide.setAnimation(anim);

                //if expert class B not allow to modify price
                if(SaveSharedPreference.getCustomerInfo(getApplicationContext()).getCustomers().get(0).getCustomerRoleName().equals("Expert B")){
                    final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(Home.this);
                    dialogBuildercard
                            .withDuration(700)//def
                            .withEffect(Effectstype.Slidetop)
                            .withDialogColor(getResources().getColor(R.color.colorPrimary))
                            .withTitleColor(Color.BLACK)
                            .withTitle(getResources().getString(R.string.app_name))
                            .withMessage(getResources().getString(R.string.discountaccess))
                            .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                            .show();
                }else {
                    SaleMangeFrag saleMangeFrag=new SaleMangeFrag();
                    android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentcontener,saleMangeFrag);
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                    //set title
                    tittle.setText(getResources().getString(R.string.mangeoffers));
                    drawer.closeDrawer(GravityCompat.START);
                }


            }
        });


        //Schedule Table
        schaduleTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                schaduleTable.clearAnimation();
                schaduleTable.setAnimation(anim);
                ScheduleMangeFrag scheduleMangeFrag =new ScheduleMangeFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, scheduleMangeFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                tittle.setText(getResources().getString(R.string.datestable));
                drawer.closeDrawer(GravityCompat.START);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        //Day times
        dayTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                dayTimes.clearAnimation();
                dayTimes.setAnimation(anim);
                //Get Today Date
                long date = System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                String dateString = format.format(date);
                OrdersFrag ordersFrag=new OrdersFrag();
                Bundle bundle =new Bundle();
                bundle.putString("today", dateString);
                ordersFrag.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, ordersFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                tittle.setText(getResources().getString(R.string.daydates));
                drawer.closeDrawer(GravityCompat.START);

            }
        });





        //Orders List
        ordersListSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                ordersListSide.clearAnimation();
                ordersListSide.setAnimation(anim);
                OrdersFrag ordersFrag=new OrdersFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, ordersFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                tittle.setText(getResources().getString(R.string.mangeorders));
                drawer.closeDrawer(GravityCompat.START);
            }
        });



        //settings button
        settingsSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                settingsSide.clearAnimation();
                settingsSide.setAnimation(anim);
                SettingsFrag settingsFrag =new SettingsFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener,settingsFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                tittle.setText(getResources().getString(R.string.settings));

                drawer.closeDrawer(GravityCompat.START);
            }
        });


        //About App button
        abutAppSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                abutAppSide.clearAnimation();
                abutAppSide.setAnimation(anim);
                AboutAppFrag aboutAppFrag =new AboutAppFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, aboutAppFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();
                //set title
                tittle.setText(getResources().getString(R.string.aboutapp));
                drawer.closeDrawer(GravityCompat.START);
            }
        });




        //Exit/login button
        exitLoginSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                exitLoginSide.clearAnimation();
                exitLoginSide.setAnimation(anim);
                SaveSharedPreference.setExpertId(Home.this, "","");
                SaveSharedPreference.setCustomerInfo(Home.this, null);
                SaveSharedPreference.setUserName(Home.this,"","");
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Home.this, SignIn.class));
                Home.this.finish();

            }
        });





        //Check every 1/2 min for orders
        final android.os.Handler mHandler = new android.os.Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep(30000);

                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub

                                Generator.createService(ExpertsApi.class).getExpertOrders(SaveSharedPreference.getExpertId(getBaseContext())).enqueue(new Callback<OrdersResponse>() {
                                    @Override
                                    public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body() != null) {
                                                if(response.body().getOrders().size()>ordersCount){
                                                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                                    NotificationCompat.Builder builder =
                                                            (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                                                    .setSmallIcon(R.drawable.rihanna_logo)
                                                                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                                                                    .setSound(alarmSound)
                                                                    .setContentTitle(getResources().getString(R.string.app_name))
                                                                    .setAutoCancel(true)
                                                                    .setContentText(getResources().getString(R.string.notifiction));
                                                    Intent notificationIntent = new Intent(getApplicationContext(),Home.class).putExtra("target","orders");
                                                    TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(getApplicationContext());
                                                    taskStackBuilder.addParentStack(Home.class);
                                                    taskStackBuilder.addNextIntent(notificationIntent);
                                                    PendingIntent contentIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                                                    builder.setContentIntent(contentIntent);
                                                    manager=(NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                                                    manager.notify(notId,builder.build());
                                                    notId++;
                                                    ordersCount=response.body().getOrders().size();
                                                }
                                            } else {
                                                Toast.makeText(getBaseContext(), "Null From Orders List", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            try {
                                                Toast.makeText(getBaseContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrdersResponse> call, Throwable t) {
                                        Toast.makeText(getBaseContext(), "Connection error From Orders List" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();
























        //Side menu toggle

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.sidem_icon);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                    //Animate side icons
                    sideMenuButtons = (LinearLayout) findViewById(R.id.side_buttons_container);
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
                    sideMenuButtons.clearAnimation();
                    sideMenuButtons.setAnimation(anim);
                }
            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(Home.this);
            dialogBuilder
                    .withTitle(getResources().getString(R.string.app_name))
                    .withDialogColor(R.color.colorPrimary)
                    .withTitleColor("#FFFFFF")
                    .withIcon(getResources().getDrawable(R.drawable.logo))
                    .withDuration(700)                                          //def
                    .withEffect(Effectstype.RotateBottom)
                    .withMessage(getResources().getString(R.string.areyousurexiteapp))
                    .withButton1Text(getResources().getString(R.string.yes))
                    .withButton2Text(getResources().getString(R.string.no))
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                            Home.this.finish();
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            OrdersFrag ordersFrag=new OrdersFrag();
            android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentcontener, ordersFrag);
            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            fragmentTransaction.commit();
            //set title
            tittle.setText(getResources().getString(R.string.mangeorders));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public static void setOrdersCount(final Context context){
        Generator.createService(ExpertsApi.class).getExpertOrders(SaveSharedPreference.getExpertId(context)).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ordersCount=response.body().getOrders().size();
                    } else {
                        Toast.makeText(context, "Null From Orders List", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(context, "Connection error From Orders List" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }








}
