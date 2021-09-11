package com.tomveselka.sep.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;

@Service
public class ProfileInfoService {

	Logger logger = LoggerFactory.getLogger(ProfileInfoService.class);
	
	public String getProfileInfo(AccessToken accessToken) {
		// Set the right data API url (for example UserInfo)
		String userInfoURL = "https://oidc.sandbox.bankid.cz/profile";

		try {
			URI userInfoEndpoint = new URI(userInfoURL);

			// You must have a valid access_token
			BearerAccessToken token = BearerAccessToken.parse("Bearer "+accessToken);
			logger.info("BearerAccessToken token="+token.toString());
			
			// And call the API
			HTTPResponse dataResponse = new UserInfoRequest(userInfoEndpoint, token).toHTTPRequest().send();

			// Data in JSON format
			String data = dataResponse.getContentAsJSONObject().toJSONString();
			logger.info("Successfully obtained client data: "+data);
			return data;
		} catch (URISyntaxException e) {
			logger.info("Getting ProfileInfo failed with Exception " + e.toString());
		} catch (ParseException e) {
			logger.info("Getting ProfileInfo failed with Exception " + e.toString());
		} catch (IOException e) {
			logger.info("Getting ProfileInfo failed with Exception " + e.toString());
		}
		return null;
	}
}
