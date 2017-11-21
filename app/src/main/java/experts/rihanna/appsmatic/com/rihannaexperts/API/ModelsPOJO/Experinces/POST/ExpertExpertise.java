package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 11/21/2017.
 */
public class ExpertExpertise {
    @SerializedName("expert_id")
    @Expose
    private Integer expertId;
    @SerializedName("expertise")
    @Expose
    private List<Expertise> expertise = null;

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public List<Expertise> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<Expertise> expertise) {
        this.expertise = expertise;
    }
}
