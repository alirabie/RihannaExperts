package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrdersResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.OrderInfoFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

/**
 * Created by Eng Ali on 11/27/2017.
 */
public class ExpertOrdersAdb extends RecyclerView.Adapter<ExpertOrdersAdb.vh00> {

    private OrdersResponse orders;
    private Context context;
    private String actionSource;

    public ExpertOrdersAdb(OrdersResponse orders, Context context, String actionSource) {
        this.orders = orders;
        this.context = context;
        this.actionSource = actionSource;
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




        holder.orderTv.setText(context.getResources().getString(R.string.ordernum)+" : "+orders.getOrders().get(position).getId()+"   "+context.getResources().getString(R.string.servicetype)+" : "+orders.getOrders().get(position).getOrderStatus());
        holder.dateTv.setText(context.getResources().getString(R.string.total)+" : "+orders.getOrders().get(position).getOrderTotal()+context.getResources().getString(R.string.sr));
        holder.timeTv.setText(context.getResources().getString(R.string.paymentstaus)+" : "+orders.getOrders().get(position).getPaymentStatus());




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
                bundle.putString("orderId", orders.getOrders().get(position).getId());
                bundle.putString("action_source",actionSource);
                orderInfoFrag.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontener, orderInfoFrag);
                fragmentTransaction.commit();

                Log.e("order Id ",orders.getOrders().get(position).getId());
            }
        });

    }
    @Override
    public int getItemCount() {
        return orders.getOrders().size();
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
