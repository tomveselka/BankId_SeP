package com.tomveselka.sep.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

@SpringBootTest
public class GetConfigurationServiceTest {

	@Autowired
	GetConfigurationService getConfigurationService;
	
	@Test
	public void getConfigurationServiceTest() {
		OIDCProviderMetadata metadata=getConfigurationService.getAuthorizationEndpoint();
		URI auth = metadata.getAuthorizationEndpointURI();
		assertThat(auth).isNotNull();
	}
}
