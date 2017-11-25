package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Services.Subscribe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/25/2017.
 */
public class ExpertService {

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
    private Double discountPercentage;

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

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}

