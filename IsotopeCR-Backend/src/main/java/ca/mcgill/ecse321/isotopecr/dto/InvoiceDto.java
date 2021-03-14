package ca.mcgill.ecse321.isotopecr.dto;

public class InvoiceDto {
	
	private double cost;
	private boolean isPaid;
	private String invoiceID;
	
	public InvoiceDto() {
		
	}
	
	public InvoiceDto(double cost, boolean isPaid, String invoiceID) {
		
	this.cost = cost;
	this.isPaid = isPaid;
	this.invoiceID = invoiceID;
	
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public boolean getIsPaid() {
		return this.isPaid;
	}
	
	public String getInvoiceID() {
		return this.invoiceID;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void setIsPaiddouble(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}


	
}
