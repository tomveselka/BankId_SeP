package com.tomveselka.sep.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.tomveselka.sep.imported.model.Profile;
import com.tomveselka.sep.services.BuildLoginURIService;
import com.tomveselka.sep.services.ExchangeTokenService;
import com.tomveselka.sep.services.GetConfigurationService;
import com.tomveselka.sep.services.ParseJsonService;
import com.tomveselka.sep.services.ProfileInfoService;





@Controller
//@SessionAttributes("responseData")
public class MainController {
	
	@Autowired
	GetConfigurationService configurationService;
	
	@Autowired
	BuildLoginURIService loginURIService;
	
	@Autowired
	ExchangeTokenService exchnageTokenService;
	
	@Autowired
	ProfileInfoService profileInfoService;
	
	@Autowired
	ParseJsonService parseJsonService;
	
	Logger logger = LoggerFactory.getLogger(MainController.class);
	
	
	
	@GetMapping(path="/main")
	public String mainScreen() {
		return "login";
	}
		
	@GetMapping(path="main/login")
	public String getEndpoint() {
		OIDCProviderMetadata metadata=configurationService.getAuthorizationEndpoint();
		AuthenticationRequest request = loginURIService.buildLoginUri(metadata.getAuthorizationEndpointURI());
		return "redirect:"+request.toURI();

	}   
	
    @GetMapping(path = "/main/redirectURI")
    public String redirectURI(@RequestParam String code, RedirectAttributes redirectAttributes) {
    	logger.info("Returned code="+code);
    	AccessToken accessToken = exchnageTokenService.getIdToken(code);
    	String responseClientData = profileInfoService.getProfileInfo(accessToken);
    	redirectAttributes.addFlashAttribute("responseClientData", responseClientData);
    	return "redirect:/main/display";
    }
    
    @GetMapping(path = "/main/display")
    public String displayOutput(Model model, HttpServletRequest request) {
    	Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            String responseClientData = (String) inputFlashMap.get("responseClientData");
            System.out.println("Passed data:"+responseClientData);
            Profile profile = parseJsonService.parseProfile(responseClientData);
            model.addAttribute("clientData", profile);
            return "output";
        }else {
        	return "output";
        }
    }
    
    @GetMapping(path="main/test")
    public String testOutput(Model model){
    	FileInputStream fis;
		Profile profile = new Profile();
		try {
			fis = new FileInputStream("src/main/resources/profileInput.txt");
			String data = IOUtils.toString(fis, "UTF-8");
			//System.out.println("Input from file is:"+data);
			profile= parseJsonService.parseProfile(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("clientData", profile);
    	return "output";
    }
}
