package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
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

import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.DatesTableFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.DayDatesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.OrdersFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.SaleMangeFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class Home extends AppCompatActivity  {

    private LinearLayout sideMenuButtons;
    public static Typeface face;
    DrawerLayout drawer;
    private ImageView homeSide,profileSide,latestOffersSide,schaduleTable,dayTimes,ordersListSide,settingsSide,abutAppSide, exitLoginSide;
    public static TextView tittle;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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



        //set main fragment on app start
        MainFrag mainFrag=new MainFrag();
        android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentcontener, mainFrag);
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.commit();
        //set title
        tittle.setText(getResources().getString(R.string.hometitle));


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

/*
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

            if(SaveSharedPreference.getCustomerInfo(Home.this)==null){
                exitLoginSide.setImageResource(R.drawable.signin);
            }else {
                exitLoginSide.setImageResource(R.drawable.logout);
            }

        }else{
            homeSide.setImageResource(R.drawable.home_en);
            profileSide.setImageResource(R.drawable.profile_en);
            latestOffersSide.setImageResource(R.drawable.sale_en);
            ordersListSide.setImageResource(R.drawable.orders_en);
            schaduleTable.setImageResource(R.drawable.table_en);
            dayTimes.setImageResource(R.drawable.date_en);
            settingsSide.setImageResource(R.drawable.settings_en);
            abutAppSide.setImageResource(R.drawable.aboutus_en);

            if(SaveSharedPreference.getCustomerInfo(Home.this)==null){
                exitLoginSide.setImageResource(R.drawable.signin_en);
            }else {
                exitLoginSide.setImageResource(R.drawable.signout_en);
            }

        }
*/



        //Action Side menu buttons :

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
        });


        //Schedule Table
        schaduleTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.alpha);
                schaduleTable.clearAnimation();
                schaduleTable.setAnimation(anim);

                DatesTableFrag datesTableFrag=new DatesTableFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener,datesTableFrag);
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
                DayDatesFrag dayDatesFrag=new DayDatesFrag();
                android.support.v4.app.FragmentManager fragmentManager = (Home.this).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener,dayDatesFrag);
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





                    drawer.closeDrawer(GravityCompat.START);
            }
        });





        //Side menu

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
            super.onBackPressed();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}