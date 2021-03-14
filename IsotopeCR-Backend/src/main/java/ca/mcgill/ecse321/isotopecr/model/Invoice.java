package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.*;

@Entity
public class Invoice {
	private String invoiceID;

	public void setInvoiceID(String value) {
		this.invoiceID = value;
	}

	@Id
	public String getInvoiceID() {
		return this.invoiceID;
	}

	private double cost;

	public void setCost(double value) {
		this.cost = value;
	}

	public double getCost() {
		return this.cost;
	}

	private Boolean isPaid;

	public void setIsPaid(Boolean value) {
		this.isPaid = value;
	}

	public Boolean getIsPaid() {
		return this.isPaid;
	}
}
