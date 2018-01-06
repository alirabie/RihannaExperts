package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateOrderTime.Res;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eng Ali on 11/28/2017.
 */
public class OrderItemsAdb extends RecyclerView.Adapter<OrderItemsAdb.Vh01> {
    List<experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrderItem>orderItems;
    Context context;
    String status;

    public OrderItemsAdb(List<experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrderItem> orderItems, Context context,String status) {
        this.orderItems = orderItems;
        this.context = context;
        this.status=status;
    }

    @Override
    public Vh01 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Vh01(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_order_items,parent,false));
    }

    @Override
    public void onBindViewHolder(final Vh01 holder, final int position) {
        holder.name.setText(orderItems.get(position).getProduct().getName());
        holder.price.setText(orderItems.get(position).getProduct().getPrice()+" "+context.getResources().getString(R.string.sr));

        holder.saveTimeDate.setVisibility(View.INVISIBLE);
        holder.completeCheck.setVisibility(View.INVISIBLE);


        if(orderItems.get(position).getServiceDate()==null){
            holder.dateTv.setText(context.getResources().getString(R.string.notset));
        }else {
            //Date setup
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

            Date date = null;
            try {
                date = sourceFormat.parse(orderItems.get(position).getServiceDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String datet = DateFormat.format(date);

            holder.dateTv.setText(datet);
        }


        if(orderItems.get(position).getServiceTimeFrom()==null){
            holder.timefromTv.setText(context.getResources().getString(R.string.notset));
        }else {
            holder.timefromTv.setText(orderItems.get(position).getServiceTimeFrom().toString());
        }


        if(orderItems.get(position).getServiceTimeTo()==null){
            holder.timeToTv.setText(context.getResources().getString(R.string.notset));
        }else {
            holder.timeToTv.setText(orderItems.get(position).getServiceTimeTo().toString());
        }



        //Close editing time and date when order finished
        if(status.equals("Complete")){
            holder.editDate.setVisibility(View.INVISIBLE);
            holder.editTimeFrom.setVisibility(View.INVISIBLE);
            holder.editTimeTo.setVisibility(View.INVISIBLE);
        }


        //Date config
        holder.editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.editDate.clearAnimation();
                holder.editDate.setAnimation(anim);


                    //Date setup
                    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                    Date date = null;
                    try {
                        date = sourceFormat.parse(orderItems.get(position).getServiceDate());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }




                final Calendar calendar = Calendar.getInstance();
                if(date!=null) {
                    calendar.setTimeInMillis(date.getTime());
                }
                DatePickerDialog datePicker = new DatePickerDialog(context, 0, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                        String date = dateFormatter.format(c.getTime());
                        holder.saveTimeDate.setVisibility(View.VISIBLE);
                        holder.dateTv.setText(date);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                datePicker.getDatePicker().setFirstDayOfWeek(Calendar.DAY_OF_WEEK);
                datePicker.show();

            }
        });





        // Edit time from
        holder.editTimeFrom.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick (View v){
                                                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                                                holder.editTimeFrom.clearAnimation();
                                                holder.editTimeFrom.setAnimation(anim);

                                                Calendar mcurrentTime = Calendar.getInstance();
                                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                                TimePickerDialog mTimePicker;
                                                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                                    @Override
                                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                        Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                                                        Format formatter;
                                                        formatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                                                        holder.timefromTv.setText(formatter.format(tme).toString());
                                                        holder.saveTimeDate.setVisibility(View.VISIBLE);
                                                    }
                                                }, hour, minute, false);//Yes 24 hour time
                                                mTimePicker.setTitle(context.getString(R.string.from));
                                                mTimePicker.show();

                                            }
                                        }

        );






        //Edit time to
       holder.editTimeTo.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick (View v){
                                              Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                                              holder.editTimeTo.clearAnimation();
                                              holder.editTimeTo.setAnimation(anim);

                                              Calendar mcurrentTime = Calendar.getInstance();
                                              int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                              int minute = mcurrentTime.get(Calendar.MINUTE);
                                              TimePickerDialog mTimePicker;
                                              mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                                  @Override
                                                  public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                      Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                                                      Format formatter;
                                                      formatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                                                      holder.timeToTv.setText(formatter.format(tme).toString());
                                                      holder.saveTimeDate.setVisibility(View.VISIBLE);
                                                  }
                                              }, hour, minute, false);//Yes 24 hour time
                                              mTimePicker.setTitle(context.getString(R.string.from));
                                              mTimePicker.show();

                                          }
                                      }

        );




        holder.saveTimeDate.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View v) {
                                                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                                                holder.saveTimeDate.clearAnimation();
                                                holder.saveTimeDate.setAnimation(anim);
                                                String pattern = "hh:mm a";
                                                SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
                                                try {
                                                    Date datefrom = sdf.parse(holder.timefromTv.getText().toString());
                                                    Date dateto = sdf.parse(holder.timeToTv.getText().toString());

                                                    if (dateto.compareTo(datefrom) > 0) {


                                                      //  Log.e("From : ", timefromTv.getText().toString());
                                                      //  Log.e("To : ", timeToTv.getText().toString());
                                                      //  Log.e("Date : ", dateTv.getText().toString());


                                                        //Update time and date
                                                        final ProgressDialog mProgressDialog = new ProgressDialog(context);
                                                        mProgressDialog.setIndeterminate(true);
                                                        mProgressDialog.setMessage(context.getString(R.string.loading));
                                                        mProgressDialog.show();
                                                        Generator.createService(ExpertsApi.class).updateOrderTime(orderItems.get(position).getId()+"",holder.dateTv.getText()+"",holder.timefromTv.getText().toString(),holder.timeToTv.getText().toString()).enqueue(new Callback<Res>() {
                                                            @Override
                                                            public void onResponse(Call<Res> call, Response<Res> response) {
                                                                if (response.isSuccessful()) {
                                                                    if (mProgressDialog.isShowing())
                                                                        mProgressDialog.dismiss();
                                                                    if (response.body().getMessage() != null) {
                                                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        holder.saveTimeDate.setVisibility(View.INVISIBLE);
                                                                    } else {
                                                                        Toast.makeText(context, "Null From Update Order Time", Toast.LENGTH_SHORT).show();
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
                                                            public void onFailure(Call<Res> call, Throwable t) {
                                                                if (mProgressDialog.isShowing())
                                                                    mProgressDialog.dismiss();
                                                                Toast.makeText(context, "Connection error From Update Order Time" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    } else {
                                                        Toast.makeText(context, context.getResources().getString(R.string.fromto), Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (Exception e) {

                                                }

                                            }
                                        }

        );


















    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class Vh01 extends RecyclerView.ViewHolder{
        private TextView name,price,saveTimeDate,dateTv,timefromTv,timeToTv;
        private ImageView editDate,editTimeFrom,editTimeTo;
        private CheckBox completeCheck ;

        public Vh01(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.order_items_name);
            price=(TextView)itemView.findViewById(R.id.order_items_price);

            dateTv=(TextView)itemView.findViewById(R.id.order_info_date);
            timefromTv=(TextView)itemView.findViewById(R.id.order_info_time_from);
            timeToTv=(TextView)itemView.findViewById(R.id.order_info_time_to);
            editDate=(ImageView)itemView.findViewById(R.id.edite_date_btn);
            editTimeFrom=(ImageView)itemView.findViewById(R.id.edittime_from_btn);
            editTimeTo=(ImageView)itemView.findViewById(R.id.edittime_to_btn);
            saveTimeDate=(TextView)itemView.findViewById(R.id.savetimedate);
            completeCheck=(CheckBox)itemView.findViewById(R.id.compelte);
        }
    }
}
