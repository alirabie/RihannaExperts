package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public class UpdateCertificate {
    @SerializedName("certificate")
    @Expose
    private Certificate certificate;

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
}
