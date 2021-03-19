package ca.mcgill.ecse321.isotopecr.service;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	/**
	 * Configuration for sending emails
	 * 
	 * @author Victoria
	 * @return JavaMailSenderImpl
	 */
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("gmail@gmail.com");
		mailSender.setPassword("gmail password");

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;
	}

}
