package com.company.adminapi.viewmodel;

import com.company.adminapi.model.Customer;
import com.company.adminapi.model.InvoiceItem;
import com.company.adminapi.model.LevelUp;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrderViewModel {
    @Valid
    @NotNull(message = "Enter customer details.")
    private Customer customer;
    private Integer invoiceId;
    private LocalDate purchaseDate;

    @Valid
    @NotNull(message = "Enter product or invoice item.")
    @NotEmpty(message = "Enter product or invoice item.")
    private List<InvoiceItem> invoiceItems;
    private BigDecimal orderTotal;
    private Integer awardedPoints;
    private LevelUp memberPoints;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(Integer awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    public LevelUp getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(LevelUp memberPoints) {
        this.memberPoints = memberPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewModel that = (OrderViewModel) o;
        return Objects.equals(customer, that.customer) &&
                Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(invoiceItems, that.invoiceItems) &&
                Objects.equals(orderTotal, that.orderTotal) &&
                Objects.equals(awardedPoints, that.awardedPoints) &&
                Objects.equals(memberPoints, that.memberPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, invoiceId, purchaseDate, invoiceItems, orderTotal, awardedPoints, memberPoints);
    }

    @Override
    public String toString() {
        return "OrderViewModel{" +
                "customer=" + customer +
                ", invoiceId=" + invoiceId +
                ", purchaseDate=" + purchaseDate +
                ", invoiceItems=" + invoiceItems +
                ", orderTotal=" + orderTotal +
                ", awardedPoints=" + awardedPoints +
                ", memberPoints=" + memberPoints +
                '}';
    }
}