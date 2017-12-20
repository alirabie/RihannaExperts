package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UploadingImages.Put;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/19/2017.
 */
public class Image {
    @SerializedName("attachment")
    @Expose
    private String attachment;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
