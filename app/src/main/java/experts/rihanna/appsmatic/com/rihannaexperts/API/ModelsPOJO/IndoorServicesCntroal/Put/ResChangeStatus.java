package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.IndoorServicesCntroal.Put;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 12/14/2017.
 */
public class ResChangeStatus {
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
