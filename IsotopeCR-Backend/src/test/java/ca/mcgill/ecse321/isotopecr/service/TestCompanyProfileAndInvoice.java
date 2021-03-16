package ca.mcgill.ecse321.isotopecr.service;

import java.util.List;

import ca.mcgill.ecse321.isotopecr.model.Invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.CompanyProfileRepository;
import ca.mcgill.ecse321.isotopecr.dao.InvoiceRepository;
import ca.mcgill.ecse321.isotopecr.model.CompanyProfile;

@ExtendWith(MockitoExtension.class)
public class TestCompanyProfileAndInvoice {
	
	@Mock
	private CompanyProfileRepository companyProfileRepository;
	@Mock
	private InvoiceRepository invoiceRepository;
	
	@InjectMocks
	private AutoRepairShopService autoRepairShopService;
	
	/* Mock up Services */
	//CompanyProfile 1
	private static final String COMPANY_NAME1 = "IsotopeCR";
	private static final String ADDRESS1 = "845 Sherbrooke St W";
	private static final String WORKING_HOURS1 = "MondayToFriday";
	
	//CompanyProfile 2
	private static final String COMPANY_NAME2 = "IsotopeCR";
	private static final String ADDRESS2 = "847 Sherbrooke St W";
	private static final String WORKING_HOURS2 = "MondayToFriday";
	
	//Invoice 1
	private static final String INVOICEID1 = "invoice1";
	private static final double COST1 = 250.75;
	private static final Boolean ISPAID1 = true;
	
	//Invoice 2
	private static final String INVOICEID2 = "invoice2";
	private static final double COST2 = 123.45;
	private static final Boolean ISPAID2 = true;
	
	//Invoice 3
	private static final String INVOICEID3 = "invoice3";
	private static final double COST3 = 455.25;
	private static final Boolean ISPAID3 = true;
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(companyProfileRepository.findCompanyProfileByAddress(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(ADDRESS1)) {
				CompanyProfile companyProfile = new CompanyProfile();
				companyProfile.setCompanyName(COMPANY_NAME1);
				companyProfile.setWorkingHours(WORKING_HOURS1);
				companyProfile.setAddress(ADDRESS1);
				return companyProfile;
			}else {
				return null;
			}
		});
		
		lenient().when(invoiceRepository.findInvoiceByInvoiceID(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(INVOICEID1)) {
				Invoice invoice = new Invoice();
				invoice.setCost(COST1);
				invoice.setIsPaid(ISPAID1);
				invoice.setIsPaid(ISPAID1);
				return invoice;
			}else {
				return null;
			}
		});
		
	}
	
	@Test
	public void testCreateCompanyProfile() {
		CompanyProfile companyProfile = null;
		
		try {
			companyProfile = autoRepairShopService.createCompanyProfile(COMPANY_NAME1, ADDRESS1, WORKING_HOURS1);
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(companyProfile);
		assertEquals(COMPANY_NAME1, companyProfile.getCompanyName());
		assertEquals(ADDRESS1, companyProfile.getAddress());
		assertEquals(WORKING_HOURS1, companyProfile.getWorkingHours());
	}
	
	@Test
	public void testEditCompanyProfile() {
		CompanyProfile companyProfileOld = null;
		CompanyProfile companyProfileNew = null;
		
		try {
			companyProfileOld = autoRepairShopService.createCompanyProfile(COMPANY_NAME1, ADDRESS1, WORKING_HOURS1);
			companyProfileNew = autoRepairShopService.editCompanyProfile(COMPANY_NAME1, ADDRESS2, WORKING_HOURS1);
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(companyProfileNew);
		assertEquals(COMPANY_NAME1, companyProfileNew.getCompanyName());
		assertEquals(ADDRESS2, companyProfileNew.getAddress());
		assertEquals(WORKING_HOURS1, companyProfileNew.getWorkingHours());
	}
	
	@Test
	public void testGetAllCompanyProfiles() {
		CompanyProfile companyProfile1 = null;
		CompanyProfile companyProfile2 = null;
		
		companyProfile1 = autoRepairShopService.createCompanyProfile(COMPANY_NAME1, ADDRESS1, WORKING_HOURS1);
		companyProfile2 = autoRepairShopService.createCompanyProfile(COMPANY_NAME2, ADDRESS2, WORKING_HOURS2);
		
		List<CompanyProfile> companyProfiles = autoRepairShopService.getCompanyProfiles();
		
		companyProfiles.add(companyProfile1);
		companyProfiles.add(companyProfile2);
		
		assertNotNull(companyProfiles);
		assertEquals(2, companyProfiles.size());
		assertEquals(ADDRESS1, companyProfiles.get(0).getAddress());
		assertEquals(ADDRESS2, companyProfiles.get(1).getAddress());
		
	}
	
	@Test
	public void testCreateInvoice() {
		Invoice invoice = null;
		
		try {
			invoice = autoRepairShopService.createInvoice(INVOICEID1, COST1, ISPAID1);
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(invoice);
		assertEquals(INVOICEID1, invoice.getInvoiceID());
		assertEquals(COST1, invoice.getCost());
		assertEquals(ISPAID1, invoice.getIsPaid());
	}
	
	@Test
	public void testGetAllInvoices() {
		Invoice invoice1 = null;
		Invoice invoice2 = null;
		
		invoice1 = autoRepairShopService.createInvoice(INVOICEID1, COST1, ISPAID1);
		invoice2 = autoRepairShopService.createInvoice(INVOICEID2, COST2, ISPAID2);
		
		List<Invoice> invoices = autoRepairShopService.getAllInvoices();
		
		invoices.add(invoice1);
		invoices.add(invoice2);
		
		assertNotNull(invoices);
		assertEquals(2, invoices.size());
		assertEquals(INVOICEID1, invoices.get(0).getInvoiceID());
		assertEquals(INVOICEID2, invoices.get(1).getInvoiceID());
		
		
		
	}
	
}
