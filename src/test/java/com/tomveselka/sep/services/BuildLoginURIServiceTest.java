package com.tomveselka.sep.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuildLoginURIServiceTest {

	@Autowired 
	BuildLoginURIService buildURILoginService;
	
	@Test
	public void getLoginURI() {
		buildURILoginService.buildLoginUri(URI.create("https://oidc.sandbox.bankid.cz/auth"));

	}
}
