package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public class Certificate {

    @SerializedName("expert_id")
    @Expose
    private Integer expertId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("service_category_id")
    @Expose
    private Integer serviceCategoryId;
    @SerializedName("authorized_by")
    @Expose
    private String authorizedBy;
    @SerializedName("year_acquired")
    @Expose
    private String yearAcquired;

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(Integer serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public String getYearAcquired() {
        return yearAcquired;
    }

    public void setYearAcquired(String yearAcquired) {
        this.yearAcquired = yearAcquired;
    }
}
