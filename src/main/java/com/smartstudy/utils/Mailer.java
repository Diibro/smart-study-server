package com.smartstudy.utils;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mailer {
	public static boolean send(String email, String message, String subject) {
		String host = "smtp.gmail.com";
		String port = "587";
		String username = "smartstudy.plat@gmail.com";
		String password = "kvoj hxbu ttqc voqn";
		
// 		Properties props = new Properties();
// 		props.put("mail.smtp.host", host);
// 		props.put("mail.smtp.port", port);
// 		props.put("mail.smtp.auth", true);
// 		props.put("mail.smtp.starttls.enable",true);
// //		props.put("mail.debug", true);
// //		props.put("mail.smtp.ssl.protocols", "TLSv1.3");
// //		props.put("mail.smtp.ssl.ciphersuites", "TLS_AES_128_GCM_SHA256");
// 		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
          // for gpt
          Properties props = new Properties();
          props.put("mail.smtp.host", host);
          props.put("mail.smtp.port", port);
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
          props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
          props.put("mail.debug", "true");

		// Session session = Session.getInstance(props, new Authenticator() {
		// 	protected PasswordAuthentication getPasswordAuthentication() {
		// 		return new PasswordAuthentication(username, password);
		// 	}
		// });

          //for gpt
          Session session = Session.getInstance(props, new Authenticator() {
               @Override
               protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
               }
          });
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject(subject);
			msg.setText(message);
			
			Transport.send(msg);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
