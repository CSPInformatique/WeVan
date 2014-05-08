package com.cspinformatique.wevan.google;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class GoogleCalendar {
	private static final String APPLICATION_NAME = "My Project";

	/** E-mail address of the service account. */
	private static final String SERVICE_ACCOUNT_EMAIL = "141051216875-o6d4m2qsl7n3od6kj1klc0cu6gsf2c7r@developer.gserviceaccount.com";
	
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	private static Calendar client;
	private static HttpTransport httpTransport;

	private static Credential authorize() throws Exception {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        // check for valid setup
        if (SERVICE_ACCOUNT_EMAIL.startsWith("Enter ")) {
          System.err.println(SERVICE_ACCOUNT_EMAIL);
          System.exit(1);
        }
        
        String p12Content = IOUtils.toString(
        		GoogleCalendar.class.getClassLoader().getResource(
            			"google/calendar/calendar.p12").openStream());
        
        if (p12Content.startsWith("Please")) {
        	throw new RuntimeException("Invalid private key file content : " + p12Content);
        }
        
		Set<String> scopes = CalendarScopes.all();
		
        // service account credential (uncomment setServiceAccountUser for domain-wide delegation)
        return new GoogleCredential.Builder().setTransport(httpTransport)
            .setJsonFactory(JSON_FACTORY)
            .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
            .setServiceAccountScopes(scopes)
            .setServiceAccountPrivateKeyFromP12File(
            	new File(GoogleCalendar.class.getClassLoader().getResource("google/calendar/calendar.p12").getFile())
            )
            // .setServiceAccountUser("user@example.com")
            .build();
	}

	public GoogleCalendar() {
		try {
			// authorization
			Credential credential = authorize();

			Calendar calendar = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME).build();
			
			com.google.api.services.calendar.model.Calendar carCalendar = new com.google.api.services.calendar.model.Calendar();
			carCalendar.setDescription("Test coliss !");
			carCalendar.setSummary("Un sommaire esti !");
			
			System.out.println(calendar.calendars().insert(carCalendar).execute());	
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
