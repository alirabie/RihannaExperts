package experts.rihanna.appsmatic.com.rihannaexperts;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrdersResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Activities.Home;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eng Ali on 2/20/2018.
 */
public class NotificationsServiceEx extends IntentService {
    public NotificationsServiceEx() {
        super("");

    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.+s

        setOrdersCount(getApplicationContext());

    }


    public static int ordersCount=0;
    public static NotificationManager manager;
    static int notId=0;


    @Override
    protected void onHandleIntent(Intent intent) {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
                                                                    .setContentText(getResources().getString(R.string.notifiction)+" "+(response.body().getOrders().size()-ordersCount)+"  "+getResources().getString(R.string.ordersnot));
                                                    Home.setupCartBadge(response.body().getOrders().size()-ordersCount);
                                                    Intent notificationIntent = new Intent(getApplicationContext(),Home.class).putExtra("target","orders")
                                                            .putExtra("count",response.body().getOrders().size()-ordersCount);
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
                                                //Toast.makeText(getBaseContext(), "Null From Orders List", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            /*
                                            try {
                                                Toast.makeText(getBaseContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            */
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrdersResponse> call, Throwable t) {
                                        //  Toast.makeText(getBaseContext(), "Connection error From Orders List" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        return START_STICKY;

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent restartService = new Intent("RestartServiceEx");
        sendBroadcast(restartService);
    }



    public static void setOrdersCount(final Context context){
        Generator.createService(ExpertsApi.class).getExpertOrders(SaveSharedPreference.getExpertId(context)).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ordersCount = response.body().getOrders().size();
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
           //     Toast.makeText(context, "Connection error From Orders List" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
