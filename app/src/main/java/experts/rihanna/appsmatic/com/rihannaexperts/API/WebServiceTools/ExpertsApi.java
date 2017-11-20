package experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Categories.ResCategory;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add.ResCertificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Delete.ResDelete;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Get.CertificatesList;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.ResUpdate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.UpdateCertificate;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public interface ExpertsApi {

    //Get Categories
    @GET("api/categories")
    Call<ResCategory> getCategories();

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




}
