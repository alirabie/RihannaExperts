package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateOrderTime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/13/2017.
 */
public class Res {
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
