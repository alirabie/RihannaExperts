package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.ResCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Delete.ResDelete;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.UpdateCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.OutdoorAddress.Get.ResAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Get.Image;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.RegistrationFragments.RegCertificates;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments.OutdoorAdressesFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(final OutVh holder, final int position) {

        animate(holder);
        holder.city.setText(addresses.get(position).getState());
        holder.district.setText(addresses.get(position).getDistrict());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.delete.clearAnimation();
                holder.delete.setAnimation(anim);

                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
                dialogBuilder
                        .withTitle(context.getString(R.string.app_name))
                        .withDialogColor(R.color.colorPrimary)
                        .withTitleColor("#FFFFFF")
                        .withIcon(context.getDrawable(R.drawable.logo))
                        .withDuration(700)                                          //def
                        .withEffect(Effectstype.RotateBottom)
                        .withMessage(context.getString(R.string.areyousure))
                        .withButton1Text(context.getString(R.string.yes))
                        .withButton2Text(context.getString(R.string.no))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Generator.createService(ExpertsApi.class).deleteaddress(addresses.get(position).getId() + "", SaveSharedPreference.getExpertId(context)).enqueue(new Callback<ResCertificate>() {
                                    @Override
                                    public void onResponse(Call<ResCertificate> call, Response<ResCertificate> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body().getStatus().equals("ok")) {
                                                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayout6, new OutdoorAdressesFrag());
                                                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                                fragmentTransaction.commit();
                                            } else {
                                                Toast.makeText(context, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
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
                                    public void onFailure(Call<ResCertificate> call, Throwable t) {
                                        Toast.makeText(context, "Connection error from delete address " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialogBuilder.dismiss();

                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();


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
            delete=(ImageView)itemView.findViewById(R.id.delete_btn);

        }
    }
}
