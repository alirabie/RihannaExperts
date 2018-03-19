package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.ExtraFees.PUT;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 3/19/2018.
 */

public class Attribute {
    @SerializedName("attribute_id")
    @Expose
    private Integer attributeId;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("attribute_key")
    @Expose
    private String attributeKey;
    @SerializedName("attribute_name")
    @Expose
    private String attributeName;
    @SerializedName("attribute_data_type")
    @Expose
    private String attributeDataType;
    @SerializedName("attribute_default_value")
    @Expose
    private String attributeDefaultValue;

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeDataType() {
        return attributeDataType;
    }

    public void setAttributeDataType(String attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    public String getAttributeDefaultValue() {
        return attributeDefaultValue;
    }

    public void setAttributeDefaultValue(String attributeDefaultValue) {
        this.attributeDefaultValue = attributeDefaultValue;
    }
}
