package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.IndoorServicesCntroal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/14/2017.
 */
public class IndoorGetRes {
    @SerializedName("IndoorService")
    @Expose
    private String indoorService;

    public String getIndoorService() {
        return indoorService;
    }
    public void setIndoorService(String indoorService) {
        this.indoorService = indoorService;
    }
}
