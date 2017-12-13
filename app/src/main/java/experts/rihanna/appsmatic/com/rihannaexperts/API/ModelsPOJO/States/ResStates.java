package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.States;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 12/13/2017.
 */
public class ResStates {
    @SerializedName("states")
    @Expose
    private List<State> states = null;

    public List<State> getStates() {
        return states;
    }
    public void setStates(List<State> states) {
        this.states = states;
    }

}
