package com.cspinformatique.google;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.AclRule.Scope;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class GoogleCalendar {
	private static final Logger logger = LoggerFactory.getLogger(GoogleCalendar.class);
	
	private String applicationName;
	private String serviceAccountEmail;
	private String p12FilePath;
	
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private com.google.api.services.calendar.Calendar calendarService;
	private Credential credential;
	private HttpTransport httpTransport;

	public GoogleCalendar(String applicationName, String serviceAccountEmail, String p12FilePath){
		this.applicationName = applicationName;
		this.serviceAccountEmail = serviceAccountEmail;
		this.p12FilePath = p12FilePath;
	}
	
	private void addRuleToCalendar(String calendarId, String ownerAccountEmail){
		this.manageCredentials();
		
		AclRule aclRule = new AclRule();
		aclRule.setRole("owner");
		
		Scope scope = new Scope();
		scope.setType("user");
		scope.setValue(ownerAccountEmail);
		
		aclRule.setScope(scope);
		
		int retryCount = 5;
		boolean completed = false;
		do{
			try {
				this.calendarService.acl().insert(calendarId, aclRule).execute();
				
				completed = true;
			} catch (IOException ioEx) {
				if(retryCount > 0){
					--retryCount;
				}else{
					throw new RuntimeException(ioEx);	
				}
			}
		}while(!completed);
	}
	
	private void authorize(){
		try{
			this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			
	        // service account credential (uncomment setServiceAccountUser for domain-wide delegation)
			this.credential =	new GoogleCredential.Builder().
	    		   					setTransport(this.httpTransport).
	    		   					setJsonFactory(JSON_FACTORY).
	    		   					setServiceAccountId(this.serviceAccountEmail).
	    		   					setServiceAccountScopes(CalendarScopes.all()).
	    		   					setServiceAccountPrivateKeyFromP12File(
	    		   						new File(GoogleCalendar.class.getClassLoader().getResource(p12FilePath).getFile())
	    		   					).build();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public String createCalendar(String summary, String description, String ownerAccountEmail){
		this.manageCredentials();
		
		Calendar calendar = new Calendar();
		
		calendar.setSummary(summary);
		calendar.setDescription(description);
		
		try {
			calendar = calendarService.calendars().insert(calendar).execute();
			
			this.addRuleToCalendar(calendar.getId(), ownerAccountEmail);
		} catch (IOException ioEx) {
			throw new RuntimeException(ioEx);
		}
		
		logger.info("Calendar " + calendar.getId() + " created.");
		
		return calendar.getId();
	}
	
	public String createEvent(String calendarId, String summary, Date startDate, Date endDate){
		this.manageCredentials();
		
		Event event = new Event();
	    event.setSummary(summary);
	    DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
	    event.setStart(new EventDateTime().setDateTime(start));
	    DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
	    event.setEnd(new EventDateTime().setDateTime(end));
		
	    int retryCount = 5;
	    boolean completed = false;
	    do{
			try {
				event = this.calendarService.events().insert(calendarId, event).execute();
				
				completed = true;
			} catch (IOException ioEx) {
				if(retryCount > 0){
					--retryCount;
				}else{
					throw new RuntimeException(ioEx);					
				}
			}
	    }while(!completed);
	    
		return event.getId();
	}
	
	public void deleteCalendar(String calendarId){
		manageCredentials();
		
		try {
			this.calendarService.calendars().delete(calendarId).execute();
		} catch (IOException ioEx) {
			throw new RuntimeException(ioEx);
		}

		logger.info("Calendar " + calendarId + " deleted.");
	}
	
	public void deleteEvent(String calendarId, String eventId){
		this.manageCredentials();
		
		try {
			this.calendarService.events().delete(calendarId, eventId).execute();
		} catch (IOException ioEx) {
			if(ioEx instanceof GoogleJsonResponseException && ((GoogleJsonResponseException)ioEx).getStatusCode() == 410){
				logger.warn("Calendar event " + eventId + " has already been deleted.");
			}else{
				throw new RuntimeException(ioEx);				
			}
		}
	}
	
	public List<String> findAllCalendar(){
		manageCredentials();
		
		try{
			List<String> calendarsIds = new ArrayList<String>();
			
			CalendarList calendarList = null;
			String nextToken = null;
			do{
				calendarList = this.calendarService.calendarList().list().setPageToken(nextToken).execute();
				
				nextToken = calendarList.getNextPageToken();
				
				for(CalendarListEntry entry : calendarList.getItems()){
					calendarsIds.add(entry.getId());
				}
			}while(calendarList.getItems().size() != 0 && nextToken != null);
			
			return calendarsIds;
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
	
	public boolean isCalendarActive(String calendarId, String ownerAccountEmail){
		try{
			for(AclRule rule : this.calendarService.acl().list(calendarId).execute().getItems()){
				if(rule.getScope().getValue().equals(ownerAccountEmail)){
					return true;
				}
			}
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
		
		return false;
	}
	
	public void manageCredentials(){
		try{
			if(this.credential == null){
				authorize();
			}else if(this.credential.getExpiresInSeconds() < 60){
				if(!this.credential.refreshToken()){
					authorize();
				};
			}
			
			this.calendarService =	new com.google.api.services.calendar.Calendar.Builder(
										httpTransport, 
										JSON_FACTORY, 
										credential
									).setApplicationName(
										this.applicationName
									).build();
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
	
	public void updateEvent(String eventId, String calendarId, String summary, Date startDate, Date endDate){
		this.manageCredentials();
		
		Event event = new Event();
	    event.setSummary(summary);
	    DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
	    event.setStart(new EventDateTime().setDateTime(start));
	    DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
	    event.setEnd(new EventDateTime().setDateTime(end));
		
		try {
			event = this.calendarService.events().patch(calendarId, calendarId, event).execute();
		} catch (IOException ioEx) {
			throw new RuntimeException(ioEx);
		}
	}
}
