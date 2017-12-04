package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 12/3/2017.
 */
public class SchdulesResponse {
    @SerializedName("deliveryschedules")
    @Expose
    private List<Deliveryschedule> deliveryschedules = null;

    public List<Deliveryschedule> getDeliveryschedules() {
        return deliveryschedules;
    }

    public void setDeliveryschedules(List<Deliveryschedule> deliveryschedules) {
        this.deliveryschedules = deliveryschedules;
    }
}
