package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.*;

@Entity
public class Admin extends Profile {
	private Boolean isOwner;

	public void setIsOwner(Boolean value) {
		this.isOwner = value;
	}

	public Boolean getIsOwner() {
		return this.isOwner;
	}
}
