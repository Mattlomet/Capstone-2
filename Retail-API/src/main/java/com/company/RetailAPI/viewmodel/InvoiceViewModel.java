package com.company.RetailAPI.viewmodel;

import com.company.RetailAPI.model.Invoice;
import com.company.RetailAPI.model.InvoiceItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {
    private int invoiceId;
    @Min(value = 0, message = "you must supply a valid customer id")
    private int customerId;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
    private int levelUpPoints;
    private List<InvoiceItem> invoiceItemList;
    private BigDecimal totalPrice;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getLevelUpPoints() {
        return levelUpPoints;
    }

    public void setLevelUpPoints(int levelUpPoints) {
        this.levelUpPoints = levelUpPoints;
    }

    public List<InvoiceItem> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return getInvoiceId() == that.getInvoiceId() &&
                getCustomerId() == that.getCustomerId() &&
                getLevelUpPoints() == that.getLevelUpPoints() &&
                Objects.equals(getPurchaseDate(), that.getPurchaseDate()) &&
                Objects.equals(getInvoiceItemList(), that.getInvoiceItemList()) &&
                Objects.equals(getTotalPrice(), that.getTotalPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getPurchaseDate(), getLevelUpPoints(), getInvoiceItemList(), getTotalPrice());
    }

    @Override
    public String toString() {
        return "InvoiceViewModel{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", purchaseDate=" + purchaseDate +
                ", levelUpPoints=" + levelUpPoints +
                ", invoiceItemList=" + invoiceItemList +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
