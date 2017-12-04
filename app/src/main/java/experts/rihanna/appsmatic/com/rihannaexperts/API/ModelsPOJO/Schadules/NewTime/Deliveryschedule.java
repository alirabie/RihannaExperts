package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Schadules.NewTime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/3/2017.
 */
public class Deliveryschedule {

    @SerializedName("vendorid")
    @Expose
    private Integer vendorid;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("timefrom")
    @Expose
    private String timefrom;
    @SerializedName("timeto")
    @Expose
    private String timeto;

    public Integer getVendorid() {
        return vendorid;
    }

    public void setVendorid(Integer vendorid) {
        this.vendorid = vendorid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimefrom() {
        return timefrom;
    }

    public void setTimefrom(String timefrom) {
        this.timefrom = timefrom;
    }

    public String getTimeto() {
        return timeto;
    }

    public void setTimeto(String timeto) {
        this.timeto = timeto;
    }

}
