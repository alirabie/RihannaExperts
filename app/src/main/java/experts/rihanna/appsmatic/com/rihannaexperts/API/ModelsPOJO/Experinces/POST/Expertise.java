package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.POST;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/21/2017.
 */
public class Expertise {
    @SerializedName("about_expert")
    @Expose
    private String aboutExpert;
    @SerializedName("years_of_experience")
    @Expose
    private Integer yearsOfExperience;
    @SerializedName("expert_id")
    @Expose
    private Integer expertId;

    public String getAboutExpert() {
        return aboutExpert;
    }

    public void setAboutExpert(String aboutExpert) {
        this.aboutExpert = aboutExpert;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }
}
