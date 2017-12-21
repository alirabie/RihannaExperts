package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.OutdoorAddress.Get.ResAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Get.Image;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

/**
 * Created by Eng Ali on 12/21/2017.
 */
public class OutdoorAddressAdb extends RecyclerView.Adapter<OutdoorAddressAdb.OutVh> {

    private Context context;
    private List<ResAddress>addresses;

    public OutdoorAddressAdb(Context context, List<ResAddress> addresses) {
        this.context = context;
        this.addresses = addresses;
    }

    @Override
    public OutVh onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OutVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_outdoor,parent,false));
    }

    @Override
    public void onBindViewHolder(final OutVh holder, int position) {

        animate(holder);
        holder.city.setText(addresses.get(position).getState());
        holder.district.setText(addresses.get(position).getDistrict());
        holder.address.setText(addresses.get(position).getAddress());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.delete.clearAnimation();
                holder.delete.setAnimation(anim);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.fadein);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public static class OutVh extends RecyclerView.ViewHolder{

        private TextView city,district,address;
        private ImageView delete;

        public OutVh(View itemView) {
            super(itemView);

            city=(TextView)itemView.findViewById(R.id.city_name_tv);
            district=(TextView)itemView.findViewById(R.id.district_name_tv);
            address=(TextView)itemView.findViewById(R.id.add_tv);
            delete=(ImageView)itemView.findViewById(R.id.delete_btn);

        }
    }
}
