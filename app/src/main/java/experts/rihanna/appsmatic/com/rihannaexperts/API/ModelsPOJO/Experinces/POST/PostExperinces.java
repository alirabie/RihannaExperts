package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/21/2017.
 */
public class PostExperinces {

    @SerializedName("expert_expertise")
    @Expose
    private ExpertExpertise expertExpertise;

    public ExpertExpertise getExpertExpertise() {
        return expertExpertise;
    }

    public void setExpertExpertise(ExpertExpertise expertExpertise) {
        this.expertExpertise = expertExpertise;
    }
}
