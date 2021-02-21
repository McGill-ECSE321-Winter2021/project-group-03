package ca.mcgill.ecse321.model;


import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Resource
{
  private String resourceType;
  public void setResourceType(String resourceType) {
	  this.resourceType=resourceType;
  }
  
  @Id
  public String getResourceType() {
	  return this.resourceType;
  }
  
  
  private int maxAvailable;
  public void setMaxAvailable(int maxnum) {
	  this.maxAvailable=maxnum;
  }
  
  public int getMaxAvailable() {
	  return this.maxAvailable;
  }
  
  
}


