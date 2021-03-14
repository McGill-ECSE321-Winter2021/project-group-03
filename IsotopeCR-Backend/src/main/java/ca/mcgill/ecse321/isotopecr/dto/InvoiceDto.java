package ca.mcgill.ecse321.isotopecr.dto;

public class InvoiceDto {
	private String invoiceID;
	private double cost;
	private boolean isPaid;
	
	public InvoiceDto() {
		
	}
	
	public InvoiceDto(String invoiceID, double cost, boolean isPaid) {
		this.invoiceID = invoiceID;
		this.cost = cost;
		this.isPaid = isPaid;
	}
	
	public boolean getIsPaid() {
		return this.isPaid;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public String getInvoiceID() {
		return this.invoiceID;
	}
	
	public void setCost(double cost){
	    this.cost=cost;
	}

}
