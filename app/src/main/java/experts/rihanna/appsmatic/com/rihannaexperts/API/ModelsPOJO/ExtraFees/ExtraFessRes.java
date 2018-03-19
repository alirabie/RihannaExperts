package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExtraFees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 3/19/2018.
 */

public class ExtraFessRes {

    @SerializedName("ServiceFees")
    @Expose
    private String serviceFees;

    public String getServiceFees() {
        return serviceFees;
    }

    public void setServiceFees(String serviceFees) {
        this.serviceFees = serviceFees;
    }

}
