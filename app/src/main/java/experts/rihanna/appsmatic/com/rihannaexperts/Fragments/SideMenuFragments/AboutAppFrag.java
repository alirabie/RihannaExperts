package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ContactUs.Contactuspost;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ContactUs.MessegeSentRes;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutAppFrag extends Fragment {

    private TextView txt1,txt2,txt3;
    private Button bt1,bt2,bt3;
    private ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3;
    private ImageView copyright,twBtn,instaBtn,fbBtn,gmBtn;
    private EditText messageInput;
    private TextView sendBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_app, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Social media buttons
        twBtn=(ImageView)view.findViewById(R.id.tw_btn);
        instaBtn=(ImageView)view.findViewById(R.id.insta_btn);
        gmBtn=(ImageView)view.findViewById(R.id.gm_btn);


        messageInput=(EditText)view.findViewById(R.id.complaints_input_body);
        sendBtn=(TextView)view.findViewById(R.id.complaints_send_btn_about_us);

        copyright=(ImageView)view.findViewById(R.id.copyright_tv);
        bt1=(Button)view.findViewById(R.id.expandableButton1);
        bt2=(Button)view.findViewById(R.id.expandableButton2);
        bt3=(Button)view.findViewById(R.id.expandableButton3);
        expandableLayout1 = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout1);
        expandableLayout1.collapse();
        expandableLayout2 = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout2);
        expandableLayout2.collapse();
        expandableLayout3 = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout3);
        expandableLayout3.collapse();



        //check language
        if(SaveSharedPreference.getLangId(getContext()).equals("ar")){
            copyright.setImageResource(R.drawable.copyright);
        }else{
            copyright.setImageResource(R.drawable.copyright_en);
        }




        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                txt1=(TextView)expandableLayout1.findViewById(R.id.text1);
                txt1.setText(getResources().getString(R.string.termsheder) +" : "+
                        "\n"+"- "+getResources().getString(R.string.role1)+
                        "\n"+"- "+getResources().getString(R.string.role2)+
                        "\n"+"- "+getResources().getString(R.string.role3));


                expandableLayout1.toggle(); // toggle expand and collapse
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                txt2=(TextView)expandableLayout2.findViewById(R.id.text2);
                txt2.setText("");
                expandableLayout2.toggle(); // toggle expand and collapse

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                expandableLayout3.toggle(); // toggle expand and collapse

            }
        });







        twBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                twBtn.clearAnimation();
                twBtn.setAnimation(anim);
                Intent intent = null;
                try {
                    // get the Twitter app if possible
                    getContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/RIHANNA_APP"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/RIHANNA_APP"));
                }

                startActivity(intent);
            }
        });




        instaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                instaBtn.clearAnimation();
                instaBtn.setAnimation(anim);
                Uri uri = Uri.parse("https://www.instagram.com/rihanna.app/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/rihanna.app/")));
                }
            }
        });


        gmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                gmBtn.clearAnimation();
                gmBtn.setAnimation(anim);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.google.android.apps.plus",
                            "com.google.android.apps.plus.phone.UrlGatewayActivity");
                    intent.putExtra("customAppUri","test");
                    startActivity(intent);
                } catch(ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/test/posts")));
                }
            }
        });





        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                sendBtn.clearAnimation();
                sendBtn.setAnimation(anim);
                if(messageInput.getText().toString().isEmpty()){
                    messageInput.setError("!");
                }else {
                    Contactuspost contactuspost=new Contactuspost();
                    contactuspost.setSubject("Experts App");
                    contactuspost.setEnquiry(messageInput.getText().toString());
                    contactuspost.setEmail("");
                    contactuspost.setFullName("");
                    Generator.createService(ExpertsApi.class).contactUs(contactuspost).enqueue(new Callback<MessegeSentRes>() {
                        @Override
                        public void onResponse(Call<MessegeSentRes> call, Response<MessegeSentRes> response) {
                            if(response.isSuccessful()){
                                if(response.body().getMessage().equals("ok")){
                                    Toast.makeText(getContext(), getResources().getString(R.string.messagesent), Toast.LENGTH_SHORT).show();
                                    messageInput.setText("");
                                }else {
                                    Toast.makeText(getContext(),response.body().getErrorMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                try {
                                    Toast.makeText(getContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<MessegeSentRes> call, Throwable t) {
                            Toast.makeText(getContext(),"Connection error from contact us API "+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

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
                    MainFrag mainFrag = new MainFrag();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentcontener, mainFrag);
                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    fragmentTransaction.commit();
                    //set title
                    Home.tittle.setText(getResources().getString(R.string.hometitle));
                    return true;
                }
                return false;
            }
        });






    }
}
