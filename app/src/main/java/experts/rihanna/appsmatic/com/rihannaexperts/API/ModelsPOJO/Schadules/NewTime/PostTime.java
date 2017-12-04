package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.NewTime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/3/2017.
 */
public class PostTime {
    @SerializedName("deliveryschedule")
    @Expose
    private Deliveryschedule deliveryschedule;

    public Deliveryschedule getDeliveryschedule() {
        return deliveryschedule;
    }

    public void setDeliveryschedule(Deliveryschedule deliveryschedule) {
        this.deliveryschedule = deliveryschedule;
    }
}
