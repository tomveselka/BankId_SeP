package com.tomveselka.sep.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tomveselka.sep.imported.model.Profile;

@Service
public class ParseJsonService {
	Logger logger = LoggerFactory.getLogger(ParseJsonService.class);
	
	public Profile parseProfile(String json) {
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());
		Profile profile = new Profile();
		try {
			profile = objectMapper.readValue(json, Profile.class);
			logger.info("Parsed Profile JSON successfully with output:"+profile.toString());
			return profile;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.info("Parsing Profile JSON failed with Exception: "+e.toString());
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			logger.info("Parsing Profile JSON failed with Exception: "+e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
