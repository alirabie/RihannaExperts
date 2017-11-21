package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.DELETE;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/21/2017.
 */
public class DeleteExp {

    @SerializedName("experience")
    @Expose
    private Experience experience;

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }
}
