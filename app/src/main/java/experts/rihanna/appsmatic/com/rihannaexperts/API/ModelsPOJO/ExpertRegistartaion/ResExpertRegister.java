package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExpertRegistartaion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eng Ali on 12/1/2017.
 */
public class ResExpertRegister {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("PictureId")
    @Expose
    private Integer pictureId;
    @SerializedName("AdminComment")
    @Expose
    private Object adminComment;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("Deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("DisplayOrder")
    @Expose
    private Integer displayOrder;
    @SerializedName("MetaKeywords")
    @Expose
    private Object metaKeywords;
    @SerializedName("MetaDescription")
    @Expose
    private Object metaDescription;
    @SerializedName("MetaTitle")
    @Expose
    private Object metaTitle;
    @SerializedName("PageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("AllowCustomersToSelectPageSize")
    @Expose
    private Boolean allowCustomersToSelectPageSize;
    @SerializedName("PageSizeOptions")
    @Expose
    private Object pageSizeOptions;
    @SerializedName("VendorNotes")
    @Expose
    private List<Object> vendorNotes = null;

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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Object getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Object adminComment) {
        this.adminComment = adminComment;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Object getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(Object metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public Object getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(Object metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Object getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(Object metaTitle) {
        this.metaTitle = metaTitle;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getAllowCustomersToSelectPageSize() {
        return allowCustomersToSelectPageSize;
    }

    public void setAllowCustomersToSelectPageSize(Boolean allowCustomersToSelectPageSize) {
        this.allowCustomersToSelectPageSize = allowCustomersToSelectPageSize;
    }

    public Object getPageSizeOptions() {
        return pageSizeOptions;
    }

    public void setPageSizeOptions(Object pageSizeOptions) {
        this.pageSizeOptions = pageSizeOptions;
    }

    public List<Object> getVendorNotes() {
        return vendorNotes;
    }

    public void setVendorNotes(List<Object> vendorNotes) {
        this.vendorNotes = vendorNotes;
    }
}
