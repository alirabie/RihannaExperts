package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.UpdateTime.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/3/2017.
 */
public class Times {

    @SerializedName("2017-12-03T13:03:00")
    @Expose
    private String _20171203T130300;

    public String get20171203T130300() {
        return _20171203T130300;
    }

    public void set20171203T130300(String _20171203T130300) {
        this._20171203T130300 = _20171203T130300;
    }
}
