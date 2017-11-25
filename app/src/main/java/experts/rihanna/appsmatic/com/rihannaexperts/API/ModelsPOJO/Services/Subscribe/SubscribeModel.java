package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Subscribe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/25/2017.
 */
public class SubscribeModel {
    @SerializedName("expert_service")
    @Expose
    private ExpertService expertService;

    public ExpertService getExpertService() {
        return expertService;
    }

    public void setExpertService(ExpertService expertService) {
        this.expertService = expertService;
    }

}
