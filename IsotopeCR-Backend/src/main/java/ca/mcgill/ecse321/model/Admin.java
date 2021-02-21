package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;


@Entity
@DiscriminatorValue("Admin")
public class Admin {
    private Boolean isOwner;

    public void setIsOwner (Boolean isOwner){
        this.isOwner = isOwner;
    }

    public Boolean getIsOwner (){
        return this.isOwner;
    }
}
