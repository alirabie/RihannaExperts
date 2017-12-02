package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Errors.ResErrors;

/**
 * Created by Eng Ali on 12/2/2017.
 */
public class LoginResponse extends ResErrors {
    @SerializedName("customers")
    @Expose
    private List<Customer> customers = null;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
