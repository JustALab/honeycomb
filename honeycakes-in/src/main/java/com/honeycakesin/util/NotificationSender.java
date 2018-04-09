package com.honeycakesin.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.honeycakesin.constants.NotificationDeliveryType;
import com.honeycakesin.constants.NotificationStatus;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotificationSender {

	@Value("${honeycakes.sms}")
	private boolean sendSms;

	@Value("${honeycakes.email}")
	private boolean sendEmail;

	@Value("${honeycakes.push}")
	private boolean sendPush;

	@Value("${honeycakes.sms.apikey}")
	private String smsApiKey;

	@Value("${honeycakes.sms.sender}")
	private String sender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	EmailService emailService;

	public NotificationStatus notifyUser(String recipient, NotificationDeliveryType notificationDeliveryType,
			String subject, String message) {

		switch (notificationDeliveryType) {
		case SMS:
			if (sendSms) {
				return sendSms(recipient, message);
			}
			log.warn("SMS sending blocked in application.properties");
			return NotificationStatus.BLOCKED;
		case EMAIL:
			if (sendEmail) {
				return sendEmail(recipient, subject, message);
			}
			log.warn("Email sending blocked in application.properties");
			return NotificationStatus.BLOCKED;
		case PUSH:
			if (sendPush) {
				return sendPush(message);
			}
			log.warn("Push notifications blocked in application.properties");
			return NotificationStatus.BLOCKED;
		}

		return null;
	}

	private NotificationStatus sendSms(String mobile, String message) {
		try {
			// sms sending
			log.info("SMS notification message --> {}", message);
			return NotificationStatus.SUCCESS;
		} catch (Exception exception) {
			log.error(exception.toString());
			return NotificationStatus.FAILURE;
		}
	}

	private NotificationStatus sendEmail(String toEmail, String subject, String message) {
		try {
			final Email email = DefaultEmail.builder().from(new InternetAddress(fromEmail, "Honeycakes"))
					.to(Arrays.asList(new InternetAddress(toEmail, "one"))).subject(subject).body(message)
					.encoding("UTF-8").build();
			emailService.send(email);
			return NotificationStatus.SUCCESS;
		} catch (UnsupportedEncodingException e) {
			log.error(e.toString());
			return NotificationStatus.FAILURE;
		}
	}

	private NotificationStatus sendPush(String message) {
		return NotificationStatus.SUCCESS;
	}

}
