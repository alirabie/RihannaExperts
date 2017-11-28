package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.MangeOrders.OrderItem;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

/**
 * Created by Eng Ali on 11/28/2017.
 */
public class OrderItemsAdb extends RecyclerView.Adapter<OrderItemsAdb.Vh01> {
    List<OrderItem>orderItems;
    Context context;

    public OrderItemsAdb(List<OrderItem> orderItems, Context context) {
        this.orderItems = orderItems;
        this.context = context;
    }

    @Override
    public Vh01 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Vh01(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_order_items,parent,false));
    }

    @Override
    public void onBindViewHolder(Vh01 holder, int position) {
        holder.name.setText(orderItems.get(position).getName());
        holder.price.setText(orderItems.get(position).getFinalPrice()+" "+context.getResources().getString(R.string.sr));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class Vh01 extends RecyclerView.ViewHolder{
        private TextView name , price;

        public Vh01(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.order_items_name);
            price=(TextView)itemView.findViewById(R.id.order_items_price);
        }
    }
}
