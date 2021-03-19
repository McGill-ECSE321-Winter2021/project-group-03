package ca.mcgill.ecse321.isotopecr.service;

import java.util.List;

import ca.mcgill.ecse321.isotopecr.model.Invoice;

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

import ca.mcgill.ecse321.isotopecr.dao.CompanyProfileRepository;
import ca.mcgill.ecse321.isotopecr.dao.InvoiceRepository;

@ExtendWith(MockitoExtension.class)
public class TestInvoice {

	@Mock
	private CompanyProfileRepository companyProfileRepository;
	@Mock
	private InvoiceRepository invoiceRepository;

	@InjectMocks
	private AutoRepairShopService autoRepairShopService;

	/* Mock up Services */

	// Invoice 1
	private static final String INVOICEID1 = "invoice1";
	private static final double COST1 = 250.75;
	private static final Boolean ISPAID1 = true;

	// Invoice 2
	private static final String INVOICEID2 = "invoice2";
	private static final double COST2 = 123.45;
	private static final Boolean ISPAID2 = true;

	private static final double INVALID_COST = Math.pow(10, 10);

	@BeforeEach
	public void setMockOutput() {

		lenient().when(invoiceRepository.findInvoiceByInvoiceID(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(INVOICEID1)) {
						Invoice invoice = new Invoice();
						invoice.setCost(COST1);
						invoice.setIsPaid(ISPAID1);
						invoice.setIsPaid(ISPAID1);
						return invoice;
					} else {
						return null;
					}
				});

	}

	@Test
	public void testCreateInvoice() {
		Invoice invoice = null;

		try {
			invoice = autoRepairShopService.createInvoice(INVOICEID1, COST1, ISPAID1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(invoice);
		assertEquals(INVOICEID1, invoice.getInvoiceID());
		assertEquals(COST1, invoice.getCost());
		assertEquals(ISPAID1, invoice.getIsPaid());
	}

	@Test
	public void testCreateInvoiceInvalidCost() {
		Invoice invoice = null;

		try {
			invoice = autoRepairShopService.createInvoice(INVOICEID1, INVALID_COST, ISPAID1);
		} catch (IllegalArgumentException e) {
			assertEquals("ERROR: Unable to create Invoice, invalid cost.", e.getMessage());
		}

		assertNull(invoice);

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
