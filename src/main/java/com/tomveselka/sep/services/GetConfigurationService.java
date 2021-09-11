package com.tomveselka.sep.services;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderConfigurationRequest;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;


@Service
public class GetConfigurationService {
	
	Logger logger = LoggerFactory.getLogger(GetConfigurationService.class);
	
	public OIDCProviderMetadata getAuthorizationEndpoint() {
		// The BankID Sandbox issuer uri
		String bankIDIssuerURI = "https://oidc.sandbox.bankid.cz/";

		// Create new issuer
		Issuer issuer = new Issuer(bankIDIssuerURI);

		// Request for configuration
		OIDCProviderConfigurationRequest request = new OIDCProviderConfigurationRequest(issuer);

		try {
			HTTPRequest httpRequest = request.toHTTPRequest();

			// Call for configuration
			HTTPResponse httpResponse = httpRequest.send();

			// Parsing BankID Configuration
			OIDCProviderMetadata opMetadata = OIDCProviderMetadata.parse(httpResponse.getContentAsJSONObject());
			logger.info("OIDCProviderMetadata successfully obtained. OIDCProviderMetadata="+httpResponse.getContentAsJSONObject().toString());
			
			// Obtain the auth endpoint URI
			URI auth = opMetadata.getAuthorizationEndpointURI();

			logger.info("Configuration successfully obtained. Authorization endpoint URI="+auth.toString());
			
			return opMetadata;
		} catch (IOException e) {
			logger.info("Obtaining OIDC Configuration thrown exception "+e.toString());
			return null;
		} catch (ParseException e) {
			logger.info("Obtaining OIDC Configuration thrown exception "+e.toString());
			return null;
		}
		

	}
}
