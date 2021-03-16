package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.*;

@Entity
public class Service {
	private Resource resource;

	@ManyToOne(optional = false)
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	private String serviceName;

	public void setServiceName(String value) {
		this.serviceName = value;
	}

	@Id
	public String getServiceName() {
		return this.serviceName;
	}

	private Integer duration;

	public void setDuration(Integer value) {
		this.duration = value;
	}

	public Integer getDuration() {
		return this.duration;
	}

	private double price;

	public void setPrice(double value) {
		this.price = value;
	}

	public double getPrice() {
		return this.price;
	}
	
	
	private Integer frequency;
	
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	public Integer getFrequency() {
		return this.frequency;
	}
}
