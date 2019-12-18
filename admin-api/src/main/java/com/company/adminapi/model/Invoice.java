package com.company.adminapi.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class Invoice {
	private int id;
	@NotNull(message = "Enter a customer id")
	@Min(value = 1)
	private int customerId;
	@NotNull(message = "Enter the purchase date.")
	private LocalDate purchaseDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Invoice invoice = (Invoice) o;
		return id == invoice.id &&
				customerId == invoice.customerId &&
				Objects.equals(purchaseDate, invoice.purchaseDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, customerId, purchaseDate);
	}

	@Override
	public String toString() {
		return "Invoice{" +
				"id=" + id +
				", customerId=" + customerId +
				", purchaseDate=" + purchaseDate +
				'}';
	}
}
