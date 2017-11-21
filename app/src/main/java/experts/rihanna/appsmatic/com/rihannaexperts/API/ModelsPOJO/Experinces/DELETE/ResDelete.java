package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Experinces.DELETE;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/21/2017.
 */
public class ResDelete {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
