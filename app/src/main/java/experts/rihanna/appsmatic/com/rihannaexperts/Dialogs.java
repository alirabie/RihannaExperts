package experts.rihanna.appsmatic.com.rihannaexperts;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Eng Ali on 10/31/2017.
 */
public class Dialogs {


    public static void fireAddCertDialog(final Context context,View view,int exId){

         EditText certName;
         EditText granter;
         BetterSpinner certYear;
         BetterSpinner spicialty;
         final TextView saveBtn,close;

        //Initialize Done Dialog
        final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(context);
        dialogBuildercard
                .withDuration(700)//def
                .withEffect(Effectstype.Newspager)
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


        List<Integer> years=new ArrayList<>();
        for(int i=1990;i<= Calendar.getInstance().get(Calendar.YEAR);i++){
            years.add(i);
        }

        List<String> spicilities=new ArrayList<>();
        spicilities.add("تصفيف شعر");
        spicilities.add("ميك أب");
        certName=(EditText)dialogBuildercard.findViewById(R.id.cert_name_input);
        granter=(EditText)dialogBuildercard.findViewById(R.id.granter_input);
        saveBtn=(TextView)dialogBuildercard.findViewById(R.id.save);
        close=(TextView)dialogBuildercard.findViewById(R.id.close);

        certYear=(BetterSpinner)dialogBuildercard.findViewById(R.id.year_of_grant_input);
        certYear.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,years));
        spicialty=(BetterSpinner)dialogBuildercard.findViewById(R.id.spicialty_input);
        spicialty.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, spicilities));




        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                saveBtn.clearAnimation();
                saveBtn.setAnimation(anim);


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



    public static void fireUpdateCertDialog(final Context context,View view,int exId,int certId){


        EditText certName;
        EditText granter;
        BetterSpinner certYear;
        BetterSpinner spicialty;
        final TextView saveBtn,delete,close;



        //Initialize Done Dialog
        final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(context);
        dialogBuildercard
                .withDuration(700)//def
                .withEffect(Effectstype.Newspager)
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


        List<Integer> years=new ArrayList<>();
        for(int i=1990;i<= Calendar.getInstance().get(Calendar.YEAR);i++){
            years.add(i);
        }

        List<String> spicilities=new ArrayList<>();
        spicilities.add("تصفيف شعر");
        spicilities.add("ميك أب");
        certName=(EditText)dialogBuildercard.findViewById(R.id.cert_name_input);
        granter=(EditText)dialogBuildercard.findViewById(R.id.granter_input);
        saveBtn=(TextView)dialogBuildercard.findViewById(R.id.save2);
        delete=(TextView)dialogBuildercard.findViewById(R.id.next_btn);
        close=(TextView)dialogBuildercard.findViewById(R.id.close);

        certYear=(BetterSpinner)dialogBuildercard.findViewById(R.id.year_of_grant_input);
        certYear.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,years));
        spicialty=(BetterSpinner)dialogBuildercard.findViewById(R.id.spicialty_input);
        spicialty.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, spicilities));




        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                saveBtn.clearAnimation();
                saveBtn.setAnimation(anim);


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                delete.clearAnimation();
                delete.setAnimation(anim);
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
