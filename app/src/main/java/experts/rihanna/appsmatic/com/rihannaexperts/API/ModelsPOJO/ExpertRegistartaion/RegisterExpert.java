package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExpertRegistartaion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 12/1/2017.
 */
public class RegisterExpert {

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
