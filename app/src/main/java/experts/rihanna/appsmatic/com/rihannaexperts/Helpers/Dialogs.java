package experts.rihanna.appsmatic.com.rihannaexperts.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Categories.ResCategory;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.AddCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.Certificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.ExpertCertifications;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.ResCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Delete.ResDelete;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.ResUpdate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.UpdateCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Districts.Districts;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.OutdoorAddress.Set.Address;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.OutdoorAddress.Set.PostNewAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.OutdoorAddress.Set.SetNewAddressResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.Deleteschaduleres;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Get.ResService;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Subscribe.ExpertService;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Subscribe.SubscribeModel;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Subscribe.SubscribeResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.States.ResStates;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegCertificates;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments.OutdoorAdressesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.UpdateServicesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eng Ali on 10/31/2017.
 */
public class Dialogs {
    static String categoriyId="";
    static String year="";
    static String serviceId="";
    static String servicePrice="";
    static List<String>categoriesNames;
    static List<String>servicesNames;
    static List<String>categoriesIds;
    static List<String>servicesIds;
    static List<String>servicesPrice;


    private static List<String>statesIds;
    private static List<String>statesNames;
    private static List<String> districtsIds;
    private static List<String> districtsNames;
    private static final String SAUDI_ID="69";

   static String stateKey;
   static String statusid;
   static String nabourhodkey;
   static String nabourhodId;



