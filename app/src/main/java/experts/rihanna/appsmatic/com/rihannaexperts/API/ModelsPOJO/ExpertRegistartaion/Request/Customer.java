package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExpertRegistartaion.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 12/3/2017.
 */
public class Customer {



    @SerializedName("role_ids")
    @Expose
    private List<Integer> roleIds = null;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("longtitude")
    @Expose
    private Object longtitude;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("billing_address")
    @Expose
    private BillingAddress billingAddress;

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Object longtitude) {
        this.longtitude = longtitude;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }
}
