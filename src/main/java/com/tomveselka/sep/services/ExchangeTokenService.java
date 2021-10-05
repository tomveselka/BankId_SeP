package com.tomveselka.sep.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretPost;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;

@Service
public class ExchangeTokenService {

	Logger logger = LoggerFactory.getLogger(ExchangeTokenService.class);

	public AccessTokenResponse getIdToken(String code) {
		// Application configuration from BankID dev. portal
		ClientID clientId = new ClientID("9e701408-88a2-4e2a-b052-2e1387583e40");

		// Application redirect URI ()
		String redirectURI = "http://localhost:8080/sep/main/redirectURI";

		// Client secret value
		String clintSecretStr = "AO4_j0WBfq0fUCXlr9c4DtJzXo7rwgv_1tlIWGnnlROJmsOg4LFlbJBrfyt5Ho7D2zHx1CEQnfv4CoYkdHwEC9A";

		// BankID token endpoint (from discovery endpoint)
		String tokenEndpoint = "https://oidc.sandbox.bankid.cz/token";

		// Code from callback on redirect URI
		// String code = "... code ...";

		try {
			// Set the code object
			AuthorizationCode authorizationCode = new AuthorizationCode(code);

			// Set the redirectURI and create code grant object
			URI callbackURI = new URI(redirectURI);
			AuthorizationGrant codeGrant = new AuthorizationCodeGrant(authorizationCode, callbackURI);

			// Set the client_secret value and create client authentication
			Secret clientSecret = new Secret(clintSecretStr);
			ClientAuthentication clientAuth = new ClientSecretPost(clientId, clientSecret);

			// Create token endpoint URI and make the token request
			URI tokenEndpointURI = new URI(tokenEndpoint);
			TokenRequest request = new TokenRequest(tokenEndpointURI, clientAuth, codeGrant);

			// Get the token response
			TokenResponse tokenResponse = OIDCTokenResponseParser.parse(request.toHTTPRequest().send());

			if (tokenResponse.indicatesSuccess()) {

				// Get success response
				AccessTokenResponse successResponse = tokenResponse.toSuccessResponse();
				
				// Obtaining an access_token
				//AccessToken accessToken = successResponse.getTokens().getAccessToken();
				//logger.info("Access token obtained succesfully value="+accessToken.getValue());
				// and also obtaining an refresh_token (if can)
				// RefreshToken refreshToken = successResponse.getTokens().getRefreshToken();

				// and and finally obtaining an id_token
				JWT idToken = successResponse.getTokens().toOIDCTokens().getIDToken();
				logger.info("ID token obtained succesfully value=" + idToken.getParsedString());
				return successResponse;
			} else {
				TokenErrorResponse errorResponse = tokenResponse.toErrorResponse();
				logger.info("Token response not succesfull. Error code=" + errorResponse.getErrorObject().getCode()
						+ " Error HTTP code" + errorResponse.getErrorObject().getHTTPStatusCode()
						+ " Error description=" + errorResponse.getErrorObject().getDescription());
			}
		} catch (URISyntaxException e) {
			logger.info("Token exchange failed with Exception " + e.toString());
		} catch (ParseException e) {
			logger.info("Token exchange failed with Exception " + e.toString());
		} catch (IOException e) {
			logger.info("Token exchange failed with Exception " + e.toString());
		}
		return null;
	}
}
