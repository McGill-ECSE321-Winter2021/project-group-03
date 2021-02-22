package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.DiscriminatorValue;


@Entity
@DiscriminatorValue("Admin")
public class Admin extends Profile{
	
    private Boolean isOwner;

    public void setIsOwner (Boolean isOwner){
        this.isOwner = isOwner;
    }

    public Boolean getIsOwner (){
        return this.isOwner;
    }
    
    
    
}