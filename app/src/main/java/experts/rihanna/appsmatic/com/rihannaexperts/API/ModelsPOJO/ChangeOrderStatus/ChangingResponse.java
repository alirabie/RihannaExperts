package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ChangeOrderStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/28/2017.
 */
public class ChangingResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
