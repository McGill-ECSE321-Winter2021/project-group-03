package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Resource{
   private String resourceType;

public void setResourceType(String value) {
    this.resourceType = value;
}
@Id
public String getResourceType() {
    return this.resourceType;
}
private Integer maxAvailable;

public void setMaxAvailable(Integer value) {
    this.maxAvailable = value;
}
public Integer getMaxAvailable() {
    return this.maxAvailable;
}
}
