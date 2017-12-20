package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.IndoorServicesCntroal.Put.ResChangeStatus;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Put.Customer;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Put.Image;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Put.PostImages;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.PutResponse.ResponseUploadImage;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.ExpertsApi;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.ImageUtil;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import gun0912.tedbottompicker.TedBottomPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotoGalleryFrag extends Fragment {

    private LinearLayout addPicBtn;


    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private Uri mImageCaptureUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPicBtn = (LinearLayout) view.findViewById(R.id.add_photo_btn);


        //First fragment preview
        PhotosHolderFragment photosHolderFragment=new PhotosHolderFragment();
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.images_cont, photosHolderFragment);
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.commit();




        addPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                addPicBtn.clearAnimation();
                addPicBtn.setAnimation(anim);

                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getContext())
                        .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(ArrayList<Uri> uriList) {
                                // here is selected uri list

                                try {

                                    if(uriList!=null) {
                                        Customer customer =new Customer();
                                        PostImages postImages =new PostImages();
                                        List<Image>images=new ArrayList<Image>();
                                        for (int i = 0; i < uriList.size(); i++) {
                                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriList.get(i));
                                            Image image=new Image();
                                            image.setAttachment(ImageUtil.convert(bitmap));
                                            Log.e("image",ImageUtil.convert(bitmap));
                                            images.add(image);
                                        }

                                        customer.setEmail(SaveSharedPreference.getCustomerInfo(getContext()).getCustomers().get(0).getEmail());
                                        customer.setId(Integer.parseInt(SaveSharedPreference.getCustId(getContext())));
                                        List<Integer>role_ids=new ArrayList<Integer>();
                                        role_ids.add(3);
                                        customer.setRoleIds(role_ids);
                                        customer.setImages(images);
                                        postImages.setCustomer(customer);

                                        Gson gson=new Gson();
                                        Log.e("Images Data",gson.toJson(postImages));


                                        //Post Images to server
                                        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                                        mProgressDialog.setIndeterminate(true);
                                        mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
                                        mProgressDialog.show();
                                        Generator.createService(ExpertsApi.class).uploadImage(postImages).enqueue(new Callback<ResponseUploadImage>() {
                                            @Override
                                            public void onResponse(Call<ResponseUploadImage> call, Response<ResponseUploadImage> response) {
                                                if(response.isSuccessful()){
                                                    if (mProgressDialog.isShowing())
                                                        mProgressDialog.dismiss();
                                                    if(response.body().getCustomers()!=null){
                                                        if(response.body().getCustomers().get(0).getImages().get(0).getSrc()!=null){
                                                            //Update Fragment
                                                            PhotosHolderFragment photosHolderFragment=new PhotosHolderFragment();
                                                            android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
                                                            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.images_cont, photosHolderFragment);
                                                            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                                            fragmentTransaction.commit();
                                                            Log.e("Uploaded : ",response.body().getCustomers().get(0).getImages().get(0).getSrc().toString());
                                                        }else {
                                                            Log.e("Uploaded : ","Null");
                                                        }

                                                    }else {
                                                        Toast.makeText(getContext(),"Null from upload images API ",Toast.LENGTH_SHORT).show();
                                                    }



                                                }else {
                                                    if (mProgressDialog.isShowing())
                                                        mProgressDialog.dismiss();
                                                    try {
                                                        Toast.makeText(getContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseUploadImage> call, Throwable t) {
                                                if (mProgressDialog.isShowing())
                                                    mProgressDialog.dismiss();
                                                Toast.makeText(getContext(),"Connection Error from upload images API "+t.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setPeekHeight(1600)
                        .setSelectMaxCount(1)
                        .showTitle(false)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("No Select")
                        .create();

                bottomSheetDialogFragment.show(getFragmentManager());


            }
        });


    }




}
