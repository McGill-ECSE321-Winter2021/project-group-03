package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.isotopecr.dao.AppointmentRepository;
import ca.mcgill.ecse321.isotopecr.dao.CustomerRepository;
import ca.mcgill.ecse321.isotopecr.dao.ServiceRepository;
import ca.mcgill.ecse321.isotopecr.model.*;

@Service
@EnableScheduling
public class EmailService {

	@Autowired
    private JavaMailSender javaMailSender;
    
    private String serviceEmail = "${spring.mail.username}";
    
//    @Value("${baseurl}")
//    private String url;
//    

    
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

    public void sendInvoice(Admin admin, Appointment appointment) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(serviceEmail);
        msg.setTo(appointment.getCustomer().getEmail());
        msg.setText(
        		"Dear " + appointment.getCustomer().getFirstName() + " "
                + appointment.getCustomer().getLastName()
                + ",/nThank you for your business./n"
                + "_______________________________________/n"
                + " Invoice Summary /n" 
                + "_______________________________________/n"
                + " Invoice ID: " + appointment.getInvoice().getInvoiceID()
        		+ " Amount: $" + appointment.getInvoice().getCost());
        try{
        	javaMailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

    public void sendReminder(Customer customer, ca.mcgill.ecse321.isotopecr.model.Service service) {
    	SimpleMailMessage msg = new SimpleMailMessage();
    	msg.setFrom(serviceEmail);
        msg.setTo(customer.getEmail());
        msg.setText(
        		"Dear " + customer.getFirstName() + " "
                + customer.getLastName()
                + ",/nThis is a reminder to schedule your next appointment for a "
                + service.getServiceName());
        try{
        	javaMailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
    
    @Scheduled(cron = "0 30 9 1 * *")
    public void checkForServiceReminders() {
    	for(Customer customer : customerRepository.findAll()) {
    		for(ca.mcgill.ecse321.isotopecr.model.Service service : serviceRepository.findServiceByFrequencyGreaterThan(0)) {
    			for(Appointment appointment: appointmentRepository.findAppointmentByCustomerAndService(customer, service)) {
    				Period diff = Period.between(
    						appointment.getTimeslot().iterator().next().getDate().toLocalDate().withDayOfMonth(1),
    			            LocalDate.now().withDayOfMonth(1));
    				if(diff.getMonths() == service.getFrequency()) {
    					sendReminder(customer, service);
    				}
    			}
    		}
    	}
    }
    
    public void notifyTechnician(Appointment appointment) {
    	SimpleMailMessage msg = new SimpleMailMessage();
    	msg.setFrom(serviceEmail);
        msg.setTo(appointment.getTechnician().getEmail());
        msg.setText(
        		"Dear " + appointment.getTechnician().getFirstName() + " "
                + appointment.getTechnician().getLastName()
                + ",/n"
                + "_______________________________________/n"
                + " Appointment Notification /n" 
                + "_______________________________________/n"
                + " New Upcoming Appointment /n"
                + " Date: " + appointment.getTimeslot().iterator().next().getDate() + "/n"
                + " Time: " + appointment.getTimeslot().iterator().next().getTime() + "/n"
                + " Service: " + appointment.getService().getServiceName());
        try{
        	javaMailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Sends email confirmation for donation
     *
     * @param email    user's email
     * @param username user's username
     * @param amount   user's donation amount
     * @param time     donation time
     * @param date     donation date
     */
    public void donationConfirmationEmail(String email, String username, double amount, Time time, Date date) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(serviceEmail);
        msg.setTo(email);
    
        msg.setSubject("Pet Shelter - Donation Confirmation");
        msg.setText("Hey " + username + " , \n\n" + "Thank you for your donation. We have successfully processed it at " +
                time + " EST " + date + " for an amount of: " + currencyFormat.format(amount) + "\n\nYours, \nPetShelter");
        
        javaMailSender.send(msg);
    }
    
    /**
     * Sends email to confirm user's email address
     *
     * @param email    user's email
     * @param username user's username
     * @param token    randomly generated token for confirmation
     */
    public void userCreationEmail(String email, String username, String token) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(serviceEmail);
        msg.setTo(email);
        msg.setSubject("Pet Shelter - Account Creation Verification for " + username);
        msg.setText("Hey " + username + " ,\n\nPlease confirm your email here: " + /*url +*/ "api/user/registrationConfirmation?token=" + token + "\n\nYours, \nPetShelter");
        javaMailSender.send(msg);
    }
    
    /**
     * Sends temporary password to user
     *
     * @param email    user's email
     * @param tempPw   user's temporary password
     * @param username user's username
     */
    public void userForgotPasswordEmail(String email, String tempPw, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Pet Shelter - Password Reset");
        msg.setText("Hey " + username + " ,\n\nHere is your temporary password " + tempPw + "\n\nYours, \nPetShelter");
        javaMailSender.send(msg);
    }
    
}