package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.ResPost;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.NewTime.Deliveryschedule;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.NewTime.PostTime;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetScheduleFrag extends Fragment {

    private EditText from,to;
    private TextView save;
    private BetterSpinner dayes;
    private String day="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_schedule, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup items
        from=(EditText)view.findViewById(R.id.work_time_from);
        to=(EditText)view.findViewById(R.id.work_time_to);
        save=(TextView)view.findViewById(R.id.save_new_scadule_btn);
        dayes=(BetterSpinner)view.findViewById(R.id.day_spinner);
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
        final String weekdays[] = dfs.getWeekdays();
        dayes.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,weekdays));
        dayes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              day=weekdays[position];
                Toast.makeText(getContext(),day,Toast.LENGTH_SHORT).show();
            }
        });

        to.setFocusable(false);
        from.setFocusable(false);

        //Set times Fragment
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.scadules_contener, new ExpertTimesFarg());
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.commit();


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                        Format formatter;
                        formatter = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
                        from.setText(formatter.format(tme).toString());
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.from));
                mTimePicker.show();

            }
        });




        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                        Format formatter;
                        formatter = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
                        to.setText(formatter.format(tme).toString());
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.to));
                mTimePicker.show();
            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                save.clearAnimation();
                save.setAnimation(anim);


                if(from.getText().toString().isEmpty()){
                    from.setError(getResources().getString(R.string.insertfrom));
                }else if(to.getText().toString().isEmpty()){
                    to.setError(getResources().getString(R.string.insertto));
                }else if(dayes.getText().toString().isEmpty()){
                    dayes.setError(getResources().getString(R.string.insertday));
                }else {
                    String pattern = "hh:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.ENGLISH);
                    try {
                        Date datefrom = sdf.parse(from.getText().toString());
                        Date dateto = sdf.parse(to.getText().toString());
                        if(dateto.compareTo(datefrom)>0) {
                            Deliveryschedule deliveryschedule =new Deliveryschedule();
                            PostTime postTime=new PostTime();
                            deliveryschedule.setTimefrom(from.getText().toString());
                            deliveryschedule.setTimeto(to.getText().toString());
                            deliveryschedule.setDay(day);
                            deliveryschedule.setVendorid(Integer.parseInt(SaveSharedPreference.getExpertId(getContext())));
                            postTime.setDeliveryschedule(deliveryschedule);

                            //Loading Dialog
                            final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                            mProgressDialog.setIndeterminate(true);
                            mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
                            mProgressDialog.show();
                            Generator.createService(ExpertsApi.class).setNewTime(postTime).enqueue(new Callback<ResPost>() {
                                @Override
                                public void onResponse(Call<ResPost> call, Response<ResPost> response) {
                                    if(response.isSuccessful()){
                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();
                                        if (response.body().getStatus().toString().equals("ok")) {
                                            //Set times Fragment
                                            //Reload Fragment
                                            dayes.setText("");
                                            from.setText("");
                                            to.setText("");
                                            day="";
                                            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.scadules_contener, new ExpertTimesFarg());
                                            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                            fragmentTransaction.commit();
                                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.sucsess) + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getContext(),response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();
                                        try {
                                            Toast.makeText(getContext(),response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResPost> call, Throwable t) {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    Toast.makeText(getContext(),"Connection error from add time "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });



                        } else {
                            Toast.makeText(getContext(),getResources().getString(R.string.fromto),Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("errrr",e.getMessage());
                    }






                }






            }
        });



    }
}
