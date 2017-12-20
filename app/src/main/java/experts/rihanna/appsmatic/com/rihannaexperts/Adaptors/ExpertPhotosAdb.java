package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Del.DeletePhotoRes;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Get.GetExpertPhotos;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Get.Image;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.PhotosHolderFragment;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eng Ali on 12/20/2017.
 */
public class ExpertPhotosAdb extends RecyclerView.Adapter<ExpertPhotosAdb.PhotosVh> {


    private Context context;
    private GetExpertPhotos expertPhotos;


    public ExpertPhotosAdb(Context context, GetExpertPhotos expertPhotos) {
        this.context = context;
        this.expertPhotos = expertPhotos;
    }

    @Override
    public PhotosVh onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotosVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_imge,parent,false));
    }

    @Override
    public void onBindViewHolder(final PhotosVh holder, final int position) {

        animate(holder);

        Picasso.with(context)
                .load(expertPhotos.getCustomers().get(0).getImages().get(position).getSrc())
                .placeholder(R.drawable.imageslider)
                .fit()
                .into(holder.expertPhoto);

        holder.photoContener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context,R.anim.alpha);
                holder.photoContener.clearAnimation();
                holder.photoContener.setAnimation(anim);


            }
        });



        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context,R.anim.alpha);
                holder.deleteBtn.clearAnimation();
                holder.deleteBtn.setAnimation(anim);

                Generator.createService(ExpertsApi.class).deletePhoto(SaveSharedPreference.getExpertId(context),expertPhotos.getCustomers().get(0).getImages().get(position).getId()+"").enqueue(new Callback<DeletePhotoRes>() {
                    @Override
                    public void onResponse(Call<DeletePhotoRes> call, Response<DeletePhotoRes> response) {
                        if(response.isSuccessful()){

                            //Update Fragment
                            PhotosHolderFragment photosHolderFragment=new PhotosHolderFragment();
                            android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.images_cont, photosHolderFragment);
                            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                            fragmentTransaction.commit();


                        }else {
                            try {
                                Toast.makeText(context,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeletePhotoRes> call, Throwable t) {
                        Toast.makeText(context,"Connection Error from delete photo API "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return expertPhotos.getCustomers().get(0).getImages().size();
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public static class PhotosVh extends RecyclerView.ViewHolder{

        private ImageView expertPhoto,deleteBtn;
        private FrameLayout photoContener;
        public PhotosVh(View itemView) {
            super(itemView);
            expertPhoto=(ImageView)itemView.findViewById(R.id.expert_image);
            deleteBtn=(ImageView)itemView.findViewById(R.id.delete_btn_image);
            photoContener=(FrameLayout)itemView.findViewById(R.id.image_contaner);

        }
    }
}
