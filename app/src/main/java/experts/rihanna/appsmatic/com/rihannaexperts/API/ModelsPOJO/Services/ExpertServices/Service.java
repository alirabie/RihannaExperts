package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.ExpertServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/25/2017.
 */
public class Service {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("expert_id")
    @Expose
    private Integer expertId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("discount_percentage")
    @Expose
    private Integer discountPercentage;
    @SerializedName("LastUpdate")
    @Expose
    private String lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
