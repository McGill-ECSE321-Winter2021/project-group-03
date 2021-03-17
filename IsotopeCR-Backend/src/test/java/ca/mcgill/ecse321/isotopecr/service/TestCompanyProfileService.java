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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;

@ExtendWith(MockitoExtension.class)
public class TestCompanyProfileService {
	
	@Mock
	private CompanyProfileRepository companyProfileRepository;
	
	@InjectMocks
	private AutoRepairShopService service;

	// Mock up company profile
	private static final String ADDRESS1 = "Rue TestAddress1";
	private static final String ADDRESS2 = "Rue TestAddress2";
	private static final String COMPANY_NAME = "Isotope";
	private static final String COMPANY_NAME2 = "Isotope 2";
	private static final String INVALID_COMPANY_NAME = "!!!!###";
	private static final String WORKING_HOURS = "9:00 AM - 5:00 PM, MON - FRI";
	private static final String WORKING_HOURS2 = "9:00 AM - 9:00 PM. MON - SAT";
	
	private static boolean companyProfileIsCreated = false;
	
	@BeforeEach
	public void setCompanyProfileMockOutput() {
	    lenient().when(companyProfileRepository.findCompanyProfileByAddress(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
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
	    
		lenient().when(companyProfileRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			if(companyProfileIsCreated) {
				Set<CompanyProfile> companyProfiles = new HashSet<CompanyProfile>();
	        	CompanyProfile companyProfile = new CompanyProfile();
	        	companyProfile.setAddress(ADDRESS1);
	            companyProfile.setCompanyName(COMPANY_NAME);
	            companyProfile.setWorkingHours(WORKING_HOURS);
	            companyProfiles.add(companyProfile);
	           return companyProfiles;
			} else {
				Set<CompanyProfile> companyProfiles = new HashSet<CompanyProfile>();
				return companyProfiles;
			}
		});
	    
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(companyProfileRepository.save(any(CompanyProfile.class))).thenAnswer(returnParameterAsAnswer);
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
			assertEquals("ERROR: Invalid company name.", e.getMessage());
		}
		assertNull(companyProfile);
	}
	
	@Test
	public void testCreateCompanyProfileWithEmptyAddress() {
		CompanyProfile companyProfile = null;
		try {
			companyProfile = service.createCompanyProfile(COMPANY_NAME, "", WORKING_HOURS);
		} catch (IllegalArgumentException e) {
			assertEquals("ERROR: Address cannot be empty.", e.getMessage());
		}
		assertNull(companyProfile);
	}
	
	@Test
	public void testCreateMultipleCompanyProfile() {
		CompanyProfile companyProfile1 = null;
		CompanyProfile companyProfile2 = null;
		
		try {
			companyProfile1 = service.createCompanyProfile(COMPANY_NAME, ADDRESS1, WORKING_HOURS);
			companyProfileIsCreated = true;
			companyProfile2 = service.createCompanyProfile(COMPANY_NAME, ADDRESS2, WORKING_HOURS);
		} catch (Exception e) {
			companyProfileIsCreated = false;
			assertEquals("ERROR: Company Profile already exists.", e.getMessage());
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
			companyProfile = service.createCompanyProfile(COMPANY_NAME, ADDRESS1, WORKING_HOURS);
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
	public void testEditCompanyProfileWithInvalidCompanyName() {
		CompanyProfile companyProfile = null;
		
		try {
			companyProfile = service.createCompanyProfile(COMPANY_NAME, ADDRESS1, WORKING_HOURS);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			companyProfile = service.editCompanyProfile(INVALID_COMPANY_NAME, ADDRESS2, WORKING_HOURS2);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid company name.", e.getMessage());
		}
		
		assertNotNull(companyProfile);
		assertEquals(COMPANY_NAME, companyProfile.getCompanyName());
		assertEquals(ADDRESS1, companyProfile.getAddress());
		assertEquals(WORKING_HOURS, companyProfile.getWorkingHours());
	}
	
	@Test
	public void testEditCompanyProfileWithEmptyAddress() {
		CompanyProfile companyProfile = null;
		
		try {
			companyProfile = service.createCompanyProfile(COMPANY_NAME, ADDRESS1, WORKING_HOURS);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			companyProfile = service.editCompanyProfile(INVALID_COMPANY_NAME, "", WORKING_HOURS2);
		} catch (Exception e) {
			assertEquals("ERROR: Address cannot be empty.", e.getMessage());
		}
		
		assertNotNull(companyProfile);
		assertEquals(COMPANY_NAME, companyProfile.getCompanyName());
		assertEquals(ADDRESS1, companyProfile.getAddress());
		assertEquals(WORKING_HOURS, companyProfile.getWorkingHours());
	}
	
	@Test
	public void testGetCompanyProfiles() {
		CompanyProfile companyProfile = null;
		
		companyProfileIsCreated = true;
		List<CompanyProfile> companyProfiles = service.getCompanyProfiles();
		companyProfileIsCreated = false;
		
		companyProfile = companyProfiles.get(0);
		
		assertNotNull(companyProfile);
		assertEquals(COMPANY_NAME, companyProfile.getCompanyName());
		assertEquals(ADDRESS1, companyProfile.getAddress());
		assertEquals(WORKING_HOURS, companyProfile.getWorkingHours());
	}
	
}
