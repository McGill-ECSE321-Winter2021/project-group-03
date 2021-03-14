package ca.mcgill.ecse321.isotopecr.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;

@ExtendWith(MockitoExtension.class)
public class TestIsotopeCRService {
	@Mock
	private ResourceRepository resourceDao;

	@InjectMocks
	private IsotopeCRService service;

	private static final String RESOURCE_KEY = "TestResource";
	private static final Integer RESOURCE_MAX = 6;
	
	@BeforeEach
	public void setResourceMockOutput() {
	    lenient().when(resourceDao.findResourceByResourceType(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(RESOURCE_KEY)) {
	            Resource resource = new Resource();
	            resource.setResourceType(RESOURCE_KEY);
	            resource.setMaxAvailable(RESOURCE_MAX);
	            return resource;
	        } else {
	            return null;
	        }
	    });
	}
	
	@Test
	public void testCreateResource() {
		assertEquals(0, service.getAllResources().size());
		String type = "PullCar";
		Integer max = 6;
		Resource resource = null;
		try {
			resource = service.addResource(type, max);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail(e.getMessage());
		}
		assertNotNull(resource);
		assertEquals(type, resource.getResourceType());
		assertEquals(max, resource.getMaxAvailable());
	}
	
	@Test
	public void testCreateResourceTypeNull() {
		String type = null;
		Integer max = null;
		String error = null;
		Resource resource = null;
		try {
			resource = service.addResource(type, max);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(resource);
		// check error
		assertEquals("ERROR: the resource type can not be empty.", error);
	}
	
	@Test
	public void testCreateResourceMaxTooSmall() {
		String type = "PullCar";
		Integer max = 0;
		String error = null;
		Resource resource = null;
		try {
			resource = service.addResource(type, max);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(resource);
		// check error
		assertEquals("ERROR: the resource should at least have one availability.", error);
	}
	
	

}