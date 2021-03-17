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
public class TestCustomerAdminService {
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private ProfileRepository profileRepository;
	@Mock
	private VehicleRepository vehicleRepository;
	@Mock
	private AdminRepository adminRepository;
	
	@InjectMocks
	private ProfileService service;
	
	private static final String VALID_ADMIN_EMAIL1 = "john.doe@isotopecr.ca";
	private static final String VALID_ADMIN_EMAIL2 = "jane.doe@isotopecr.ca";
	private static final Boolean ISOWNER = true;
	
	private static final String VALID_CUSTOMER_EMAIL1 = "john.doe@gmail.com";
	private static final String VALID_CUSTOMER_EMAIL2 = "jane.doe@gmail.com";
	private static final String EMAIL_NULL = null;
	private static final String INVALID_EMAIL = "john.doe";
	
	private static final String PROFILE_ID1 = String.valueOf(VALID_CUSTOMER_EMAIL1.hashCode());
	private static final String PROFILE_ID2 = String.valueOf(VALID_CUSTOMER_EMAIL2.hashCode());
	
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
	private static final String DEFAULT_PHONE_NUMBER = null;
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
			if(invocation.getArgument(0).equals(PROFILE_ID1)) {
				if(johnDoeIsCreated) {
					Profile profile = new Profile();
					profile.setEmail(VALID_CUSTOMER_EMAIL1);
					profile.setFirstName(FIRSTNAME);
					profile.setLastName(LASTNAME);
					profile.setPassword(VALID_PASSWORD);
					profile.setIsRegisteredAccount(true);
					profile.setProfileID(PROFILE_ID1);
					return profile;
				} else {
					return null;
				}
			} else if (invocation.getArgument(0).equals(PROFILE_ID2)) {
				Customer customer = new Customer();
				customer.setEmail(VALID_CUSTOMER_EMAIL2);
				customer.setFirstName(FIRSTNAME);
				customer.setLastName(LASTNAME);
				customer.setPassword("");
				customer.setIsRegisteredAccount(false);
				customer.setProfileID(PROFILE_ID2);
				Set<Vehicle> vehicles = new HashSet<Vehicle>();
				Vehicle vehicle = new Vehicle();
				vehicle.setBrand(BRAND);
				vehicle.setLicensePlate(LICENSEPLATE);
				vehicle.setModel(MODEL);
				vehicle.setYear(YEAR);
				vehicles.add(vehicle);
				customer.setVehicle(vehicles);
				return customer;
			} else if (invocation.getArgument(0).equals(String.valueOf(VALID_ADMIN_EMAIL1.hashCode()))) {
				Admin admin = new Admin();
				admin.setEmail(VALID_ADMIN_EMAIL1);
				admin.setFirstName(FIRSTNAME);
				admin.setLastName(LASTNAME);
				admin.setPassword(VALID_PASSWORD);
				admin.setIsRegisteredAccount(true);
				admin.setProfileID(String.valueOf(VALID_ADMIN_EMAIL1.hashCode()));
				admin.setIsOwner(ISOWNER);
				return admin;
			} else {
				return null;
			}
		});
		
		lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(VALID_CUSTOMER_EMAIL1)) {
				if(johnDoeIsCreated) {
					Customer customer = new Customer();
					customer.setEmail(VALID_CUSTOMER_EMAIL1);
					customer.setFirstName(FIRSTNAME);
					customer.setLastName(LASTNAME);
					customer.setPassword(VALID_PASSWORD);
					customer.setIsRegisteredAccount(true);
					customer.setProfileID(PROFILE_ID1);
					customer.setVehicle(new HashSet<Vehicle>());
					return customer;
				} else {
					return null;
				}
			} else if (invocation.getArgument(0).equals(VALID_CUSTOMER_EMAIL2)) {
				Customer customer = new Customer();
				customer.setEmail(VALID_CUSTOMER_EMAIL2);
				customer.setFirstName(FIRSTNAME);
				customer.setLastName(LASTNAME);
				customer.setPassword("");
				customer.setIsRegisteredAccount(false);
				customer.setProfileID(PROFILE_ID2);
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
		lenient().when(adminRepository.save(any(Admin.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateCustomerProfile() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(customer);
		assertEquals(FIRSTNAME, customer.getFirstName());
		assertEquals(LASTNAME, customer.getLastName());
		assertEquals(VALID_CUSTOMER_EMAIL1, customer.getEmail());
		assertEquals(VALID_PHONE_NUMBER, customer.getPhoneNumber());
		assertEquals(VALID_PASSWORD, customer.getPassword());
		assertEquals(PROFILE_ID1, customer.getProfileID());
		assertTrue(customer.getIsRegisteredAccount());
		assertTrue(customer.getVehicle().isEmpty());
	}
	
	@Test
	public void testCreateAdminProfile() {
		Admin admin = null;
		try {
			admin = service.createAdminProfile(FIRSTNAME, LASTNAME, VALID_ADMIN_EMAIL2, ISOWNER, VALID_PASSWORD);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(admin);
		assertEquals(FIRSTNAME, admin.getFirstName());
		assertEquals(LASTNAME, admin.getLastName());
		assertEquals(VALID_ADMIN_EMAIL2, admin.getEmail());
		assertEquals(ISOWNER, admin.getIsOwner());
		assertEquals(String.valueOf(VALID_ADMIN_EMAIL2.hashCode()), admin.getProfileID());
		assertTrue(admin.getIsRegisteredAccount());
	}
	
	@Test
	public void testCreateAdminProfileWithoutCompanyEmail() {
		Admin admin = null;
		try {
			admin = service.createAdminProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, ISOWNER, VALID_PASSWORD);
		} catch (Exception e) {
			assertEquals("ERROR: Administrative account creation forbidden. Not a company email.",e.getMessage());
		}
		
		assertNull(admin);

	}
	
	@Test
	public void testCreateExistingAdminProfile() {
		Admin admin = null;
		try {
			admin = service.createAdminProfile(FIRSTNAME, LASTNAME, VALID_ADMIN_EMAIL1, ISOWNER, VALID_PASSWORD);
		} catch (Exception e) {
			assertEquals("ERROR: Administrative account with that email already exists.",e.getMessage());
		}
		
		assertNull(admin);
	}
	
	
	@Test
	public void testCreateCustomerProfileWithEmptyPassword() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, "");
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(customer);
		assertEquals(FIRSTNAME, customer.getFirstName());
		assertEquals(LASTNAME, customer.getLastName());
		assertEquals(VALID_CUSTOMER_EMAIL1, customer.getEmail());
		assertEquals(VALID_PHONE_NUMBER, customer.getPhoneNumber());
		assertEquals(null, customer.getPassword());
		assertEquals(PROFILE_ID1, customer.getProfileID());
		assertFalse(customer.getIsRegisteredAccount());
		assertTrue(customer.getVehicle().isEmpty());
	}
	
	@Test
	public void testCreateCustomerProfileWithEmptyPhoneNumber() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, "", VALID_PASSWORD);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(customer);
		assertEquals(FIRSTNAME, customer.getFirstName());
		assertEquals(LASTNAME, customer.getLastName());
		assertEquals(VALID_CUSTOMER_EMAIL1, customer.getEmail());
		assertEquals(null, customer.getPhoneNumber());
		assertEquals(VALID_PASSWORD, customer.getPassword());
		assertEquals(PROFILE_ID1, customer.getProfileID());
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
			customer = service.createCustomerProfile(INVALID_FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid name.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPhoneNumber() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, INVALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid phonenumber.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPassword1() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, INVALID_PASSWORD1);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid password.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPassword2() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, INVALID_PASSWORD2);
		} catch (Exception e) {
			assertEquals("ERROR: Invalid password.",e.getMessage());
		}
		
		assertNull(customer);

	}
	
	@Test
	public void testCreateCustomerProfileWithInvalidPassword3() {
		Customer customer = null;
		try {
			customer = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, INVALID_PASSWORD3);
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
			customer1 = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
			johnDoeIsCreated = true;
			customer2 = service.createCustomerProfile(FIRSTNAME, LASTNAME, VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER, VALID_PASSWORD);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Profile with the provided email exists.",e.getMessage());
		}
		
		assertNull(customer2);
		assertNotNull(customer1);
		assertEquals(FIRSTNAME, customer1.getFirstName());
		assertEquals(LASTNAME, customer1.getLastName());
		assertEquals(VALID_CUSTOMER_EMAIL1, customer1.getEmail());
		assertEquals(VALID_PHONE_NUMBER, customer1.getPhoneNumber());
		assertEquals(VALID_PASSWORD, customer1.getPassword());
		assertEquals(PROFILE_ID1, customer1.getProfileID());
		assertTrue(customer1.getIsRegisteredAccount());
		assertTrue(customer1.getVehicle().isEmpty());

	}
	
	@Test
	public void testGetCustomer() {
		Customer foundCustomer = null;
		try {
			foundCustomer = service.getCustomer(VALID_CUSTOMER_EMAIL2);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(foundCustomer);
		assertEquals(FIRSTNAME, foundCustomer.getFirstName());
		assertEquals(LASTNAME, foundCustomer.getLastName());
		assertEquals(VALID_CUSTOMER_EMAIL2, foundCustomer.getEmail());
		assertEquals(DEFAULT_PHONE_NUMBER, foundCustomer.getPhoneNumber());
		assertEquals("", foundCustomer.getPassword());
		assertEquals(PROFILE_ID2, foundCustomer.getProfileID());
		assertFalse(foundCustomer.getIsRegisteredAccount());
		assertFalse(foundCustomer.getVehicle().isEmpty());
	}
	
	@Test
	public void testGetCustomerNullEmail() {
		Customer customer = null;
		String error = null;
		try {
			customer = service.getCustomer(EMAIL_NULL);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(customer);
		assertNotNull(error);
		assertEquals("ERROR: the customer email is null.", error);
	
	}
	
	@Test
	public void testGetCustomerNotFound() {
		Customer customer = null;
		String error = null;
		try {
			customer = service.getCustomer(VALID_CUSTOMER_EMAIL1);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(customer);
		assertNotNull(error);
		assertEquals("ERROR: the customer cannot be found.", error);
	
	}
	
	@Test
	public void testGetCustomerVehicles() {
		Customer customer = null;
		customer = service.getCustomer(VALID_CUSTOMER_EMAIL2);
		List<Vehicle> vehicles = null;
		
		try {
			vehicles = service.getCustomerVehicles(customer);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(vehicles);
		assertEquals(1, vehicles.size());
		assertEquals(BRAND, vehicles.get(0).getBrand());
		assertEquals(LICENSEPLATE, vehicles.get(0).getLicensePlate());
		assertEquals(MODEL, vehicles.get(0).getModel());
		assertEquals(YEAR, vehicles.get(0).getYear());
	
	}
	
	@Test
	public void testGetCustomerVehiclesNotFound() {
		Customer customer = null;
		String error = null;
		List<Vehicle> vehicles = null;
		try {
			vehicles = service.getCustomerVehicles(customer);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(vehicles);
		assertNotNull(error);
		assertEquals("ERROR: input customer does not exist.", error);
	
	}
	
	@Test
	public void testCreateVehicle() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_CUSTOMER_EMAIL1, LICENSEPLATE, YEAR, MODEL, BRAND);
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
	public void testCreateVehicleCustomerNotExists() {
		Vehicle vehicle = null;
		
		try {
			vehicle = service.createVehicle(INVALID_EMAIL, LICENSEPLATE, YEAR, MODEL, BRAND);
		} catch (Exception e) {
			assertEquals("ERROR: Customer does not exist.", e.getMessage());
		}
		
		assertNull(vehicle);

	}
	
	@Test
	public void testCreateVehicleEmptyLicensePlate() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_CUSTOMER_EMAIL1, "", YEAR, MODEL, BRAND);
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
			vehicle = service.createVehicle(VALID_CUSTOMER_EMAIL1, LICENSEPLATE, YEAR, MODEL, INVALID_BRAND);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Brand name is invalid.", e.getMessage());
		}

		assertNull(vehicle);

	}
	
	@Test
	public void testCreateVehicleInvalidModel() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_CUSTOMER_EMAIL1, LICENSEPLATE, YEAR, "", BRAND);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Model name is invalid.", e.getMessage());
		}

		assertNull(vehicle);

	}
	
	@Test
	public void testCreateVehicleInvalidYear() {
		Vehicle vehicle = null;
		
		try {
			johnDoeIsCreated = true;
			vehicle = service.createVehicle(VALID_CUSTOMER_EMAIL1, LICENSEPLATE, INVALID_YEAR, MODEL, BRAND);
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
			deletedVehicle = service.deleteVehicle(VALID_CUSTOMER_EMAIL2, LICENSEPLATE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertNotNull(deletedVehicle);
		assertEquals(BRAND, deletedVehicle.getBrand());
		assertEquals(LICENSEPLATE, deletedVehicle.getLicensePlate());
		assertEquals(MODEL, deletedVehicle.getModel());
		assertEquals(YEAR, deletedVehicle.getYear());

	}
	
	@Test
	public void testDeleteVehicleCustomerNotExists() {
		Vehicle deletedVehicle = null;
		
		try {
			deletedVehicle = service.deleteVehicle(INVALID_EMAIL, LICENSEPLATE);
		} catch (Exception e) {
			assertEquals("ERROR: Customer does not exist.", e.getMessage());
		}
		
		assertNull(deletedVehicle);

	}
	
	@Test
	public void testEditPassword() {
		Profile profile = null;
		
		try {
			johnDoeIsCreated = true;
			profile = service.editPassword(VALID_CUSTOMER_EMAIL1, VALID_PASSWORD2);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			fail(e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertEquals(VALID_PASSWORD2, profile.getPassword());
		assertTrue(profile.getIsRegisteredAccount());
	}
	
	@Test
	public void testEditPasswordInvalid() {
		Profile profile = null;
		
		try {
			johnDoeIsCreated = true;
			profile = service.editPassword(VALID_CUSTOMER_EMAIL1, INVALID_PASSWORD1);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Invalid password.", e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertNull(profile);
	}
	
	@Test
	public void testEditPasswordForUnregisteredCustomer() {
		Profile profile = null;
		
		try {
			profile = service.editPassword(VALID_CUSTOMER_EMAIL2, VALID_PASSWORD);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertEquals(VALID_PASSWORD, profile.getPassword());
		assertTrue(profile.getIsRegisteredAccount());
	}
	
	@Test
	public void testEditPhoneNumber() {
		Customer customer = null;
		
		try {
			johnDoeIsCreated = true;
			customer = service.editPhoneNumber(VALID_CUSTOMER_EMAIL1, VALID_PHONE_NUMBER2);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			fail(e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertEquals(VALID_PHONE_NUMBER2, customer.getPhoneNumber());
	}
	
	@Test
	public void testEditPhoneNumberInvalid() {
		Customer customer = null;
		
		try {
			johnDoeIsCreated = true;
			customer = service.editPhoneNumber(VALID_CUSTOMER_EMAIL1, INVALID_PHONE_NUMBER);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			assertEquals("ERROR: Invalid phonenumber.", e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertNull(customer);
	}
	
	@Test
	public void testEditPhoneNumberCustomerNotExists() {
		Customer customer = null;
		
		try {
			customer = service.editPhoneNumber(INVALID_EMAIL, VALID_PHONE_NUMBER2);
		} catch (Exception e) {
			assertEquals("ERROR: Customer does not exist.", e.getMessage());
		}
		
		assertNull(customer);
	}
	
	@Test
	public void testDeleteProfile() {
		Profile profile = null;
		
		try {
			johnDoeIsCreated = true;
			profile = service.deleteProfile(VALID_CUSTOMER_EMAIL1);
		} catch (Exception e) {
			johnDoeIsCreated = false;
			fail(e.getMessage());
		}
		
		johnDoeIsCreated = false;
		assertEquals(VALID_CUSTOMER_EMAIL1, profile.getEmail());
	}
	
	@Test
	public void testDeleteProfileNotExists() {
		Profile profile = null;
		
		try {
			profile = service.deleteProfile(INVALID_EMAIL);
		} catch (Exception e) {
			assertEquals("ERROR: Profile does not exist.", e.getMessage());
		}
		
		assertNull(profile);
	}
	
	@Test
	public void testGetProfileNotExists() {
		Profile profile = null;
		
		try {
			profile = service.getProfile(INVALID_EMAIL);
		} catch (Exception e) {
			assertEquals("ERROR: Profile does not exist.", e.getMessage());
		}
		
		assertNull(profile);
	}

}
