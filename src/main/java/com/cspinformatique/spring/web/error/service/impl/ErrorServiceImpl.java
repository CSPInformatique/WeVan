package com.cspinformatique.spring.web.error.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.JsError;
import com.cspinformatique.spring.web.error.service.ErrorService;

@Service
@PropertySource("classpath:error.properties")
public class ErrorServiceImpl implements ErrorService {
	private static final String PROP_EMAIL_SENDER = "error.mail.sender";
	private static final String PROP_EMAIL_RECEIVER = "error.mail.receiver";
	
	@Autowired private JavaMailSender javaMailSender;
	
	@Resource private Environment env;
	
	private String hostname;
	
	public ErrorServiceImpl(){
		try {
			this.hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException unknownHostEx) {
			throw new RuntimeException(unknownHostEx);
		}
	}
	
	@Override
	public void sendEmailAlert(Exception exception){
		// TODO - Implement velocity for error generation.
		StringBuffer mailContent = new StringBuffer();

		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		mailContent.append("<html>");
		mailContent.append("<body>");
		mailContent.append("<h1>An error occured on server " + this.hostname + " at " + date + "</h1>");
		mailContent.append("<h3>Error detail</h3>");
		mailContent.append("<p>");
		
		// TODO - Implement the algorithm for full exception print.

		mailContent.append("</p");
		mailContent.append("</body>");
		mailContent.append("</html>");
		
		this.sendEmailAlert(date + " - Server " + hostname + " error report.", mailContent.toString());
	}
	
	@Override
	public void sendEmailAlert(JsError jsError) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		// TODO - Implement velocity for error generation.
		StringBuffer mailContent = new StringBuffer();

		mailContent.append("<html>");
		mailContent.append("<body>");
		mailContent.append("<h1>An error occured on client " + jsError.getUserIp() + " at " + date + "</h1>");
		mailContent.append("<h3>User details</h3>");
		mailContent.append("<p>");
		mailContent.append("<div>IP address : " + jsError.getUserIp() + "</div>");
		mailContent.append("<div>User agent : " + jsError.getUserAgent()+ "</div>");
		mailContent.append("<div>OS : " + jsError.getUserOs() + "</div>");
		mailContent.append("</p");
		mailContent.append("<h3>Error details</h3>");
		mailContent.append("<p>");
		mailContent.append("<div>" + jsError.getMessage() + " at " + jsError.getSource() + " line " + jsError.getLine() + "</div>");
		mailContent.append("</p>");
		mailContent.append("</body>");
		mailContent.append("</html>");
		
		this.sendEmailAlert(date + " - Client " + jsError.getUserIp() + " error report.", mailContent.toString());
	}
	
	private void sendEmailAlert(String subject, String alertContent){
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(env.getRequiredProperty(PROP_EMAIL_SENDER));
		message.setTo(env.getRequiredProperty(PROP_EMAIL_RECEIVER));
		
		message.setSubject(subject);
		
		message.setText(alertContent);
		
		this.javaMailSender.send(message);
	}

}
