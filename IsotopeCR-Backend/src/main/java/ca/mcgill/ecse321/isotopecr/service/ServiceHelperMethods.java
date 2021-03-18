package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.Resource;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;

public class ServiceHelperMethods {

	public static <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	/**
	 * this method checks if the customer is null or not
	 * 
	 * @author Jiatong
	 * @param customer
	 * @return a boolean indicates whether the customer is valid
	 */
	public static boolean isValidCustomer(Customer customer) {
		boolean isValid = false;
		if (customer != null) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * this method checks if the technician is null or not
	 * 
	 * @author Jiatong
	 * @param technician
	 * @return a boolean indicates whether the technician is valid
	 */
	public static boolean isValidTechnician(Technician technician) {
		boolean isValid = false;
		if (technician != null) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * this method checks if the vehicle is null or not
	 * 
	 * @author Jiatong
	 * @param vehicle
	 * @return a boolean indicates whether the vehicle is valid
	 */
	public static boolean isValidVehicle(Vehicle vehicle) {
		boolean isValid = false;
		if (vehicle != null) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * this method checks if the service is null or not
	 * 
	 * @author Jiatong
	 * @param service
	 * @return
	 */
	public static boolean isValidService(ca.mcgill.ecse321.isotopecr.model.Service service) {
		boolean isValid = false;
		if (service != null) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidFrequency(Integer frequency) {
		boolean isValid = false;
		if (frequency >= 0) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isBeforeADay(Date date) {
		Date curDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

		boolean isBefore = false;
		if (curDate.before(date)) {
			isBefore = true;
		}

		return isBefore;
	}

	public static boolean isValidResource(Resource resource) {
		boolean isValid = false;
		if (resource != null) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidServiceName(String serviceName) {
		String regex = "^[a-zA-Z-\s]*[a-zA-Z-]+$";
		// only letters and - allowed, only one space between words allowed
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(serviceName);
		return matcher.matches();
	}

	public static boolean isValidDuration(int duration) {
		boolean isValid = false;
		if (duration % 30 == 0) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidPrice(double price) {
		boolean isValid = false;
		if (price > 0.0 && price < 1000000.0) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidCompanyName(String companyName) {
		String regex = "^[A-Z]([a-zA-Z0-9]|[- @\\.#&!]){1,20}$";
		// contains any kind of letter from any language
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(companyName);
		return matcher.matches();
	}

	/**
	 * This helper method checks if the input email address satisfies a standard
	 * email address format. The format must follow RFC 5322 and there should be no
	 * no leading, trailing, or consecutive dots. The domain name must include at
	 * least one dot, and the part of the domain name after the last dot can only
	 * consist of letters. The method returns a boolean value.
	 * 
	 * @param email
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidEmail(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		// format permitted by RFC 5322, no leading, trailing, or consecutive dots.
		// domain name must include at least one dot, and the part of the domain name
		// after the last dot can only consist of letters.
		// for example: john.doe@mail.mcgill.ca
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * This helper method checks if the email address provided (must be a valid
	 * email address), is a company email. A company email follows the format
	 * xxxxx@isotopecr.ca. The method returns a boolean value.
	 * 
	 * @param validEmail
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidCompanyEmail(String validEmail) {
		String regex = "^[\\w.+\\-]+@isotopecr\\.ca$";
		// email contains "isotopecr.ca"
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(validEmail);
		return matcher.matches();
	}

	/**
	 * This helper method checks if a name (can be first name or last name) is
	 * valid, i.e. contains any kind of letter from any language and can include
	 * hyphens, dots or apostrophes. The method returns a boolean value.
	 * 
	 * @param name
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidName(String name) {
		String regex = "^[\\p{L} .'-]+$";
		// contains any kind of letter from any language
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

	/**
	 * This helper method checks if a password is valid, i.e. 8 to 20 characters,
	 * one upper case, one lower case, one digit, contains no whitespace. The method
	 * returns a boolean value.
	 * 
	 * @param password
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
		// format: Password between 8 to 20 characters. At least one upper case, one
		// lower case, one digit, no whitespace.
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	/**
	 * This helper method checks if a phoneNumber is valid, i.e. standard phone
	 * number format, can include white spaces, dots, hyphens or parenthesis between
	 * consecutive numbers. The number can also include an international prefix. The
	 * method returns a boolean value.
	 * 
	 * @param phoneNumber
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // Number with whitespace,
																								// dots or hyphens
				+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" // Number with parentheses
				+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"; // Number with international prefix
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	/**
	 * This helper method checks if the brand name of a vehicle is valid, i.e. only
	 * letters and hyphen allowed and can consist only one space between words. This
	 * method returns a boolean value.
	 * 
	 * @param brand
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidBrandName(String brand) {
		String regex = "^[a-zA-Z-\s]*[a-zA-Z-]+$";
		// only letters and - allowed, only one space between words allowed
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(brand);
		return matcher.matches();
	}

	/**
	 * This helper method checks if the model name of vehicle is valid,i.e
	 * containing 1 to 30 characters. This method returns a boolean value.
	 * 
	 * @param model
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidModelName(String model) {
		String regex = "^.{1,30}$"; // 1 to 30 characters
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(model);
		return matcher.matches();
	}

	/**
	 * This helper method checks if the year of a vehicle is valid. The year must be
	 * between 1900 to 3000. This method returns a boolean value.
	 * 
	 * @param year
	 * 
	 * @author Jack Wei
	 */
	public static boolean isValidYear(String year) {
		boolean isValid = false;

		try {
			if (1900 <= Integer.parseInt(year) && Integer.parseInt(year) <= 3000) { // between 1900 and 3000 for any car
				isValid = true;
			}
		} catch (NumberFormatException e) {
			throw e;
		}

		return isValid;
	}

}
