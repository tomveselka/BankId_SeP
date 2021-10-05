package com.tomveselka.sep.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;

@Service
public class GetTokensFromResponse {
	Logger logger = LoggerFactory.getLogger(GetTokensFromResponse.class);
	
	public AccessToken getAccessToken(AccessTokenResponse successResponse) {
		AccessToken accessToken = successResponse.getTokens().getAccessToken();
		logger.info("Access token obtained succesfully value="+accessToken.getValue());
		return accessToken;
	}
	
	public JWT getJWTIdToken(AccessTokenResponse successResponse) {
		JWT idToken = successResponse.getTokens().toOIDCTokens().getIDToken();
		logger.info("ID token obtained succesfully value=" + idToken.getParsedString());
		return idToken;
	}
}
