package experts.rihanna.appsmatic.com.rihannaexperts.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
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
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegCertificates;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments.UpdateCertificateFrag;
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


    //Add certificate
    public static void fireAddCertDialog(final Context context,View view, final int exId, final int flag){

         final EditText certName;
         final EditText granter;
         final BetterSpinner certYear;
         final BetterSpinner spicialty;
         final TextView saveBtn,close;



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
                categoriyId=ids.get(position);

            }
        });



        certName=(EditText)dialogBuildercard.findViewById(R.id.cert_name_input);
        granter=(EditText)dialogBuildercard.findViewById(R.id.granter_input);
        saveBtn=(TextView)dialogBuildercard.findViewById(R.id.save);
        close=(TextView)dialogBuildercard.findViewById(R.id.close);
        certYear=(BetterSpinner)dialogBuildercard.findViewById(R.id.year_of_grant_input);
        certYear.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,years));
        certYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                year=years.get(position)+"";
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
                                           android.support.v4.app.FragmentManager fragmentManager2 = ((FragmentActivity) context).getSupportFragmentManager();
                                           android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                           fragmentTransaction2.replace(R.id.viewpager_presentcards, new UpdateCertificateFrag());
                                           fragmentTransaction2.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                           fragmentTransaction2.commit();
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
    public static void fireUpdateCertDialog(final Context context,View view, final experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.Certificate certificate, final int flag){


        final EditText certName;
        final EditText granter;
        final BetterSpinner certYear;
        final BetterSpinner spicialty;
        final TextView saveBtn,delete,close;



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
        close=(TextView)dialogBuildercard.findViewById(R.id.close);
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
        spicialty.setText(categories.get(certificate.getServiceCategoryId()));
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
                year=years.get(position)+"";
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
                                    Toast.makeText(context,context.getResources().getString(R.string.certupdated), Toast.LENGTH_SHORT).show();

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
                                            android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                            fragmentTransaction2.replace(R.id.viewpager_presentcards, new UpdateCertificateFrag());
                                            fragmentTransaction2.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                            fragmentTransaction2.commit();
                                            break;
                                    }

                                    dialogBuildercard.dismiss();
                                } else {

                                    Toast.makeText(context, "Null from Update cerificate", Toast.LENGTH_SHORT).show();
                                }


                            } else {

                                try {
                                    Toast.makeText(context,"Not Success from Update cert "+response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }


                        }

                        @Override
                        public void onFailure(Call<ResUpdate> call, Throwable t) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();

                            Toast.makeText(context,"Connection error from update certificate "+t.getMessage(),Toast.LENGTH_SHORT).show();
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

                //Delete certificate
                Generator.createService(ExpertsApi.class).deleteCertificate(updatedCert).enqueue(new Callback<ResDelete>() {
                    @Override
                    public void onResponse(Call<ResDelete> call, Response<ResDelete> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if(response.isSuccessful()){
                            if(response.body().getErrorMessage()!=null){
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
                                        android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                        fragmentTransaction2.replace(R.id.viewpager_presentcards, new UpdateCertificateFrag());
                                        fragmentTransaction2.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                        fragmentTransaction2.commit();
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






}
