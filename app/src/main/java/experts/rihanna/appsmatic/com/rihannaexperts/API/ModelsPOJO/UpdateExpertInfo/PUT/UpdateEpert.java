package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.UpdateExpertInfo.PUT;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/2/2017.
 */
public class UpdateEpert {
    @SerializedName("customer")
    @Expose
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
