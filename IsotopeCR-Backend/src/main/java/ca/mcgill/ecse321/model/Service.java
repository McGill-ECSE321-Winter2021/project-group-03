package ca.mcgill.ecse321.model;


import  javax.persistence.Entity;
import  javax.persistence.ManyToOne;
import  javax.persistence.Id;

@Entity
public class Service
{
	
	
  private Resource resource;

 @ManyToOne
  public Resource getResource() {
	 return this.resource;
 }
 
  public void setResource(Resource resource) {
	  this.resource=resource;
  }
  
  
  
  private String name;
  
  @Id
  public String getName() {
	  return this.name;
  }
  
  public void setName(String name) {
	  this.name=name;
  }
  
  
  
  private double price;
  
  public double getPrice() {
	  return this.price;
  }
  
  public void setPrice(double price) {
	  this.price=price;
  }
  
  
  private int duration;
  
  public int getDuration() {
	  return this.duration;
  }
  
  public void setDuration(int duration) {
	  this.duration=duration;
  }
  
}



