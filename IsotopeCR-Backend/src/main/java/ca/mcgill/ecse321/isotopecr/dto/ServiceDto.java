package ca.mcgill.ecse321.isotopecr.dto;

import ca.mcgill.ecse321.isotopecr.model.Resource;

public class ServiceDto {
	
	private String serviceName;
	private int duration;
	private double price;
	private ResourceDto resource;
	
	public ServiceDto() {
		
	}
	
	public ServiceDto(String serviceName, int duration, double price, ResourceDto resource) {
		this.serviceName = serviceName;
		this.duration = duration;
		this.price = price;
		this.resource = resource;
	}
	
	public String getServiceName() {
		return this.serviceName;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public ResourceDto getResource() {
		return this.resource;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setResource(ResourceDto resource) {
		this.resource = resource;
	}
	
	

}