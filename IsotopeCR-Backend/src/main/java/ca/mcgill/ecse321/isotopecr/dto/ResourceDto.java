package ca.mcgill.ecse321.isotopecr.dto;

public class ResourceDto {
	private String resourceType;
	private Integer maxAvailable;
	
	public ResourceDto() {
	}
	
	// By default a resource with type has 0 max available
	public ResourceDto(String resourceType) {
		this(resourceType, 0);
	}

	public ResourceDto(String resourceType, int i) {
		this.resourceType = resourceType;
		this.maxAvailable = i;
	}

	public String getResourceType() {
		return resourceType;
	}

	public int getMaxAvailable() {
		return maxAvailable;
	}
}
