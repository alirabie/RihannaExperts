package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.MangeOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/28/2017.
 */
public class OrderItem {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("CustomPrice")
    @Expose
    private Integer customPrice;
    @SerializedName("ExpertPrice")
    @Expose
    private Integer expertPrice;
    @SerializedName("ExpertPriceAfterDiscount")
    @Expose
    private Integer expertPriceAfterDiscount;
    @SerializedName("finalPrice")
    @Expose
    private Integer finalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(Integer customPrice) {
        this.customPrice = customPrice;
    }

    public Integer getExpertPrice() {
        return expertPrice;
    }

    public void setExpertPrice(Integer expertPrice) {
        this.expertPrice = expertPrice;
    }

    public Integer getExpertPriceAfterDiscount() {
        return expertPriceAfterDiscount;
    }

    public void setExpertPriceAfterDiscount(Integer expertPriceAfterDiscount) {
        this.expertPriceAfterDiscount = expertPriceAfterDiscount;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }
}
