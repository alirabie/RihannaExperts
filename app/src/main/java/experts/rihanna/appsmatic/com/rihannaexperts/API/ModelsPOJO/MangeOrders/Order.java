package experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.MangeOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng Ali on 11/27/2017.
 */
public class Order {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("orderNum")
    @Expose
    private Integer orderNum;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("OrderStatusId")
    @Expose
    private Integer orderStatusId;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("PaymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("PaymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("OrderItems")
    @Expose
    private Object orderItems;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("ServiceTimeTo")
    @Expose
    private String serviceTimeTo;
    @SerializedName("ServiceTimeFrom")
    @Expose
    private String serviceTimeFrom;
    @SerializedName("CustomerLat")
    @Expose
    private String customerLat;
    @SerializedName("CustomerLong")
    @Expose
    private String customerLong;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("OrderTotal")
    @Expose
    private String orderTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Object getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Object orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTimeTo() {
        return serviceTimeTo;
    }

    public void setServiceTimeTo(String serviceTimeTo) {
        this.serviceTimeTo = serviceTimeTo;
    }

    public String getServiceTimeFrom() {
        return serviceTimeFrom;
    }

    public void setServiceTimeFrom(String serviceTimeFrom) {
        this.serviceTimeFrom = serviceTimeFrom;
    }

    public String getCustomerLat() {
        return customerLat;
    }

    public void setCustomerLat(String customerLat) {
        this.customerLat = customerLat;
    }

    public String getCustomerLong() {
        return customerLong;
    }

    public void setCustomerLong(String customerLong) {
        this.customerLong = customerLong;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
