package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExtraFees.PUT;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 3/19/2018.
 */

public class ExtrafeesPuttingRes {
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = null;

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
