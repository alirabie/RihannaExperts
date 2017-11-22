package experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Categories.ResCategory;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.ResCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Delete.ResDelete;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Get.CertificatesList;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.ResUpdate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.UpdateCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.DELETE.DeleteExp;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.GET.GetExperinces;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST.ResPost;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.PUT.UpdateExp;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Get.ResService;
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


}
