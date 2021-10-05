package com.tomveselka.sep.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.Prompt;
import com.nimbusds.openid.connect.sdk.claims.ACR;

@Service
public class BuildLoginURIService {
	
	Logger logger = LoggerFactory.getLogger(BuildLoginURIService.class);
	
	public AuthenticationRequest buildLoginUri(URI authorizationEndpoint) {
		// Application configuration from BankID dev. portal
	    String[] scopes = {"openid", "profile.name","profile.verification", "profile.name", "profile.email","profile.birthnumber","profile.addresses","profile.birthplaceNationality","profile.idcards","profile.phonenumber","profile.maritalstatus","profile.gender","profile.paymentAccounts"};
	    ClientID clientId = new ClientID("9e701408-88a2-4e2a-b052-2e1387583e40");
	    String redirectURI = "http://localhost:8080/sep/main/redirectURI";

	    // Creating the required scopes
	    Scope scope = new Scope(scopes);

	    try {
	        
	        // Construct the AuthenticationRequest Builder with scope, client_id and redirect_uri parameters
	        AuthenticationRequest.Builder authBuilder = new AuthenticationRequest.Builder(
	            // We are generating a URL for code_grant
	                new ResponseType("code"),
	                scope,
	                clientId,
	                new URI(redirectURI));
	        
	        // Set the auth endpoint URI
	        authBuilder.endpointURI(authorizationEndpoint);
	        
	        // Set random (default constructor) state
	        authBuilder.state(new State());
	        
	        // Set random (default constructor) nonce
	        authBuilder.nonce(new Nonce());
	        
	        // Set prompt=consent
	        authBuilder.prompt(new Prompt("consent"));
	        
	        //Set LoA so that 2FA is required
	        List<ACR> acrValueList = new ArrayList<ACR>();
	        acrValueList.add(new ACR("loa3"));
	        authBuilder.acrValues(acrValueList);

	        // ... and build the auth login URI
	        AuthenticationRequest request = authBuilder.build();
	        logger.info("Authentication request built successfully, result: "+request.toURI());
	        return request;
	        
	    } catch (URISyntaxException e) {
	    	logger.info("Building authentication request failed with Exception "+e.toString());
	    }
		return null;
}
}
