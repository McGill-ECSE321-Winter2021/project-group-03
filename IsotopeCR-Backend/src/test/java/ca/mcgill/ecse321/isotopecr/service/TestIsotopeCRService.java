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
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;

@ExtendWith(MockitoExtension.class)
public class TestIsotopeCRService {
	@Mock
	private ResourceRepository resourceDao;
	
	@Mock
	private CompanyProfileRepository companyProfileDao;
	
	@Mock
	private CustomerRepository customerDao;
	
	@Mock
	private DailyAvailabilityRepository dailyAvailability;
	
	@InjectMocks
	private IsotopeCRService service;

	private static final String RESOURCE_KEY = "TestResource";
	private static final Integer RESOURCE_MAX = 6;
	private static final String ADDRESS1 = "Rue TestAddress1";
	private static final String ADDRESS2 = "Rue TestAddress2";
	private static final String COMPANY_NAME = "Isotope";
	private static final String COMPANY_NAME2 = "Isotop 2";
	private static final String INVALID_COMPANY_NAME = "!!!!###";
	private static final String WORKING_HOURS = "9:00 AM - 5:00 PM, MON - FRI";
	private static final String WORKING_HOURS2 = "9:00 AM - 9:00 PM. MON - SAT";
	
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
	
	@BeforeEach
	public void setCompanyProfileMockOutput() {
	    lenient().when(companyProfileDao.findCompanyProfileByAddress(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(ADDRESS1)) {
	        	CompanyProfile companyProfile = new CompanyProfile();
	        	companyProfile.setAddress(ADDRESS1);
	            companyProfile.setCompanyName(COMPANY_NAME);
	            companyProfile.setWorkingHours(WORKING_HOURS);
	            return companyProfile;
	        } else {
	            return null;
	        }
	    });
	}
	
	@Test
	public void testCreateCompanyProfile() {
		CompanyProfile companyProfile = null;
		try {
			companyProfile = service.createCompanyProfile(COMPANY_NAME, ADDRESS1, WORKING_HOURS);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}
		assertNotNull(companyProfile);
		assertEquals(COMPANY_NAME, companyProfile.getCompanyName());
		assertEquals(ADDRESS1, companyProfile.getAddress());
		assertEquals(WORKING_HOURS, companyProfile.getWorkingHours());
	}
	
	@Test
	public void testCreateCompanyProfileWithInvalidName() {
		CompanyProfile companyProfile = null;
		try {
			companyProfile = service.createCompanyProfile(INVALID_COMPANY_NAME, ADDRESS1, WORKING_HOURS);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid input: Company Name", e.getMessage());
		}
		assertNull(companyProfile);
	}
	
	@Test
	public void testCreateMultipleCompanyProfile() {
		CompanyProfile companyProfile1 = null;
		CompanyProfile companyProfile2 = null;
		
		try {
			companyProfile1 = service.createCompanyProfile(COMPANY_NAME, ADDRESS1, WORKING_HOURS);
			companyProfile2 = service.createCompanyProfile(COMPANY_NAME, ADDRESS2, WORKING_HOURS);
		} catch (Exception e) {
			assertEquals("Cannot create more than 1 profile", e.getMessage());
		}
		assertNull(companyProfile2);
		assertNotNull(companyProfile1);
		assertEquals(COMPANY_NAME, companyProfile1.getCompanyName());
		assertEquals(ADDRESS1, companyProfile1.getAddress());
		assertEquals(WORKING_HOURS, companyProfile1.getWorkingHours());
	}
	
	@Test
	public void testEditCompanyProfile() {
		CompanyProfile companyProfile = null;
		
		try {
			companyProfile = service.createCompanyProfile(COMPANY_NAME, ADDRESS2, WORKING_HOURS);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			companyProfile = service.editCompanyProfile(COMPANY_NAME2, ADDRESS2, WORKING_HOURS2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(companyProfile);
		assertEquals(COMPANY_NAME2, companyProfile.getCompanyName());
		assertEquals(ADDRESS2, companyProfile.getAddress());
		assertEquals(WORKING_HOURS2, companyProfile.getWorkingHours());
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