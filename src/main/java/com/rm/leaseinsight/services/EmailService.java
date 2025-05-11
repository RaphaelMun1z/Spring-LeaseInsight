package com.rm.leaseinsight.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.entities.enums.TemplatesEnum;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	@Transactional
	public void sendEmail(TemplatesEnum type, String recipientName, String recipientEmail, String subject) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(sender);
			helper.setTo(recipientEmail);
			helper.setSubject(subject);

			String template = loadTemplate(type);
			template = template.replace("#{name}", recipientName);

			helper.setText(template, true);

			helper.addInline("Logo1", new ClassPathResource("/emailTemplates/logo1.png"));
			helper.addInline("Logo2", new ClassPathResource("/emailTemplates/logo2.png"));

			javaMailSender.send(message);
			System.out.println("Email successfully sent: " + recipientEmail);
		} catch (Exception e) {
			System.out.println("Error when trying to send the email: " + e.getLocalizedMessage());
		}
	}

	@Transactional
	public void sendPasswordEmail(TemplatesEnum type, String recipientName, String recipientEmail, String subject,
			String password) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(sender);
			helper.setTo(recipientEmail);
			helper.setSubject(subject);

			String template = loadTemplate(type);
			template = template.replace("#{name}", recipientName);
			template = template.replace("#{pass-1}", String.valueOf(password.charAt(0)));
			template = template.replace("#{pass-2}", String.valueOf(password.charAt(1)));
			template = template.replace("#{pass-3}", String.valueOf(password.charAt(2)));
			template = template.replace("#{pass-4}", String.valueOf(password.charAt(3)));
			template = template.replace("#{pass-5}", String.valueOf(password.charAt(4)));
			template = template.replace("#{pass-6}", String.valueOf(password.charAt(5)));

			helper.setText(template, true);

			helper.addInline("Logo1", new ClassPathResource("/emailTemplates/logo1.png"));
			helper.addInline("Logo2", new ClassPathResource("/emailTemplates/logo2.png"));

			javaMailSender.send(message);
			System.out.println("Email successfully sent: " + recipientEmail);
		} catch (Exception e) {
			System.out.println("Error when trying to send the email: " + e.getLocalizedMessage());
		}
	}

	public String loadTemplate(TemplatesEnum template) throws IOException {
		ClassPathResource resource;

		switch (template) {
		case TemplatesEnum.WELCOME: {
			resource = new ClassPathResource("/emailTemplates/welcome/welcome.html");
			return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
		}
		case TemplatesEnum.INVOICE: {
			resource = new ClassPathResource("/emailTemplates/invoice/invoice.html");
			return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
		}
		case TemplatesEnum.NEW_ACCOUNT: {
			resource = new ClassPathResource("/emailTemplates/newAccount/newAccount.html");
			return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + template);
		}
	}
}
