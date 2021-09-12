package com.tomveselka.sep.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tomveselka.sep.imported.model.Profile;

@SpringBootTest
public class ParseJsonServiceTest {

	@Autowired
	ParseJsonService parseJsonService;

	@Test
	public void testParsingWorks() {
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

		 
		assertThat(profile.getBirthcountry()).isNotNull();
	}
}
