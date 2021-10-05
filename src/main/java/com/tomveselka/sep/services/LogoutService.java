package com.tomveselka.sep.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.openid.connect.sdk.LogoutRequest;

@Service
public class LogoutService {
	
	Logger logger = LoggerFactory.getLogger(LogoutService.class);

	public void logoutClient(JWT id_token) {
		String logoutURIString = "https://oidc.sandbox.bankid.cz/logout";
		URI logoutURI;
		try {
			logoutURI = new URI(logoutURIString);
		
		//String redirectAfterLogoutURI="http://localhost:8080/sep/main/redirectURI"
		
		//LogoutRequest logoutRequest = new LogoutRequest(logoutURI, null, null, null)
		LogoutRequest logoutRequest = new LogoutRequest(logoutURI,id_token);
		logger.info("Tried logging out with request:"+logoutRequest.toURI().toString());
		logoutRequest.toHTTPRequest().send();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
