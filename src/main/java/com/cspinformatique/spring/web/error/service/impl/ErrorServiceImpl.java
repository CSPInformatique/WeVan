package com.cspinformatique.spring.web.error.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cspinformatique.spring.web.error.entity.JsError;
import com.cspinformatique.spring.web.error.service.ErrorService;

@Service
@PropertySource("classpath:error.properties")
public class ErrorServiceImpl implements ErrorService {
	private static final Logger logger = LoggerFactory.getLogger(ErrorServiceImpl.class);
	
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
		StringBuffer mailContent = new StringBuffer();
		StringWriter exceptionWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(exceptionWriter);
		
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		mailContent.append("<html>");
		mailContent.append("<body>");
		mailContent.append("<h1>An error occured on server " + this.hostname + " at " + date + "</h1>");
		mailContent.append("<h3>Error detail</h3>");
		mailContent.append("<p>");
		
		exception.printStackTrace(printWriter);
		
		mailContent.append(exceptionWriter.toString().replaceAll("\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"));
		
		mailContent.append("</p>");
		mailContent.append("</body>");
		mailContent.append("</html>");
		
		this.sendEmailAlert(date + " - Server " + hostname + " error report.", mailContent.toString());
	}
	
	@Override
	public void sendEmailAlert(JsError jsError) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
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
		mailContent.append("<div>" + jsError.getMessage() + " at " + jsError.getFileName() + " line " + jsError.getLineNumber() + " column " + jsError.getColumnNumber() + ".</div>");
		mailContent.append("</p>");
		mailContent.append("</body>");
		mailContent.append("</html>");
		
		this.sendEmailAlert(date + " - Client " + jsError.getUserIp() + " error report.", mailContent.toString());
	}
	
	private void sendEmailAlert(String subject, String alertContent){
		try{
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			
			helper.setFrom(env.getRequiredProperty(PROP_EMAIL_SENDER));
			helper.setTo(env.getRequiredProperty(PROP_EMAIL_RECEIVER));
			helper.setSubject(subject);
			helper.setText(alertContent, true);
			
			final MimeMessage messageForThread = mimeMessage;
			
			// Asynchronous send.
			new Thread(new Runnable() {
				@Override
				public void run() {
					javaMailSender.send(messageForThread);
				}
			}).start();
		}catch(Exception exception){
			logger.error("Email could not be sent.", exception);
		}
	}
}
