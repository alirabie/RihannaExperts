package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.Deleteschaduleres;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.SchdulesResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.Deliveryschedule;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.PutTime;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.Response.ResponseUpdate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eng Ali on 12/3/2017.
 */
public class SchdulesAdb extends RecyclerView.Adapter<SchdulesAdb.Vh23> {


    SchdulesResponse schdulesResponse;
    Context context;
    Fragment fragment;

    public SchdulesAdb(SchdulesResponse schdulesResponse, Fragment fragment, Context context) {
        this.schdulesResponse = schdulesResponse;
        this.fragment = fragment;
        this.context = context;
    }

    @Override
    public Vh23 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Vh23(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_expert_times,parent,false));
    }

    @Override
    public void onBindViewHolder(final Vh23 holder, final int position) {

        DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
        final String weekdays[] = dfs.getWeekdays();

        holder.save.setVisibility(View.INVISIBLE);

        //Date setup
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat DesiredFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        Date dateFrom = null;
        try {
            dateFrom = sourceFormat.parse(schdulesResponse.getDeliveryschedules().get(position).getTimefrom());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateTo = null;
        try {
            dateTo = sourceFormat.parse(schdulesResponse.getDeliveryschedules().get(position).getTimeto());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date=null;
        try {
            date = sourceFormat.parse(schdulesResponse.getDeliveryschedules().get(position).getTimefrom());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDateFrom = DesiredFormat.format(dateFrom.getTime());
        String fromatedDateTo=DesiredFormat.format(dateTo.getTime());
        String dateTv=DateFormat.format(date);


        holder.weekDay.setText(weekdays[schdulesResponse.getDeliveryschedules().get(position).getDay() + 1]);
        holder.from.setText(formattedDateFrom);
        holder.to.setText(fromatedDateTo);

        //Edit From
        holder.editfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.editfrom.clearAnimation();
                holder.editfrom.setAnimation(anim);

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                        Format formatter;
                        formatter = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
                        holder.from.setText(formatter.format(tme).toString());
                        holder.save.setVisibility(View.VISIBLE);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(context.getString(R.string.from));
                mTimePicker.show();
            }
        });


        //Edit To
        holder.editTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.editTo.clearAnimation();
                holder.editTo.setAnimation(anim);

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                        Format formatter;
                        formatter = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
                        holder.to.setText(formatter.format(tme).toString());
                        holder.save.setVisibility(View.VISIBLE);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(context.getString(R.string.from));
                mTimePicker.show();
            }
        });



        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.save.clearAnimation();
                holder.save.setAnimation(anim);

                String pattern = "hh:mm a";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.ENGLISH);
                try {
                    Date datefrom = sdf.parse(holder.from.getText().toString());
                    Date dateto = sdf.parse(holder.to.getText().toString());
                    if(dateto.compareTo(datefrom)>0) {

                        PutTime putTime=new PutTime();
                        Deliveryschedule deliveryschedule=new Deliveryschedule();
                        deliveryschedule.setVendorid(Integer.parseInt(SaveSharedPreference.getExpertId(context)));
                        deliveryschedule.setTimefrom(holder.from.getText().toString());
                        deliveryschedule.setTimeto(holder.to.getText().toString());
                        deliveryschedule.setId(schdulesResponse.getDeliveryschedules().get(position).getId());
                        deliveryschedule.setDay(weekdays[schdulesResponse.getDeliveryschedules().get(position).getDay() + 1]);
                        putTime.setDeliveryschedule(deliveryschedule);

                        Gson gson =new Gson();
                        Log.e("ddddd",gson.toJson(putTime));

                        //Update
                        //get data from server
                        final ProgressDialog mProgressDialog = new ProgressDialog(context);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage(context.getResources().getString(R.string.loading));
                        mProgressDialog.show();
                        Generator.createService(ExpertsApi.class).updateTime(putTime).enqueue(new Callback<ResponseUpdate>() {
                            @Override
                            public void onResponse(Call<ResponseUpdate> call, Response<ResponseUpdate> response) {
                                if(response.isSuccessful()){
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                   if(response.body().getDeliveryschedules()!=null){
                                       Toast.makeText(context,context.getResources().getString(R.string.sucsess),Toast.LENGTH_SHORT).show();
                                       //refresh fragment
                                       android.support.v4.app.FragmentManager fragmentManager3 = ((FragmentActivity) context).getSupportFragmentManager();
                                       fragmentManager3.beginTransaction().detach(fragment).attach(fragment).commit();

                                   } else {
                                       Toast.makeText(context,"Null from update time adapter",Toast.LENGTH_SHORT).show();
                                   }
                                }else {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    try {
                                        Toast.makeText(context,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseUpdate> call, Throwable t) {
                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                Toast.makeText(context,"Connection error from update time adapter "+t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });





                    }else {
                        Toast.makeText(context, context.getResources().getString(R.string.fromto), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }




            }
        });




        //Delete Time
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.delete.clearAnimation();
                holder.delete.setAnimation(anim);


                Generator.createService(ExpertsApi.class).deleteSchadule(
                        schdulesResponse.getDeliveryschedules().get(position).getVendorid()+"",
                        schdulesResponse.getDeliveryschedules().get(position).getId()+"")
                        .enqueue(new Callback<Deleteschaduleres>() {
                            @Override
                            public void onResponse(Call<Deleteschaduleres> call, Response<Deleteschaduleres> response) {
                                if (response.isSuccessful()) {
                                    if(response.body().getStatus().equals("ok")){
                                        //refresh fragment
                                        android.support.v4.app.FragmentManager fragmentManager3 = ((FragmentActivity) context).getSupportFragmentManager();
                                        fragmentManager3.beginTransaction().detach(fragment).attach(fragment).commit();

                                    }else {
                                        Toast.makeText(context,response.body().getErrorMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    try {
                                        Toast.makeText(context,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Deleteschaduleres> call, Throwable t) {
                                Toast.makeText(context,"Connection error from schedule delete API "+t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }

    @Override
    public int getItemCount() {
        return schdulesResponse.getDeliveryschedules().size();
    }

    public static class Vh23 extends RecyclerView.ViewHolder{

        private TextView weekDay,from,to,save;
        private ImageView editfrom,editTo,delete;
        public Vh23(View itemView) {
            super(itemView);

            weekDay=(TextView)itemView.findViewById(R.id.week_day_tv);
            from=(TextView)itemView.findViewById(R.id.time_from_tv);
            to=(TextView)itemView.findViewById(R.id.time_to_tv);
            save=(TextView)itemView.findViewById(R.id.save_time_btn);
            editfrom=(ImageView)itemView.findViewById(R.id.edit_time_from);
            editTo=(ImageView)itemView.findViewById(R.id.edit_time_to);
            delete=(ImageView)itemView.findViewById(R.id.delete_time_btn);



        }
    }
}
