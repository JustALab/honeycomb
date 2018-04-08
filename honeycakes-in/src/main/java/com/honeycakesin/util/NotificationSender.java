package com.honeycakesin.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.honeycakesin.constants.NotificationDeliveryType;
import com.honeycakesin.constants.NotificationStatus;

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

	@Value("${honeycakes.smsapikey}")
	private String smsApiKey;

	@Value("${honeycakes.sender}")
	private String sender;

	public NotificationStatus notifyUser(NotificationDeliveryType notificationDeliveryType, String message) {

		switch (notificationDeliveryType) {
		case SMS:
			if (sendSms) {
				return sendSms(message);
			}
			log.warn("SMS sending blocked in application.properties");
			return NotificationStatus.BLOCKED;
		case EMAIL:
			if (sendEmail) {
				return sendEmail(message);
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

	private NotificationStatus sendSms(String message) {
		try {
			// sms sending
			log.info("SMS notification message --> {}", message);
			return NotificationStatus.SUCCESS;
		} catch (Exception exception) {
			log.error(exception.toString());
			return NotificationStatus.FAILURE;
		}
	}

	private NotificationStatus sendEmail(String message) {
		return NotificationStatus.SUCCESS;
	}

	private NotificationStatus sendPush(String message) {
		return NotificationStatus.SUCCESS;
	}

}
