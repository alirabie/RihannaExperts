package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.MangeOrders.Order;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.OrderInfoFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

/**
 * Created by Eng Ali on 11/27/2017.
 */
public class ExpertOrdersAdb extends RecyclerView.Adapter<ExpertOrdersAdb.vh00> {

    private List<Order>orders;
    private Context context;
    private String actionSource;

    public ExpertOrdersAdb(List<Order> orders, String actionSource, Context context) {
        this.orders = orders;
        this.actionSource = actionSource;
        this.context = context;
    }

    @Override
    public vh00 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vh00(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_order,parent,false));
    }

    @Override
    public void onBindViewHolder(final vh00 holder, final int position) {
        animate(holder);
        //Set images languages
        if(SaveSharedPreference.getLangId(context).equals("ar")){
            holder.goBtn.setImageResource(R.drawable.gotomakeupartist);

        }else{
            holder.goBtn.setImageResource(R.drawable.gotomakeupartist_en);
        }




        holder.orderTv.setText(context.getResources().getString(R.string.ordernum)+" : "+orders.get(position).getOrderNum()+"   "+context.getResources().getString(R.string.servicetype)+" : "+orders.get(position).getOrderStatus());
        //Date setup
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat DesiredFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        Date dateFrom = null;
        try {
            dateFrom = sourceFormat.parse(orders.get(position).getServiceTimeFrom());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateTo = null;
        try {
            dateTo = sourceFormat.parse(orders.get(position).getServiceTimeTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date=null;
        try {
            date = sourceFormat.parse(orders.get(position).getServiceTimeFrom());
        } catch (ParseException e) {
            e.printStackTrace();
        }




        String formattedDateFrom = DesiredFormat.format(dateFrom.getTime());
        String fromatedDateTo=DesiredFormat.format(dateTo.getTime());
        String dateTv=DateFormat.format(date);

        holder.timeTv.setText(formattedDateFrom+" - "+fromatedDateTo);
        holder.dateTv.setText(dateTv);

        //Order List Item Btn Action
        holder.contaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.contaner.clearAnimation();
                holder.contaner.setAnimation(anim);

                //Go Order Info Fragment
                OrderInfoFrag orderInfoFrag=new OrderInfoFrag();
                Bundle bundle = new Bundle();
                bundle.putInt("orderId",orders.get(position).getId());
                bundle.putString("action_source",actionSource);
                orderInfoFrag.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, orderInfoFrag);
                fragmentTransaction.commit();

            }
        });

    }
    @Override
    public int getItemCount() {
        return orders.size();
    }


    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.fadein);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public static class vh00 extends RecyclerView.ViewHolder{

        private TextView orderTv,dateTv,timeTv;
        private LinearLayout contaner;
        private ImageView goBtn;

        public vh00(View itemView) {
            super(itemView);
            orderTv=(TextView)itemView.findViewById(R.id.order_num_and_category);
            dateTv=(TextView)itemView.findViewById(R.id.order_date);
            timeTv=(TextView)itemView.findViewById(R.id.order_time);
            contaner=(LinearLayout)itemView.findViewById(R.id.btn);
            goBtn=(ImageView)itemView.findViewById(R.id.order_item_go);
        }
    }
}
