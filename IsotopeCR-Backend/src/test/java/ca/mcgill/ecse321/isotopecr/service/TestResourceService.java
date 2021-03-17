package ca.mcgill.ecse321.isotopecr.service;

import java.util.List;

import ca.mcgill.ecse321.isotopecr.model.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.isotopecr.dao.ResourceRepository;

@ExtendWith(MockitoExtension.class)
public class TestResourceService {
	
	@Mock
	private ResourceRepository resourceRepository;

	@InjectMocks
	private AutoRepairShopService autoRepairShopService;

	/* Mock up Resources */
	
	//Resource 1
	private static final String RESOURCE_TYPE1 = "PullCar";
	private static final Integer MAX1 = 4;
	
	//Resource 3
	private static final String RESOURCE_TYPE2 = "CarTires";
	private static final Integer MAX2 = 8;
	
	//Resource 4
	private static final String RESOURCE_TYPE3 = "CarEngine";
	private static final Integer MAX3 = 1;
	//Resource 5
	private static final String RESOURCE_TYPE4 = "CarWindShield";
	private static final Integer MAX4 = 2;
	//Resource 6
	private static final String RESOURCE_TYPE5 = "Spaces";
	private static final Integer MAX5 = 2;
	//Resource 7
	private static final String EMPTY_RESOURCE = null;
	private static final Integer EMPTY_AVAILABILITIES = 0;
	
	@BeforeEach
	public void setMockOutput() {
		
		lenient().when(resourceRepository.findResourceByResourceType(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(RESOURCE_TYPE1)) {
				Resource resource = new Resource();
				resource.setMaxAvailable(MAX1);
				resource.setResourceType(RESOURCE_TYPE1);
				return resource;
			}else {
				return null;
			}
		});
		
	}
		
		@Test
		public void testAddResource() {
				Resource resource = null;
			
			try {
			    resource = autoRepairShopService.addResource(RESOURCE_TYPE2, MAX2);
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			
			assertNotNull(resource);
			assertEquals(RESOURCE_TYPE2, resource.getResourceType());
			assertEquals(MAX2, resource.getMaxAvailable());

		}
		
		@Test
		public void testAddResourceAlreadyAdded() {
				Resource resourceAdded = null;
				String error = null;
			
			try {
			    resourceAdded = autoRepairShopService.addResource(RESOURCE_TYPE1, MAX1);
			}catch(IllegalArgumentException e) {
				error = e.getMessage();
			}
			
			assertNotNull(error);
			assertEquals("ERROR: the resource type has existed inside the system.", error);
			assertNull(resourceAdded);

		}
		
		@Test
		public void testAddResourceEmptyResource() {
				Resource resourceAdded = null;
				String error = null;
			
			try {
			    resourceAdded = autoRepairShopService.addResource(EMPTY_RESOURCE, MAX5);
			}catch(IllegalArgumentException e) {
				error = e.getMessage();
			}
			
			assertNotNull(error);
			assertEquals("ERROR: the resource type can not be empty.", error);
			assertNull(resourceAdded);

		}
		
		@Test
		public void testAddResourceNoAvailabilities() {
				Resource resourceAdded = null;
				String error = null;
			
			try {
			    resourceAdded = autoRepairShopService.addResource(RESOURCE_TYPE5, EMPTY_AVAILABILITIES);
			}catch(IllegalArgumentException e) {
				error = e.getMessage();
			}
			
			assertNotNull(error);
			assertEquals("ERROR: the resource should at least have one availability.", error);
			assertNull(resourceAdded);

		}
		
		
		@Test
		public void testRemoveResource() {
			Resource resource = new Resource();
			resource.setResourceType(RESOURCE_TYPE1);
			
			Resource removedResource = null;
			try {
				removedResource = autoRepairShopService.removeResource(RESOURCE_TYPE1);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		
			assertNotNull(removedResource);

			
		}
		
		
		@Test
		public void testGetAllResources() {
			
			Resource resource1 = new Resource();
			Resource resource2 = new Resource();
			
			resource1 = autoRepairShopService.addResource(RESOURCE_TYPE3, MAX3);
			resource2 = autoRepairShopService.addResource(RESOURCE_TYPE4, MAX4);
			
			List<Resource> resources = autoRepairShopService.getAllResources();
			resources.add(resource1);
			resources.add(resource2);
			
			assertNotNull(resources);
			assertEquals(2, resources.size());
			assertEquals(RESOURCE_TYPE3, resources.get(0).getResourceType());
			assertEquals(RESOURCE_TYPE4, resources.get(1).getResourceType());
			
			
		}
			
}
			

	
