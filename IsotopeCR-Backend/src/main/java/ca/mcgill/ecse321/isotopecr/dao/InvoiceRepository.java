package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.model.Appointment;
import ca.mcgill.ecse321.model.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice,String> {

	Invoice findInvoiceByInvoiceID(String invoiceID);
	
	Invoice findByAppointment(Appointment appointment);
}
