package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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


import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;

import java.io.File;
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
import experts.rihanna.appsmatic.com.rihannaexperts.BuildConfig;
import experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.MainFrag;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.ImageUtil;
import experts.rihanna.appsmatic.com.rihannaexperts.Prefs.SaveSharedPreference;
import experts.rihanna.appsmatic.com.rihannaexperts.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotoGalleryFrag extends Fragment {

    private LinearLayout addPicBtn;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLARY = 2;


    Uri outPutfileUri;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        addPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                addPicBtn.clearAnimation();
                addPicBtn.setAnimation(anim);




                final NiftyDialogBuilder dialogBuildercard = NiftyDialogBuilder.getInstance(getContext());
                dialogBuildercard
                        .withDuration(700)//def
                        .withEffect(Effectstype.Fall)
                        .withDialogColor(getResources().getColor(R.color.colorPrimary))
                        .withTitleColor(Color.BLACK)
                        .withTitle(getResources().getString(R.string.app_name))
                        .isCancelableOnTouchOutside(false)
                        .withButton1Text(getResources().getString(R.string.camera))
                        .withButton2Text(getResources().getString(R.string.gallery))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // permission has been granted, continue as usual
                                Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                                outPutfileUri = FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID + ".provider",file);
                                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
                                startActivityForResult(captureIntent, PICK_FROM_CAMERA);

                                dialogBuildercard.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // Start the Intent
                                startActivityForResult(galleryIntent, PICK_FROM_GALLARY);
                                dialogBuildercard.dismiss();
                            }
                        }).show();





















            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    //pic coming from camera
                    Bitmap bitmap=null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), outPutfileUri);
                        if(bitmap!=null) {
                            Customer customer = new Customer();
                            PostImages postImages = new PostImages();
                            List<Image> images = new ArrayList<Image>();
                            Image image = new Image();
                            image.setAttachment(ImageUtil.convert(bitmap));
                            images.add(image);
                            customer.setEmail(SaveSharedPreference.getCustomerInfo(getContext()).getCustomers().get(0).getEmail());
                            customer.setId(Integer.parseInt(SaveSharedPreference.getCustId(getContext())));
                            List<Integer> role_ids = new ArrayList<Integer>();
                            role_ids.add(3);
                            role_ids.add(5);
                            role_ids.add(6);
                            customer.setRoleIds(role_ids);
                            customer.setImages(images);
                            postImages.setCustomer(customer);
                            //Post Images to server
                            final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                            mProgressDialog.setIndeterminate(true);
                            mProgressDialog.setMessage(getContext().getResources().getString(R.string.loading));
                            mProgressDialog.show();
                            Generator.createService(ExpertsApi.class).uploadImage(postImages).enqueue(new Callback<ResponseUploadImage>() {
                                @Override
                                public void onResponse(Call<ResponseUploadImage> call, Response<ResponseUploadImage> response) {
                                    if (response.isSuccessful()) {
                                        if (mProgressDialog.isShowing())
                                            mProgressDialog.dismiss();
                                        if (response.body().getCustomers() != null) {
                                            if (response.body().getCustomers().get(0).getImages().get(0).getSrc() != null) {
                                                //Update Fragment
                                                PhotosHolderFragment photosHolderFragment = new PhotosHolderFragment();
                                                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                                                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.images_cont, photosHolderFragment);
                                                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                                                fragmentTransaction.commit();
                                                Log.e("Uploaded : ", response.body().getCustomers().get(0).getImages().get(0).getSrc().toString());
                                            } else {
                                                Log.e("Uploaded : ", "Null");
                                            }

                                        } else {
                                            Toast.makeText(getContext(), "Null from upload images API ", Toast.LENGTH_SHORT).show();
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
                                public void onFailure(Call<ResponseUploadImage> call, Throwable t) {
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    Toast.makeText(getContext(), "Connection Error from upload images API " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case PICK_FROM_GALLARY:

                if (resultCode == Activity.RESULT_OK) {
                    //pick image from gallery
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    Bitmap bitmap=null;
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(imgDecodableString);

                    if(bitmap!=null){

                        Customer customer =new Customer();
                        PostImages postImages =new PostImages();
                        List<Image>images=new ArrayList<Image>();
                        Image image=new Image();
                        image.setAttachment(ImageUtil.convert(bitmap));
                        images.add(image);
                        customer.setEmail(SaveSharedPreference.getCustomerInfo(getContext()).getCustomers().get(0).getEmail());
                        customer.setId(Integer.parseInt(SaveSharedPreference.getCustId(getContext())));
                        List<Integer>role_ids=new ArrayList<Integer>();
                        role_ids.add(3);
                        role_ids.add(5);
                        role_ids.add(6);
                        customer.setRoleIds(role_ids);
                        customer.setImages(images);
                        postImages.setCustomer(customer);
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






                }
                break;
        }
    }


}
