package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public class AddCertificate {

    @SerializedName("expert_certifications")
    @Expose
    private ExpertCertifications expertCertifications;

    public ExpertCertifications getExpertCertifications() {
        return expertCertifications;
    }

    public void setExpertCertifications(ExpertCertifications expertCertifications) {
        this.expertCertifications = expertCertifications;
    }
}
