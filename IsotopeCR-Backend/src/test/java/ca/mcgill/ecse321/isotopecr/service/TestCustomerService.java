package ca.mcgill.ecse321.isotopecr.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.HashSet;
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
public class TestCustomerService {
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private ProfileRepository profileRepository;
	@Mock
	private VehicleRepository vehicleRepository;
	
	@InjectMocks
	private ProfileService service;
	
	private static final String VALID_EMAIL1 = "john.doe@gmail.com";
	private static final String VALID_EMAIL2 = "jane.doe@gmail.com";
	private static final String INVALID_EMAIL = "john.doe";
	private static final String ProfileID1 = String.valueOf(VALID_EMAIL1.hashCode());
	private static final String ProfileID2 = String.valueOf(VALID_EMAIL2.hashCode());
	private static final String FIRSTNAME = "John";
	private static final String LASTNAME = "Doe";
	private static final String INVALID_FIRSTNAME = "@#$%^&";
	private static final String VALID_PASSWORD = "Aa123456";
	private static final String VALID_PASSWORD2 = "Bb123456";
	private static final String INVALID_PASSWORD1 = "password";
	private static final String INVALID_PASSWORD2 = "123456";
	private static final String INVALID_PASSWORD3 = "Password";
	private static final String VALID_PHONE_NUMBER = "1234567890";
	private static final String VALID_PHONE_NUMBER2 = "4134567890";
	private static final String INVALID_PHONE_NUMBER = "xx1234567890";
	private static final String LICENSEPLATE = "afefea123";
	private static final String YEAR = "2008";
	private static final String INVALID_YEAR = "1008";
	private static final String MODEL = "CDX13";
	private static final String BRAND = "Nissan";
	private static final String INVALID_BRAND = "#$%^   $%^& %^&";
		
	private static boolean johnDoeIsCreated = false;
	
	@BeforeEach
	public void setCustomerMockOutput() {
		lenient().when(profileRepository.findProfileByProfileID(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(ProfileID1)) {
				if(johnDoeIsCreated) {
					Profile profile = new Profile();
					profile.setEmail(VALID_EMAIL1);
					profile.setFirstName(FIRSTNAME);
					profile.setLastName(LASTNAME);
					profile.setPassword(VALID_PASSWORD);
					profile.setIsRegisteredAccount(true);
					profile.setProfileID(ProfileID1);
					return profile;
				} else {
					return null;
				}
			} else {
				return null;
			}
		});
		
		lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(VALID_EMAIL1)) {
				if(johnDoeIsCreated) {
					Customer customer = new Customer();
					customer.setEmail(VALID_EMAIL1);
					customer.setFirstName(FIRSTNAME);
					customer.setLastName(LASTNAME);
					customer.setPassword(VALID_PASSWORD);
					customer.setIsRegisteredAccount(true);
					customer.setProfileID(ProfileID1);
					customer.setVehicle(new HashSet<Vehicle>());
					return customer;
				} else {
					return null;
				}
			} else if (invocation.getArgument(0).equals(VALID_EMAIL2)) {
				Customer customer = new Customer();
				customer.setEmail(VALID_EMAIL2);
				customer.setFirstName(FIRSTNAME);
				customer.setLastName(LASTNAME);
				customer.setPassword(VALID_PASSWORD);
				customer.setIsRegisteredAccount(true);
				customer.setProfileID(ProfileID2);
				Set<Vehicle> vehicles = new HashSet<Vehicle>();
				Vehicle vehicle = new Vehicle();
				vehicle.setBrand(BRAND);
				vehicle.setLicensePlate(LICENSEPLATE);
				vehicle.setModel(MODEL);
				vehicle.setYear(YEAR);
				vehicles.add(vehicle);
				customer.setVehicle(vehicles);
				return customer;
			} else {
				return null;
			}
		});
		
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateCustomerProfile() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(customer);
		assertEquals(FIRSTNAME, customer.getFirstName());
		assertEquals(LASTNAME, customer.getLastName());
		assertEquals(VALID_EMAIL1, customer.getEmail());
		assertEquals(VALID_PHONE_NUMBER, customer.getPhoneNumber());
		assertEquals(VALID_PASSWORD, customer.getPassword());
		assertEquals(ProfileID1, customer.getProfileID());
		assertTrue(customer.getIsRegisteredAccount());
		assertTrue(customer.getVehicle().isEmpty());
	}
	
	@Test
	public void testCreateCustomerProfileWithEmptyPassword() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, "");
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(customer);
		assertEquals(FIRSTNAME, customer.getFirstName());
		assertEquals(LASTNAME, customer.getLastName());
		assertEquals(VALID_EMAIL1, customer.getEmail());
		assertEquals(VALID_PHONE_NUMBER, customer.getPhoneNumber());
		assertEquals(null, customer.getPassword());
		assertEquals(ProfileID1, customer.getProfileID());
		assertFalse(customer.getIsRegisteredAccount());
		assertTrue(customer.getVehicle().isEmpty());
	}
	
	@Test
	public void testCreateCustomerProfileWithEmptyPhoneNumber() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, "", VALID_PASSWORD);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(customer);
		assertEquals(FIRSTNAME, customer.getFirstName());
		assertEquals(LASTNAME, customer.getLastName());
		assertEquals(VALID_EMAIL1, customer.getEmail());
		assertEquals(null, customer.getPhoneNumber());
		assertEquals(VALID_PASSWORD, customer.getPassword());
		assertEquals(ProfileID1, customer.getProfileID());
		assertTrue(customer.getIsRegisteredAccount());
		assertTrue(customer.getVehicle().isEmpty());
	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidEmail() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, INVALID_EMAIL, VALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid email.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidName() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(INVALID_FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid name.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPhoneNumber() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, INVALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid phonenumber.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPassword1() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, INVALID_PASSWORD1);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid password.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPassword2() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, INVALID_PASSWORD2);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid password.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPassword3() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, INVALID_PASSWORD3);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid password.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateExistingCustomerProfile() {
		Customer customer1 = null;
		Customer customer2 = null;
		try {
			customer1 = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
			johnDoeIsCreated = true;
			customer2 = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Profile with the provided email exists.",e.getMessage());
		}
		
		assertNull(customer2);
		assertNotNull(customer1);
		assertEquals(FIRSTNAME, customer1.getFirstName());
		assertEquals(LASTNAME, customer1.getLastName());
		assertEquals(VALID_EMAIL1, customer1.getEmail());
		assertEquals(VALID_PHONE_NUMBER, customer1.getPhoneNumber());
		assertEquals(VALID_PASSWORD, customer1.getPassword());
		assertEquals(ProfileID1, customer1.getProfileID());
		assertTrue(customer1.getIsRegisteredAccount());
		assertTrue(customer1.getVehicle().isEmpty());

	}
	
	@Test
	public void testCreateVehicle() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_EMAIL1, LICENSEPLATE, YEAR, MODEL, BRAND);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			fail(e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertNotNull(vehicle);
		assertEquals(LICENSEPLATE, vehicle.getLicensePlate());
		assertEquals(YEAR, vehicle.getYear());
		assertEquals(MODEL, vehicle.getModel());
		assertEquals(BRAND, vehicle.getBrand());

	}
	
	@Test
	public void testCreateVehicleEmptyLicensePlate() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_EMAIL1, "", YEAR, MODEL, BRAND);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: License plate cannot be empty.", e.getMessage());
		}

		assertNull(vehicle);

	}
	
	@Test
	public void testCreateVehicleInvalidBrand() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_EMAIL1, LICENSEPLATE, YEAR, MODEL, INVALID_BRAND);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Brand name is invalid.", e.getMessage());
		}

		assertNull(vehicle);

	}
	
	@Test
	public void testCreateVehicleInvalidYear() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_EMAIL1, LICENSEPLATE, INVALID_YEAR, MODEL, BRAND);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Year is invalid.", e.getMessage());
		}

		assertNull(vehicle);

	}
	
	@Test
	public void testDeleteVehicle() {
		Vehicle deletedVehicle = null;
		
		try {
			deletedVehicle = service.deleteVehicle(VALID_EMAIL2, LICENSEPLATE);
		} catch (Exception e) {
			assertEquals("ERROR: Year is invalid.", e.getMessage());
		}
		
		assertNotNull(deletedVehicle);
		assertEquals(BRAND, deletedVehicle.getBrand());
		assertEquals(LICENSEPLATE, deletedVehicle.getLicensePlate());
		assertEquals(MODEL, deletedVehicle.getModel());
		assertEquals(YEAR, deletedVehicle.getYear());

	}
	
	@Test
	public void testEditPassword() {
		Profile profile = null;
		
		try {
			johnDoeIsCreated = true;
			profile = service.editPassword(VALID_EMAIL1, VALID_PASSWORD2);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			fail(e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertEquals(VALID_PASSWORD2, profile.getPassword());
	}
	
	@Test
	public void testEditPhoneNumber() {
		Customer customer = null;
		
		try {
			johnDoeIsCreated = true;
			customer = service.editPhoneNumber(VALID_EMAIL1, VALID_PHONE_NUMBER2);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			fail(e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertEquals(VALID_PHONE_NUMBER2, customer.getPhoneNumber());
	}
	
	@Test
	public void testDeleteProfile() {
		Profile profile = null;
		
		try {
			johnDoeIsCreated = true;
			profile = service.deleteProfile(VALID_EMAIL1);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			fail(e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertEquals(VALID_EMAIL1, profile.getEmail());
	}

}
