package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public class ResUpdate {
    @SerializedName("certificates")
    @Expose
    private List<Certificate> certificates = null;

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }
}
