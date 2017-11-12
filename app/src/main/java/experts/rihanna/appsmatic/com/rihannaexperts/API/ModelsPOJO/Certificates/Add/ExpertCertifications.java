package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public class ExpertCertifications {

    @SerializedName("expert_id")
    @Expose
    private Integer expertId;
    @SerializedName("certificates")
    @Expose
    private List<Certificate> certificates = null;

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }
}
