package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Del;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/20/2017.
 */
public class DeletePhotoRes {
    @SerializedName("Message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
