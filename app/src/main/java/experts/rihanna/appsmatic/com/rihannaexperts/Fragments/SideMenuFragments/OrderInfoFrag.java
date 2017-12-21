package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import junit.framework.Test;

import java.io.IOException;
import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ChangeOrderStatus.ChangingResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.MangeOrders.SubOrder;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.Deliveryschedule;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.PutTime;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.Response.ResponseUpdate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateOrderTime.Res;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.MapsActivity;
import experts.rihanna.appsmatic.com.rihannaexperts.Adaptors.OrderItemsAdb;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInfoFrag extends Fragment  {

    private TextView orderNumTv,dateTv,timefromTv,timeToTv,customerName,customerPhone,customerAddress,saveTimeDate,emptyOrdersFlag,accept,decline,finished,serviceTypeFlag;
    private LinearLayout outDoorFlag,showOnmapBtn;
    private FrameLayout orderConfirmedFlag;
    private RecyclerView orderitemsList;
    private LinearLayout customerInfoPannel;
    private ImageView editDate,editTimeFrom,editTimeTo;



    //status
    private final int PENDING=10;
    private final int ACCEPT=20;
    private final int DONE=30;
    private final int DECLINE=40;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Setup Items
        orderNumTv=(TextView)view.findViewById(R.id.order_info_num_and_category);
        dateTv=(TextView)view.findViewById(R.id.order_info_date);
        timefromTv=(TextView)view.findViewById(R.id.order_info_time_from);
        timeToTv=(TextView)view.findViewById(R.id.order_info_time_to);
        customerName=(TextView)view.findViewById(R.id.order_info_cust_name);
        customerPhone=(TextView)view.findViewById(R.id.order_info_cust_phone);
        customerAddress=(TextView)view.findViewById(R.id.order_info_cust_address);
        emptyOrdersFlag=(TextView)view.findViewById(R.id.order_info_empty_list_flag);
        accept=(TextView)view.findViewById(R.id.accept_btn);
        decline=(TextView)view.findViewById(R.id.next_btn);
        finished=(TextView)view.findViewById(R.id.finishorder_btn);
        serviceTypeFlag=(TextView)view.findViewById(R.id.servicetypeflag);
        showOnmapBtn=(LinearLayout)view.findViewById(R.id.order_info_show_on_map);
        orderConfirmedFlag=(FrameLayout)view.findViewById(R.id.orderconfirmed_flag);
        orderitemsList=(RecyclerView)view.findViewById(R.id.order_info_order_list);
        customerInfoPannel=(LinearLayout)view.findViewById(R.id.customer_info_pannel);

        editDate=(ImageView)view.findViewById(R.id.edite_date_btn);
        editTimeFrom=(ImageView)view.findViewById(R.id.edittime_from_btn);
        editTimeTo=(ImageView)view.findViewById(R.id.edittime_to_btn);
        saveTimeDate=(TextView)view.findViewById(R.id.savetimedate);

        editDate.setVisibility(View.INVISIBLE);
        editTimeFrom.setVisibility(View.INVISIBLE);
        editTimeTo.setVisibility(View.INVISIBLE);
        saveTimeDate.setVisibility(View.INVISIBLE);

        orderConfirmedFlag.setVisibility(View.INVISIBLE);
        emptyOrdersFlag.setVisibility(View.INVISIBLE);
        finished.setVisibility(View.INVISIBLE);
        accept.setVisibility(View.INVISIBLE);
        decline.setVisibility(View.INVISIBLE);

        //Toast.makeText(getContext(),"Order Id "+getArguments().getInt("orderId"),Toast.LENGTH_SHORT).show();


        //Hide Customer Data once expert accept order
        customerInfoPannel.setVisibility(View.INVISIBLE);


        //Get Order Info from Server
        //Loading Dialog
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        mProgressDialog.show();
        Generator.createService(ExpertsApi.class).getOrderInfo(getArguments().getInt("orderId")+"").enqueue(new Callback<SubOrder>() {
            @Override
            public void onResponse(Call<SubOrder> call, final Response<SubOrder> response) {
                if (response.isSuccessful()) {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    if (response.body() != null) {
                        orderNumTv.setText(getResources().getString(R.string.ordernum) + " : " + response.body().getId() + "   " + getResources().getString(R.string.servicetype) + " : " + response.body().getOrderStatus());


                        //status Control Logic
                        switch (response.body().getOrderStatusId()) {
                            case PENDING:
                                //Pending
                                orderConfirmedFlag.setVisibility(View.INVISIBLE);
                                finished.setVisibility(View.INVISIBLE);
                                accept.setVisibility(View.VISIBLE);
                                decline.setVisibility(View.VISIBLE);
                                customerInfoPannel.setVisibility(View.INVISIBLE);
                                editDate.setVisibility(View.INVISIBLE);
                                editTimeFrom.setVisibility(View.INVISIBLE);
                                editTimeTo.setVisibility(View.INVISIBLE);
                                saveTimeDate.setVisibility(View.INVISIBLE);
                                break;
                            case ACCEPT:
                                //Accepted
                                orderConfirmedFlag.setVisibility(View.INVISIBLE);
                                finished.setVisibility(View.VISIBLE);
                                accept.setVisibility(View.INVISIBLE);
                                decline.setVisibility(View.INVISIBLE);
                                customerInfoPannel.setVisibility(View.VISIBLE);
                                editDate.setVisibility(View.VISIBLE);
                                editTimeFrom.setVisibility(View.VISIBLE);
                                editTimeTo.setVisibility(View.VISIBLE);
                                saveTimeDate.setVisibility(View.INVISIBLE);
                                break;
                            case DONE:
                                //Finished
                                orderConfirmedFlag.setVisibility(View.VISIBLE);
                                finished.setVisibility(View.INVISIBLE);
                                accept.setVisibility(View.INVISIBLE);
                                decline.setVisibility(View.INVISIBLE);
                                customerInfoPannel.setVisibility(View.VISIBLE);
                                editDate.setVisibility(View.INVISIBLE);
                                editTimeFrom.setVisibility(View.INVISIBLE);
                                editTimeTo.setVisibility(View.INVISIBLE);
                                saveTimeDate.setVisibility(View.INVISIBLE);

                                break;
                            case DECLINE:
                                //Canceled
                                orderConfirmedFlag.setVisibility(View.INVISIBLE);
                                finished.setVisibility(View.INVISIBLE);
                                accept.setVisibility(View.VISIBLE);
                                decline.setVisibility(View.INVISIBLE);
                                customerInfoPannel.setVisibility(View.INVISIBLE);
                                editDate.setVisibility(View.INVISIBLE);
                                editTimeFrom.setVisibility(View.INVISIBLE);
                                editTimeTo.setVisibility(View.INVISIBLE);
                                saveTimeDate.setVisibility(View.INVISIBLE);
                                break;
                        }


                        //Order Items List
                        if (response.body().getOrderItems() != null) {
                            orderitemsList.setAdapter(new OrderItemsAdb(response.body().getOrderItems(), getContext()));
                            orderitemsList.setLayoutManager(new LinearLayoutManager(getContext()));
                        } else {
                            emptyOrdersFlag.setVisibility(View.VISIBLE);
                        }
                        //Date setup
                        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat DesiredFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                        final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                        Date dateFrom = null;
                        try {
                            dateFrom = sourceFormat.parse(response.body().getServiceTimeFrom());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date dateTo = null;
                        try {
                            dateTo = sourceFormat.parse(response.body().getServiceTimeTo());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date date = null;
                        try {
                            date = sourceFormat.parse(response.body().getServiceTimeFrom());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        final String formattedDateFrom = DesiredFormat.format(dateFrom.getTime());
                        String fromatedDateTo = DesiredFormat.format(dateTo.getTime());
                        String datet = DateFormat.format(date);

                        dateTv.setText(datet);
                        timefromTv.setText(formattedDateFrom);
                        timeToTv.setText(fromatedDateTo);


                        //Customer name
                        customerName.setText(response.body().getCustomer());

                        //service type
                        if(response.body().getServiceType().toString().equals("Indoor")) {
                            serviceTypeFlag.setText(getResources().getString(R.string.indor));
                        }else {
                            serviceTypeFlag.setText(getResources().getString(R.string.outdor));
                        }

                        //Show on map btn action
                        showOnmapBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                showOnmapBtn.clearAnimation();
                                showOnmapBtn.setAnimation(anim1);
                                try {
                                    startActivity(new Intent(getContext(), MapsActivity.class)
                                            .putExtra("lat", Double.parseDouble(response.body().getCustomerLat()))
                                            .putExtra("long", Double.parseDouble(response.body().getCustomerLong()))
                                            .putExtra("name", response.body().getCustomer()));
                                } catch (Exception e) {
                                }

                            }
                        });


                        //Accept Btn Action
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                accept.clearAnimation();
                                accept.setAnimation(anim1);
                                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                                mProgressDialog.show();
                                Generator.createService(ExpertsApi.class).changeOrdrStatus(getArguments().getInt("orderId") + "", ACCEPT + "").enqueue(new Callback<ChangingResponse>() {
                                    @Override
                                    public void onResponse(Call<ChangingResponse> call, Response<ChangingResponse> response) {
                                        if (response.isSuccessful()) {
                                            if (mProgressDialog.isShowing())
                                                mProgressDialog.dismiss();
                                            if (response.body() != null) {
                                                if (response.body().getMessage().toString().equals("Status Updated")) {
                                                    Toast.makeText(getContext(), getResources().getString(R.string.save), Toast.LENGTH_SHORT).show();
                                                    //Refresh Fragment
                                                    android.support.v4.app.FragmentManager fragmentManager2 = ((FragmentActivity) getContext()).getSupportFragmentManager();
                                                    fragmentManager2.beginTransaction().detach(OrderInfoFrag.this).attach(OrderInfoFrag.this).commit();
                                                } else {
                                                    Toast.makeText(getContext(), "Not Updated from change order status", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "null from change order status", Toast.LENGTH_SHORT).show();
                                            }


                                        } else {
                                            if (mProgressDialog.isShowing())
                                                mProgressDialog.dismiss();
                                            try {
                                                Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ChangingResponse> call, Throwable t) {
                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();
                                        Toast.makeText(getContext(), "connection error from change order status " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });


                        //Decline Btn Action
                        decline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                decline.clearAnimation();
                                decline.setAnimation(anim1);
                                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                                mProgressDialog.show();
                                Generator.createService(ExpertsApi.class).changeOrdrStatus(getArguments().getInt("orderId") + "", DECLINE + "").enqueue(new Callback<ChangingResponse>() {
                                    @Override
                                    public void onResponse(Call<ChangingResponse> call, Response<ChangingResponse> response) {
                                        if (response.isSuccessful()) {
                                            if (mProgressDialog.isShowing())
                                                mProgressDialog.dismiss();
                                            if (response.body() != null) {
                                                if (response.body().getMessage().toString().equals("Status Updated")) {
                                                    Toast.makeText(getContext(), getResources().getString(R.string.canceld), Toast.LENGTH_SHORT).show();
                                                    //Refresh Fragment
                                                    android.support.v4.app.FragmentManager fragmentManager2 = ((FragmentActivity) getContext()).getSupportFragmentManager();
                                                    fragmentManager2.beginTransaction().detach(OrderInfoFrag.this).attach(OrderInfoFrag.this).commit();

                                                } else {
                                                    Toast.makeText(getContext(), "Not Updated from change order status", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "null from change order status", Toast.LENGTH_SHORT).show();
                                            }


                                        } else {
                                            if (mProgressDialog.isShowing())
                                                mProgressDialog.dismiss();
                                            try {
                                                Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ChangingResponse> call, Throwable t) {
                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();
                                        Toast.makeText(getContext(), "connection error from change order status " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });


                        //Finished Btn action
                        finished.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                finished.clearAnimation();
                                finished.setAnimation(anim1);
                                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                                mProgressDialog.show();
                                Generator.createService(ExpertsApi.class).changeOrdrStatus(getArguments().getInt("orderId") + "", DONE + "").enqueue(new Callback<ChangingResponse>() {
                                    @Override
                                    public void onResponse(Call<ChangingResponse> call, Response<ChangingResponse> response) {
                                        if (response.isSuccessful()) {
                                            if (mProgressDialog.isShowing())
                                                mProgressDialog.dismiss();
                                            if (response.body() != null) {
                                                if (response.body().getMessage().toString().equals("Status Updated")) {
                                                    Toast.makeText(getContext(), getResources().getString(R.string.orderfinished), Toast.LENGTH_SHORT).show();
                                                    //Refresh Fragment
                                                    android.support.v4.app.FragmentManager fragmentManager2 = ((FragmentActivity) getContext()).getSupportFragmentManager();
                                                    fragmentManager2.beginTransaction().detach(OrderInfoFrag.this).attach(OrderInfoFrag.this).commit();
                                                } else {
                                                    Toast.makeText(getContext(), "Not Updated from change order status", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "null from change order status", Toast.LENGTH_SHORT).show();
                                            }


                                        } else {
                                            if (mProgressDialog.isShowing())
                                                mProgressDialog.dismiss();
                                            try {
                                                Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ChangingResponse> call, Throwable t) {
                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();
                                        Toast.makeText(getContext(), "connection error from change order status " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });


                        //Date config

                        editDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                editDate.clearAnimation();
                                editDate.setAnimation(anim);



                                final Calendar calendar=Calendar.getInstance();
                               DatePickerDialog datePicker = new DatePickerDialog(getContext(), 0, new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                                        Calendar c=Calendar.getInstance();
                                        c.set(year,monthOfYear,dayOfMonth);
                                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
                                        String date = dateFormatter.format(c.getTime());
                                            dateTv.setText(date);
                                    }
                                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                                datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                                datePicker.getDatePicker().setFirstDayOfWeek(Calendar.DAY_OF_WEEK);
                                datePicker.show();

                                }
                            });




                        editTimeFrom.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick (View v){
                                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                editTimeFrom.clearAnimation();
                                editTimeFrom.setAnimation(anim);

                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                                        Format formatter;
                                        formatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                                        timefromTv.setText(formatter.format(tme).toString());
                                        saveTimeDate.setVisibility(View.VISIBLE);
                                    }
                                }, hour, minute, false);//Yes 24 hour time
                                mTimePicker.setTitle(getResources().getString(R.string.from));
                                mTimePicker.show();

                            }
                            }

                            );


                            editTimeTo.setOnClickListener(new View.OnClickListener()

                            {
                                @Override
                                public void onClick (View v){
                                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                editTimeTo.clearAnimation();
                                editTimeTo.setAnimation(anim);

                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        Time tme = new Time(selectedHour, selectedMinute, 0);//seconds by default set to zero
                                        Format formatter;
                                        formatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                                        timeToTv.setText(formatter.format(tme).toString());
                                        saveTimeDate.setVisibility(View.VISIBLE);
                                    }
                                }, hour, minute, false);//Yes 24 hour time
                                mTimePicker.setTitle(getResources().getString(R.string.from));
                                mTimePicker.show();

                            }
                            }

                            );


                            saveTimeDate.setOnClickListener(new View.OnClickListener()

                            {
                                @Override
                                public void onClick (View v){
                                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
                                saveTimeDate.clearAnimation();
                                saveTimeDate.setAnimation(anim);

                                String pattern = "hh:mm a";
                                SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
                                try {
                                    Date datefrom = sdf.parse(timefromTv.getText().toString());
                                    Date dateto = sdf.parse(timeToTv.getText().toString());

                                    if (dateto.compareTo(datefrom) > 0) {


                                        Log.e("From : ", timefromTv.getText().toString());
                                        Log.e("To : ", timeToTv.getText().toString());
                                        Log.e("Date : ", dateTv.getText().toString());


                                        //Update time and date
                                        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                                        mProgressDialog.setIndeterminate(true);
                                        mProgressDialog.setMessage(getResources().getString(R.string.loading));
                                        mProgressDialog.show();
                                        Generator.createService(ExpertsApi.class).updateOrderTime(getArguments().getInt("orderId") + "", timefromTv.getText().toString(), timeToTv.getText().toString()).enqueue(new Callback<Res>() {
                                            @Override
                                            public void onResponse(Call<Res> call, Response<Res> response) {
                                                if (response.isSuccessful()) {
                                                    if (mProgressDialog.isShowing())
                                                        mProgressDialog.dismiss();
                                                    if (response.body().getMessage() != null) {
                                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(getContext(), "Null From Update Order Time", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    if (mProgressDialog.isShowing())
                                                        mProgressDialog.dismiss();
                                                    try {
                                                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<Res> call, Throwable t) {
                                                if (mProgressDialog.isShowing())
                                                    mProgressDialog.dismiss();
                                                Toast.makeText(getContext(), "Connection error From Update Order Time" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        Toast.makeText(getContext(), getResources().getString(R.string.fromto), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {

                                }

                            }
                            }

                            );


                        }else{

                            Toast.makeText(getContext(), "Null from Order Info", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        try {
                            Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }


            }

                @Override
                public void onFailure (Call < SubOrder > call, Throwable t){
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Toast.makeText(getContext(), "Connection error from Order Info " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            );


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
                    if (getArguments().get("action_source").toString().equals("preview_schadule_orders")) {
                        //In case of from preview calender orders
                        ScheduleMangeFrag scheduleMangeFrag = new ScheduleMangeFrag();
                        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentcontener, scheduleMangeFrag);
                        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                        fragmentTransaction.commit();
                        //set title
                        Home.tittle.setText(getResources().getString(R.string.datestable));
                        return true;

                    } else {
                        //in case of from main orders fragment
                        OrdersFrag ordersFrag = new OrdersFrag();
                        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentcontener, ordersFrag);
                        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                        fragmentTransaction.commit();
                        //set title
                        Home.tittle.setText(getResources().getString(R.string.mangeorders));
                        return true;
                    }


                }
                return false;
            }
        });




    }

}