    //Add certificate
    public static void fireAddCertDialog(final Context context,View view, final int exId, final int flag, final android.support.v4.app.Fragment fragment){

         final EditText certName;
         final EditText granter;
         final BetterSpinner certYear;
         final BetterSpinner spicialty;
         final TextView saveBtn;
         final ImageView close;



        //Initialize Done Dialog
        final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(context);
        dialogBuildercard
                .withDuration(700)//def
                .withEffect(Effectstype.Fall)
                .withDialogColor(Color.WHITE)
                .withTitleColor(Color.BLACK)
                .withTitle(context.getResources().getString(R.string.certificates))
                .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                .setCustomView(R.layout.add_cert_dialog, view.getContext())
                .show();

        dialogBuildercard.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });


        final List<Integer> years=new ArrayList<>();
        for(int i=1990;i<= Calendar.getInstance().get(Calendar.YEAR);i++){
            years.add(i);
        }



        //Setup Spinner Categories
        final List<String>categories=new ArrayList<>();
        final List<String>ids=new ArrayList<>();
        Generator.createService(ExpertsApi.class).getCategories().enqueue(new Callback<ResCategory>() {
            @Override
            public void onResponse(Call<ResCategory> call, Response<ResCategory> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCategories() != null) {
                        for (int i = 0; i < response.body().getCategories().size(); i++) {
                            categories.add(response.body().getCategories().get(i).getName());
                            ids.add(response.body().getCategories().get(i).getId());
                        }
                    } else {
                        Toast.makeText(context, "Null from categories Api", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        Toast.makeText(context, "Response Not Success from categories Api"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResCategory> call, Throwable t) {
                Toast.makeText(context, "Connection Error from categories Api" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        spicialty=(BetterSpinner)dialogBuildercard.findViewById(R.id.spicialty_input);
        spicialty.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, categories));
        spicialty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, ids.get(position), Toast.LENGTH_SHORT).show();
                categoriyId = ids.get(position);

            }
        });



        certName=(EditText)dialogBuildercard.findViewById(R.id.cert_name_input);
        granter=(EditText)dialogBuildercard.findViewById(R.id.granter_input);
        saveBtn=(TextView)dialogBuildercard.findViewById(R.id.save);
        close=(ImageView)dialogBuildercard.findViewById(R.id.close);
        certYear=(BetterSpinner)dialogBuildercard.findViewById(R.id.year_of_grant_input);
        certYear.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, years));
        certYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                year = years.get(position) + "";
            }
        });





        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                saveBtn.clearAnimation();
                saveBtn.setAnimation(anim);

                if(certName.getText().toString().isEmpty()){
                    certName.setError(context.getResources().getString(R.string.certnameerr));
                }else if (spicialty.getText().toString().isEmpty()){
                    spicialty.setError(context.getResources().getString(R.string.spiciltyerr));
                }else if (granter.getText().toString().isEmpty()){
                    granter.setError(context.getResources().getString(R.string.grantererr));
                }else if (certYear.getText().toString().isEmpty()){
                    certYear.setError(context.getResources().getString(R.string.certyearerr));
                }else {

                    //Loading Dialog
                    final ProgressDialog mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(context.getResources().getString(R.string.loading));
                    mProgressDialog.show();

                    AddCertificate addCertificate=new AddCertificate();
                    Certificate certificate=new Certificate();
                    ExpertCertifications expertCertifications=new ExpertCertifications();

                    certificate.setName(certName.getText().toString());
                    certificate.setAuthorizedBy(granter.getText().toString());
                    certificate.setServiceCategoryId(Integer.parseInt(categoriyId));
                    certificate.setYearAcquired(year);
                    certificate.setExpertId(exId);
                    List<Certificate> certificates=new ArrayList<Certificate>();
                    certificates.add(certificate);
                    expertCertifications.setExpertId(exId);

                    expertCertifications.setCertificates(certificates);
                    addCertificate.setExpertCertifications(expertCertifications);

                    //Add new certificate
                    Generator.createService(ExpertsApi.class).addCertificate(addCertificate).enqueue(new Callback<ResCertificate>() {
                        @Override
                        public void onResponse(Call<ResCertificate> call, Response<ResCertificate> response) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();

                            if(response.isSuccessful()){
                                if(response.body().getErrorMessage()!=null){
                                    Toast.makeText(context,context.getResources().getString(R.string.addcertsucsess)+response.body().getStatus(),Toast.LENGTH_SHORT).show();
                                   //in case of flag 0 that is mean is in register mode and sing up UI and in case of 1 that is mean in update mode update UI
                                   switch (flag){
                                       case 0:
                                           //refresh fragment in sign up mode
                                           android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                           android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                           fragmentTransaction.replace(R.id.register_fm_contanier, new RegCertificates());
                                           fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                           fragmentTransaction.commit();
                                           break;
                                       case 1:
                                           //refresh fragment in update mode

                                           android.support.v4.app.FragmentManager fragmentManager2 =((FragmentActivity) context).getSupportFragmentManager();
                                           /*
                                           android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                           fragmentTransaction2.replace(R.id.fragmentcontener, new UpdateExp());
                                           fragmentTransaction2.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                           fragmentTransaction2.commitNowAllowingStateLoss();
*/
                                           fragmentManager2.beginTransaction().detach(fragment).attach(fragment).commit();
                                           break;


                                   }

                                    //Close dialog
                                    dialogBuildercard.dismiss();

                                }else {
                                    Toast.makeText(context,response.body().getErrorMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                try {
                                    Toast.makeText(context,"Response not success from add certificates"+response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResCertificate> call, Throwable t) {

                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();

                            Toast.makeText(context,"Connection error from add certificate "+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }




            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                close.clearAnimation();
                close.setAnimation(anim);
                dialogBuildercard.dismiss();

            }
        });




    }


    //update certificate
    public static void fireUpdateCertDialog(final Context context,View view, final experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.Certificate certificate, final int flag, final android.support.v4.app.Fragment fragment){


        final EditText certName;
        final EditText granter;
        final BetterSpinner certYear;
        final BetterSpinner spicialty;
        final TextView saveBtn,delete;
        final ImageView close;



        //Initialize Done Dialog
        final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(context);
        dialogBuildercard
                .withDuration(700)//def
                .withEffect(Effectstype.Fall)
                .withDialogColor(Color.WHITE)
                .withTitleColor(Color.BLACK)
                .withTitle(context.getResources().getString(R.string.certificates))
                .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                .setCustomView(R.layout.update_cert_dialog, view.getContext())
                .show();

        dialogBuildercard.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        final List<Integer> years=new ArrayList<>();
        for(int i=1990;i<= Calendar.getInstance().get(Calendar.YEAR);i++){
            years.add(i);
        }
        certName=(EditText)dialogBuildercard.findViewById(R.id.cert_name_input);
        granter=(EditText)dialogBuildercard.findViewById(R.id.granter_input);
        saveBtn=(TextView)dialogBuildercard.findViewById(R.id.save2);
        delete=(TextView)dialogBuildercard.findViewById(R.id.next_btn);
        close=(ImageView)dialogBuildercard.findViewById(R.id.close);
        certYear=(BetterSpinner)dialogBuildercard.findViewById(R.id.year_of_grant_input);
        certYear.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, years));






        //Setup Spinner Categories
        final List<String>categories=new ArrayList<>();
        final List<String>ids=new ArrayList<>();
        Generator.createService(ExpertsApi.class).getCategories().enqueue(new Callback<ResCategory>() {
            @Override
            public void onResponse(Call<ResCategory> call, Response<ResCategory> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCategories() != null) {
                        for (int i = 0; i < response.body().getCategories().size(); i++) {
                            categories.add(response.body().getCategories().get(i).getName());
                            ids.add(response.body().getCategories().get(i).getId());
                        }
                    } else {
                        Toast.makeText(context, "Null from categories Api", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Response Not Success from categories Api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResCategory> call, Throwable t) {
                Toast.makeText(context, "Connection Error from categories Api" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        spicialty=(BetterSpinner)dialogBuildercard.findViewById(R.id.spicialty_input);
        spicialty.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, categories));

        //fill data
        certName.setText(certificate.getName() + "");
        certYear.setText(certificate.getYearAcquired() + "");
        granter.setText(certificate.getAuthorizedBy() + "");
        spicialty.setHint(certificate.getServiceCategoryName() + "");
        categoriyId=certificate.getServiceCategoryId()+"";




        spicialty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, ids.get(position), Toast.LENGTH_SHORT).show();
                categoriyId = ids.get(position);

            }
        });


        certYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                year = years.get(position) + "";
            }
        });






        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                saveBtn.clearAnimation();
                saveBtn.setAnimation(anim);
                if (certName.getText().toString().isEmpty()) {
                    certName.setError(context.getResources().getString(R.string.certnameerr));
                } else if (spicialty.getText().toString().isEmpty()) {
                    spicialty.setError(context.getResources().getString(R.string.spiciltyerr));
                } else if (granter.getText().toString().isEmpty()) {
                    granter.setError(context.getResources().getString(R.string.grantererr));
                } else if (certYear.getText().toString().isEmpty()) {
                    certYear.setError(context.getResources().getString(R.string.certyearerr));
                } else {

                    //Loading Dialog
                    final ProgressDialog mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(context.getResources().getString(R.string.loading));
                    mProgressDialog.show();
                    UpdateCertificate updateCertificate = new UpdateCertificate();
                    experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.Certificate updatedCert = new experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.Certificate();
                    updatedCert.setId(certificate.getId());
                    updatedCert.setName(certName.getText().toString());
                    updatedCert.setExpertId(certificate.getExpertId());
                    updatedCert.setYearAcquired(year);
                    updatedCert.setAuthorizedBy(granter.getText().toString());
                    updatedCert.setServiceCategoryId(Integer.parseInt(categoriyId));
                    updateCertificate.setCertificate(updatedCert);
                    Generator.createService(ExpertsApi.class).updateCertificate(updateCertificate).enqueue(new Callback<ResUpdate>() {
                        @Override
                        public void onResponse(Call<ResUpdate> call, Response<ResUpdate> response) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();

                            if (response.isSuccessful()) {
                                if (response.body().getCertificates() != null) {
                                    Toast.makeText(context, context.getResources().getString(R.string.certupdated), Toast.LENGTH_SHORT).show();

                                    //in case of flag 0 that is mean is in register mode and sing up UI and in case of 1 that is mean in update mode update UI
                                    switch (flag) {
                                        case 0:
                                            //refresh fragment in sign up mode
                                            android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.register_fm_contanier, new RegCertificates());
                                            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                            fragmentTransaction.commit();
                                            break;
                                        case 1:
                                            //refresh fragment in update mode
                                            android.support.v4.app.FragmentManager fragmentManager2 = ((FragmentActivity) context).getSupportFragmentManager();
                                            /*
                                            android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                            fragmentTransaction2.replace(R.id.fragmentcontener, new AccountMangeFrag());
                                            fragmentTransaction2.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                            fragmentTransaction2.commit();
                                            */
                                            fragmentManager2.beginTransaction().detach(fragment).attach(fragment).commit();
                                            break;
                                    }

                                    dialogBuildercard.dismiss();
                                } else {

                                    Toast.makeText(context, "Null from Update cerificate", Toast.LENGTH_SHORT).show();
                                }


                            } else {

                                try {
                                    Toast.makeText(context, "Not Success from Update cert " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }


                        }

                        @Override
                        public void onFailure(Call<ResUpdate> call, Throwable t) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();

                            Toast.makeText(context, "Connection error from update certificate " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                delete.clearAnimation();
                delete.setAnimation(anim);




                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
                dialogBuilder
                        .withTitle(context.getString(R.string.app_name))
                        .withDialogColor(R.color.colorPrimary)
                        .withTitleColor("#FFFFFF")
                        .withIcon(context.getDrawable(R.drawable.logo))
                        .withDuration(700)                                          //def
                        .withEffect(Effectstype.RotateBottom)
                        .withMessage(context.getString(R.string.areyousure))
                        .withButton1Text(context.getString(R.string.yes))
                        .withButton2Text(context.getString(R.string.no))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Loading Dialog
                                final ProgressDialog mProgressDialog = new ProgressDialog(context);
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setMessage(context.getResources().getString(R.string.loading));
                                mProgressDialog.show();
                                UpdateCertificate updateCertificate = new UpdateCertificate();
                                experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.Certificate updatedCert = new experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.Certificate();
                                updatedCert.setId(certificate.getId());
                                updatedCert.setName(certificate.getName());
                                updatedCert.setExpertId(certificate.getExpertId());
                                updatedCert.setYearAcquired(certificate.getYearAcquired());
                                updatedCert.setAuthorizedBy(certificate.getAuthorizedBy());
                                updatedCert.setServiceCategoryId(certificate.getServiceCategoryId());
                                updateCertificate.setCertificate(updatedCert);
                                Gson gson =new Gson();
                                Log.e("data : ",gson.toJson(updateCertificate));
                                //Delete certificate
                                Generator.createService(ExpertsApi.class).deleteCertificate(updateCertificate).enqueue(new Callback<ResDelete>() {
                                    @Override
                                    public void onResponse(Call<ResDelete> call, Response<ResDelete> response) {

                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();

                                        if(response.isSuccessful()){
                                            if(response.body().getStatus().toString().equals("ok")){
                                                Toast.makeText(context,context.getResources().getString(R.string.removecert)+response.body().getStatus(),Toast.LENGTH_SHORT).show();
                                                //in case of flag 0 that is mean is in register mode and sing up UI and in case of 1 that is mean in update mode update UI
                                                switch (flag){
                                                    case 0:
                                                        //refresh fragment in sign up mode
                                                        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.replace(R.id.register_fm_contanier, new RegCertificates());
                                                        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                                        fragmentTransaction.commit();
                                                        break;
                                                    case 1:
                                                        //refresh fragment in update mode

                                                        android.support.v4.app.FragmentManager fragmentManager2 = ((FragmentActivity) context).getSupportFragmentManager();
                                        /*
                                        android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                        fragmentTransaction2.replace(R.id.fragmentcontener, new AccountMangeFrag());
                                        fragmentTransaction2.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                        fragmentTransaction2.commit();
                                        */
                                                        fragmentManager2.beginTransaction().detach(fragment).attach(fragment).commit();
                                                        break;
                                                }

                                                dialogBuildercard.dismiss();

                                            }else {
                                                Toast.makeText(context,response.body().getErrorMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            try {
                                                Toast.makeText(context,"Response not success from remove certificates"+response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResDelete> call, Throwable t) {
                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();
                                        Toast.makeText(context,"Connection error from remove certificate "+t.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });
                                dialogBuilder.dismiss();

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
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                close.clearAnimation();
                close.setAnimation(anim);

                dialogBuildercard.dismiss();

            }
        });









    }


    //Subscribe to new service
    public static void fireSubscribeNewServiceDialog(final Context context,View view, final String expertId, final Boolean IsA, final android.support.v4.app.Fragment fragment){

        final BetterSpinner categoriesSp;
        final BetterSpinner servicesSp;
        final EditText price;
        final EditText descountedPrice;
        final TextView subscribe_btn,close;
        FrameLayout discountframe;
        //Initialize Done Dialog
        final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(context);
        dialogBuildercard
                .withDuration(700)//def
                .withEffect(Effectstype.Fall)
                .withDialogColor(Color.WHITE)
                .withTitleColor(Color.BLACK)
                .withTitle(context.getResources().getString(R.string.services))
                .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                .setCustomView(R.layout.add_service_dilaog, view.getContext())
                .show();

        //Setup items
        categoriesSp=(BetterSpinner)dialogBuildercard.findViewById(R.id.category_spinner);
        servicesSp=(BetterSpinner)dialogBuildercard.findViewById(R.id.service_spinner);
        price=(EditText)dialogBuildercard.findViewById(R.id.price_sp);
        descountedPrice=(EditText)dialogBuildercard.findViewById(R.id.discount_sp);
        subscribe_btn=(TextView)dialogBuildercard.findViewById(R.id.subscribe_btn);
        discountframe=(FrameLayout)dialogBuildercard.findViewById(R.id.didcframe);
        close=(TextView)dialogBuildercard.findViewById(R.id.close);

        categoriesSp.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item));
        servicesSp.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item));

        //SetUp spinners
        Generator.createService(ExpertsApi.class).getCategories().enqueue(new Callback<ResCategory>() {
            @Override
            public void onResponse(Call<ResCategory> call, Response<ResCategory> response) {
                if (response.isSuccessful()) {

                    //Fill names and ids
                    categoriesNames = new ArrayList<String>();
                    categoriesIds = new ArrayList<String>();
                    for (int i = 0; i < response.body().getCategories().size(); i++) {
                        categoriesNames.add(response.body().getCategories().get(i).getName());
                        categoriesIds.add(response.body().getCategories().get(i).getId());
                    }

                    categoriesSp.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, categoriesNames));
                    categoriesSp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Generator.createService(ExpertsApi.class).getServicesById(categoriesIds.get(position)).enqueue(new Callback<ResService>() {
                                @Override
                                public void onResponse(Call<ResService> call, Response<ResService> response) {
                                    if (response.isSuccessful()) {
                                        //Fill services Ids and names
                                        servicesNames = new ArrayList<String>();
                                        servicesIds = new ArrayList<String>();
                                        servicesPrice=new ArrayList<String>();
                                        for (int i = 0; i < response.body().getProducts().size(); i++) {
                                            servicesNames.add(response.body().getProducts().get(i).getName());
                                            servicesIds.add(response.body().getProducts().get(i).getId());
                                            if(response.body().getProducts().get(i).getPrice()==null){
                                                servicesPrice.add("0");
                                            }else {
                                                servicesPrice.add(response.body().getProducts().get(i).getPrice()+"");
                                            }

                                        }
                                        servicesSp.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,servicesNames));
                                        servicesSp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                serviceId=servicesIds.get(position);
                                                servicePrice=servicesPrice.get(position);
                                                //Toast.makeText(context,serviceId,Toast.LENGTH_SHORT).show();
                                                price.setText(servicePrice);
                                            }
                                        });


                                    }else {
                                        try {
                                            Toast.makeText(context,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResService> call, Throwable t) {
                                    Toast.makeText(context,"Connection Error from services SP "+t.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });


                }else {
                    try {
                        Toast.makeText(context,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResCategory> call, Throwable t) {
                Toast.makeText(context,"Connection Error from Services SP "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        //if expert class B not allow to modify price
        if(SaveSharedPreference.getCustomerInfo(context).getCustomers().get(0).getCustomerRoleName().equals("Expert B")){
            price.setEnabled(false);
            descountedPrice.setVisibility(View.INVISIBLE);
            discountframe.setVisibility(View.INVISIBLE);
        }else {
            price.setEnabled(true);
            descountedPrice.setVisibility(View.VISIBLE);
            discountframe.setVisibility(View.VISIBLE);
        }




        subscribe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                subscribe_btn.clearAnimation();
                subscribe_btn.setAnimation(anim);

                //Loading Dialog
                final ProgressDialog mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage(context.getResources().getString(R.string.loading));
                mProgressDialog.show();

                //in case of expert A
                if (IsA) {
                    //Inputs validations if expert A
                    if (categoriesSp.getText().toString().isEmpty()) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        categoriesSp.setError(context.getResources().getString(R.string.categorieserr));

                    } else if (servicesSp.getText().toString().isEmpty()) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        servicesSp.setError(context.getResources().getString(R.string.serviceerr));

                    } else if (price.getText().toString().isEmpty()) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        price.setError(context.getResources().getString(R.string.insertprice));

                    }else if (Double.parseDouble(price.getText().toString())<=0) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        price.setError(context.getResources().getString(R.string.cannotsetzero));

                    }else {
                        SubscribeModel subscribeModel = new SubscribeModel();
                        ExpertService expertService = new ExpertService();
                        expertService.setExpertId(Integer.parseInt(expertId));
                        if(!descountedPrice.getText().toString().isEmpty()){
                            expertService.setDiscountAmount(Double.parseDouble(descountedPrice.getText().toString()));
                            expertService.setDiscountPercentage((Double.parseDouble(descountedPrice.getText().toString()) / Double.parseDouble(price.getText().toString()))*100);
                        }else {
                            expertService.setDiscountAmount(0.0);
                            expertService.setDiscountPercentage(0.0);
                        }

                        expertService.setPrice(Double.parseDouble(price.getText().toString()));
                        expertService.setServiceId(Integer.parseInt(serviceId));
                        subscribeModel.setExpertService(expertService);
                        Gson gson=new Gson();
                        Log.e("sub888888",gson.toJson(subscribeModel));
                        Generator.createService(ExpertsApi.class).subscribeService(subscribeModel).enqueue(new Callback<SubscribeResponse>() {
                            @Override
                            public void onResponse(Call<SubscribeResponse> call, Response<SubscribeResponse> response) {
                                if (response.isSuccessful()) {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    if (response.body().getStatus().toString().equals("ok")) {
                                        Toast.makeText(context, context.getResources().getString(R.string.suscribesucsess) + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                        //refresh fragment
                                        android.support.v4.app.FragmentManager fragmentManager3 = ((FragmentActivity) context).getSupportFragmentManager();
                                        fragmentManager3.beginTransaction().detach(fragment).attach(fragment).commit();
                                        dialogBuildercard.dismiss();

                                    } else {
                                        Toast.makeText(context, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    try {
                                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SubscribeResponse> call, Throwable t) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                Toast.makeText(context, "Connection error from service subscribe " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else

                //in case of expert B
                {
                    //Inputs validations if expert A
                    if (categoriesSp.getText().toString().isEmpty()) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        categoriesSp.setError(context.getResources().getString(R.string.categorieserr));
                    } else if (servicesSp.getText().toString().isEmpty()) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        servicesSp.setError(context.getResources().getString(R.string.serviceerr));
                    } else {
                        SubscribeModel subscribeModel = new SubscribeModel();
                        ExpertService expertService = new ExpertService();
                        expertService.setExpertId(Integer.parseInt(expertId));
                        expertService.setDiscountAmount(0.0);
                        expertService.setPrice(Double.parseDouble(servicePrice));
                        expertService.setServiceId(Integer.parseInt(serviceId));
                        expertService.setDiscountPercentage(0.0);
                        subscribeModel.setExpertService(expertService);
                        Generator.createService(ExpertsApi.class).subscribeService(subscribeModel).enqueue(new Callback<SubscribeResponse>() {
                            @Override
                            public void onResponse(Call<SubscribeResponse> call, Response<SubscribeResponse> response) {
                                if (response.isSuccessful()) {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    if (response.body().getStatus().toString().equals("ok")) {

                                        Toast.makeText(context, context.getResources().getString(R.string.suscribesucsess) + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                        //refresh fragment
                                        android.support.v4.app.FragmentManager fragmentManager3 = ((FragmentActivity) context).getSupportFragmentManager();
                                        fragmentManager3.beginTransaction().detach(fragment).attach(fragment).commit();
                                        dialogBuildercard.dismiss();


                                    } else {
                                        Toast.makeText(context, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    try {
                                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SubscribeResponse> call, Throwable t) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                Toast.makeText(context, "Connection error from service subscribe " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
                }
            }

            );


            close.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                close.clearAnimation();
                close.setAnimation(anim);
                dialogBuildercard.dismiss();

            }
            }

            );


        dialogBuildercard.setOnKeyListener(new DialogInterface.OnKeyListener()

                                           {
                                               @Override
                                               public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                                   return keyCode == KeyEvent.KEYCODE_BACK;
                                               }
                                           }

        );


        }


    //Add new outdoor address
    public static void fireAddOutdoorAddressDialog(final Context context,View view, final String expertId, final android.support.v4.app.Fragment fragment){

        //Initialize Done Dialog
        final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(context);
        dialogBuildercard
                .withDuration(700)//def
                .withEffect(Effectstype.Fall)
                .withDialogColor(Color.WHITE)
                .withTitleColor(Color.BLACK)
                .withTitle(context.getResources().getString(R.string.outdoraddress))
                .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                .setCustomView(R.layout.dialog_add_outdoor_place, view.getContext())
                .show();


        final BetterSpinner cities =(BetterSpinner)dialogBuildercard.findViewById(R.id.city_spinner);
        final BetterSpinner nabourhods=(BetterSpinner)dialogBuildercard.findViewById(R.id.nabourhod_spinner);
        cities.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item));
        nabourhods.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item));

        //Setup Spinners
        Generator.createService(ExpertsApi.class).getStates(SAUDI_ID+"").enqueue(new Callback<ResStates>() {
            @Override
            public void onResponse(Call<ResStates> call, Response<ResStates> response) {
                if (response.isSuccessful()) {
                    statesNames = new ArrayList<String>();
                    statesIds = new ArrayList<String>();
                    //fill names and ids to spinner list from response
                    for (int i = 0; i < response.body().getStates().size(); i++) {
                        statesNames.add(response.body().getStates().get(i).getName());
                        statesIds.add(response.body().getStates().get(i).getId());
                    }
                    cities.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, statesNames));
                    cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            statusid = statesIds.get(position);
                            Generator.createService(ExpertsApi.class).getDestrics("Saudi Arabia", statesNames.get(position)).enqueue(new Callback<Districts>() {
                                @Override
                                public void onResponse(Call<Districts> call, Response<Districts> response) {
                                    if (response.isSuccessful()) {
                                        districtsNames = new ArrayList<String>();
                                        districtsIds = new ArrayList<String>();
                                        //fill names and ids to spinner list from response
                                        for (int i = 0; i < response.body().getDistricts().size(); i++) {
                                            districtsNames.add(response.body().getDistricts().get(i).getName());
                                            districtsIds.add(response.body().getDistricts().get(i).getId());
                                        }

                                        nabourhods.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, districtsNames));
                                        nabourhods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                nabourhodId = districtsIds.get(position);
                                            }
                                        });


                                    }
                                }

                                @Override
                                public void onFailure(Call<Districts> call, Throwable t) {

                                }
                            });


                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<ResStates> call, Throwable t) {

            }
        });


        final TextView ddAddress_btn=(TextView)dialogBuildercard.findViewById(R.id.add_address_btn);
        ddAddress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                ddAddress_btn.clearAnimation();
                ddAddress_btn.setAnimation(anim);

               // Toast.makeText(context, "StateId : " + statusid + " districtId : " + nabourhodId, Toast.LENGTH_SHORT).show();


                if(cities.getText().toString().isEmpty()){
                    cities.setError("!");
                }else if(nabourhods.getText().toString().isEmpty()){
                    nabourhods.setError("!");
                }else {
                    Address address = new Address();
                    PostNewAddress postNewAddress = new PostNewAddress();
                    address.setCountryId(69);
                    address.setStateId(Integer.parseInt(statusid));
                    address.setDistrictId(Integer.parseInt(nabourhodId));
                    address.setAddress("address");
                    address.setVendorId(Integer.parseInt(expertId));
                    postNewAddress.setAddress(address);
                    Generator.createService(ExpertsApi.class).addnewAddress(postNewAddress).enqueue(new Callback<SetNewAddressResponse>() {
                        @Override
                        public void onResponse(Call<SetNewAddressResponse> call, Response<SetNewAddressResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    statusid = "";
                                    nabourhodId = "";
                                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayout6, new OutdoorAdressesFrag());
                                    fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                    fragmentTransaction.commit();
                                    dialogBuildercard.dismiss();
                                } else {
                                    Toast.makeText(context, "Null from add outdoor address API ", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<SetNewAddressResponse> call, Throwable t) {
                            Toast.makeText(context, "Connection Error from add outdoor address API " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });

    }

    }



