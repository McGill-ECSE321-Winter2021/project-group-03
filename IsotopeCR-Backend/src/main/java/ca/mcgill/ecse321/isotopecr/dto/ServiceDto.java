package ca.mcgill.ecse321.isotopecr.dto;

public class ServiceDto {

	private String serviceName;
	private int duration;
	private double price;
	private int frequency;
	private ResourceDto resource;

	public ServiceDto() {

	}

	public ServiceDto(String serviceName, int duration, double price, int frequency, ResourceDto resource) {
		this.serviceName = serviceName;
		this.duration = duration;
		this.price = price;
		this.frequency = frequency;
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

	public int getFrequency() {
		return this.frequency;
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

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setResource(ResourceDto resource) {
		this.resource = resource;
	}

}
