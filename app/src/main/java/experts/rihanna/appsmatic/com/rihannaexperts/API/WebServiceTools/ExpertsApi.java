package experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools;

import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Categories.ResCategory;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.ResCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Delete.ResDelete;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Get.CertificatesList;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.ResUpdate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.UpdateCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ChangeOrderStatus.ChangingResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Districts.Districts;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.DELETE.DeleteExp;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.GET.GetExperinces;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.ResPost;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.PUT.UpdateExp;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExpertRegistartaion.Response.ResExpertRegister;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.IndoorServicesCntroal.IndoorGetRes;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.IndoorServicesCntroal.Put.ResChangeStatus;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.IsBusy.IsBusyRes;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.MangeOrders.SubOrder;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Orders.OrderHeader.OrdersResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.OutdoorAddress.Get.ResAddress;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.OutdoorAddress.Set.SetNewAddressResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.Deleteschaduleres;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.SchdulesResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.Response.ResponseUpdate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.ExpertServices.ResExpertServices;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Get.ResService;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Subscribe.SubscribeResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.UnSubscribe.ResUnSubscribe;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.States.ResStates;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.Response.UpdateExpertResponse;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateOrderTime.Res;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Del.DeletePhotoRes;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Get.GetExpertPhotos;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.PutResponse.ResponseUploadImage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public interface ExpertsApi {

    //Get Categories
    @GET("api/categories?fields=name,id")
    Call<ResCategory> getCategories();

    //Get Services
    @GET("api/products?fields=name,id,price")
    Call<ResService>getServicesById(@Query("category_id") String id);

    //Add certificate
    @POST("api/expert/certificates")
    Call<ResCertificate> addCertificate(@Body Object cert);

    //Get Certificates
    @GET("api/expert/certificates/{id}")
    Call<CertificatesList> getExpertCertificates(@Path("id") String id);

    //Update certificate
    @PUT("api/expert/certificates")
    Call<ResUpdate> updateCertificate(@Body Object cert);

    //Delete certificate
    @HTTP(method = "DELETE", path = "api/expert/certificates", hasBody = true)
    Call<ResDelete> deleteCertificate(@Body UpdateCertificate cert);

    //Add new experiences
    @POST("api/expert/expertise")
    Call<ResPost> addExperinces(@Body Object ex);


    //Get Experiences
    @GET("api/expert/expertise/{id}")
    Call<GetExperinces> getExperinces(@Path("id") String id);


    //Update Experiences
    @PUT("api/expert/expertise/")
    Call<UpdateExp> updateExp(@Body Object exp);


    //Delete exp
    @HTTP(method = "DELETE", path = "api/expert/expertise/", hasBody = true)
    Call<ResDelete> deleteExp(@Body DeleteExp deleteExp);


    //Subscribe Services
    @POST("api/expert/services")
    Call<SubscribeResponse> subscribeService(@Body Object service);

    //Get Expert Services
    @GET("api/expert/services?")
    Call<ResExpertServices> getExpertServices(@Query("ExpertId") String id);


    //Unsubscribe Service
    @POST("api/expert/services/unsubscribe?")
    Call<ResUnSubscribe>unsubscribe(@Query("ExpertId")String expId,@Query("ServiceId")String servId);


    //Get Expert Orders
    @GET("api/expert/orders?")
    Call<OrdersResponse>getExpertOrders(@Query("ExpertId")String expId);

    //Get Expert Orders by date
    @GET("api/expert/order/filterbydate?")
    Call<OrdersResponse>getFilterdOrders(@Query("ExpertId")String expId,@Query("Filter")String date);

    //Get SubOrder By Id
    @GET("api/expert/order/services?")
    Call<OrdersResponse>getOrderInfo(@Query("ExpertId")int expId,@Query("OrderId")int OrderId);


    //Change Order Status
    @POST("/api/expert/order/status?")
    Call<ChangingResponse>changeOrdrStatus(@Query("OrderId")String orderId,@Query("StatusId")String statusId);


    //Register New Expert
    @POST("api/expert")
    Call<ResExpertRegister>registerNewExpert(@Body Object ob);


    //Login
    @POST("api/expert/login")
    Call<LoginResponse> login(@Body Object item);


    //Get Profile Data
    @GET("api/customers/{id}")
    Call<LoginResponse>getProfile(@Path("id")String id);


    //Update Expert Info
    @PUT("api/customers/{id}")
    Call<UpdateExpertResponse> updateExpertInfo(@Body Object item,@Path("id")String id);


    //Get Expert Schedules
    @GET("api/vendors/schedule?&IsClientApp=false")
    Call<SchdulesResponse>getExpertSchadules(@Query("VendorId")String id);

    //add expert time
    @POST("api/expert/schedule")
    Call<ResPost>setNewTime(@Body Object time);

    //update expert time
    @PUT("api/expert/schedule")
    Call<ResponseUpdate>updateTime(@Body Object time);

    //Update Order Time
    @POST("api/expert/service/changetime?")
    Call<Res>updateOrderTime(@Query("orderitemid")String orderId,@Query("servicedate")String date,@Query("timefrom")String timefrom,@Query("timeto")String timeto);

    //Get States by id
    @GET("api/states/{id}")
    Call<ResStates> getStates(@Path("id") String id);

    //Get districts
    @GET("api/districts/country/{country}/state/{state}")
    Call<Districts>getDestrics(@Path("country") String country,@Path("state") String state);

    //Get Indoor Service status
    @GET("api/expert/indoor?")
    Call<IndoorGetRes>getIndoorStatus(@Query("ExpertId") String expId);

    //update expert Indoor Services status
    @PUT("api/expert/indoor/update?")
    Call<ResChangeStatus>updateIndoorServiceStatus(@Query("ExpertId") String expId,@Query("IndoorService") String status);

    //Upload Image
    @PUT("api/expert/images")
    Call<ResponseUploadImage>uploadImage(@Body Object photoOb);


    //Get Expert Photos
    @GET("api/expert/images?")
    Call<GetExpertPhotos>getExpertPhotos(@Query("ExpertId") String expId);


    //delete expert photo
    @POST("api/expert/images/delete/?")
    Call<DeletePhotoRes>deletePhoto(@Query("ExpertId") String expId,@Query("ImageId") String imgId);

    //Add new Outdoor Address
    @POST("api/expert/address")
    Call<SetNewAddressResponse>addnewAddress(@Body Object ob);

    //Add new Outdoor Address
    @GET("api/expert/addresses?")
    Call<List<ResAddress>>getOutdoorAddress(@Query("ExpertId")String expId);

    //Delete schedule
    @POST("api/expert/schedule/delete?")
    Call<Deleteschaduleres>deleteSchadule(@Query("vendorid")String expId,@Query("scheduleid")String schduleId);

    //Delete Address
    @POST("api/expert/address/delete?")
    Call<ResCertificate>deleteaddress(@Query("addressid")String addressId,@Query("vendorid")String expId);


    //Check If Busy
    @POST("api/expert/schedule/isbusy?")
    Call<IsBusyRes>IsBuSYtime(@Query("expertid")String expId,
                              @Query("servicedate")String date,
                              @Query("timefrom")String timeFrom,
                              @Query("timeto")String timeTo);




}
