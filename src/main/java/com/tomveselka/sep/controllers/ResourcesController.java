package com.tomveselka.sep.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("resources")
public class ResourcesController {
	
	@Value("classpath:static/documents/terms_of_service.pdf")
	Resource termsAndConditions;
	
	@Value("classpath:static/documents/data_usage_policy.pdf")
	Resource dataUsage;
	
	@Value("classpath:static/documents/data_usage_policy.pdf")
	Resource sector;

	@GetMapping(path="/terms_and_conditions")
	public void getTCpdf(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		File file = termsAndConditions.getFile();

	    InputStream inputStream = new FileInputStream(file);
	    int nRead;
	    while ((nRead = inputStream.read()) != -1) {
	        response.getWriter().write(nRead);
	    }
	}
	
	@GetMapping(path="/data_usage")
	public void getDataUsage(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		File file = dataUsage.getFile();

	    InputStream inputStream = new FileInputStream(file);
	    int nRead;
	    while ((nRead = inputStream.read()) != -1) {
	        response.getWriter().write(nRead);
	    }
	}
	
	@GetMapping(path="/sector.json")
	public ResponseEntity<Resource>  getSector() throws FileNotFoundException, IOException {
		InputStreamResource resource = new InputStreamResource(new FileInputStream(sector.getFile()));
		 HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(sector.getFile().length())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);
	}
	
}
