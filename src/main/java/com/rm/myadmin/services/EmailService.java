package com.rm.myadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public String sendEmail(String recipient, String subject, String msg) {
		try {
			SimpleMailMessage smm = new SimpleMailMessage();
			smm.setFrom(sender);
			smm.setTo(recipient);
			smm.setSubject(subject);
			smm.setText(msg);
			javaMailSender.send(smm);
			return "Email successfully sent: " + recipient;
		} catch (Exception e) {
			return "Error when trying to send the email: " + e.getLocalizedMessage();
		}
	}
}
