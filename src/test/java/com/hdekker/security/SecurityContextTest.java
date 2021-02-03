package com.hdekker.security;

import static org.junit.Assert.assertEquals;

import javax.enterprise.inject.spi.WithAnnotations;
import javax.servlet.Servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.flow.spring.SpringBootAutoConfiguration;
import com.vaadin.flow.spring.SpringInstantiator;
import com.vaadin.flow.spring.SpringServlet;
import com.vaadin.flow.spring.VaadinMVCWebAppInitializer;
import com.vaadin.flow.spring.VaadinServletContextInitializer;
import com.vaadin.flow.spring.annotation.EnableVaadin;

/**
 * Tests
 * 
 *  - Check that the application can ...
 *  TODO should the test at deployment use default or custom authentication?
 *  Depends on the database. See readme.
 * 
 * @author HDekker
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
//@WebAppConfiguration
//@ImportAutoConfiguration(classes = {SpringBootAutoConfiguration.class})
@AutoConfigureMockMvc
public class SecurityContextTest {

	@Autowired
	MockMvc mockMvc;
	
//	
//	@Configuration
//    @EnableVaadin
//    public static class Config {
//		
//		@Bean
//		SpringBootAutoConfiguration ac() {
//			return new SpringBootAutoConfiguration();
//		}
//		
//    }
	
//	@Configuration
//	@AutoConfigureBefore(ServletWebServerFactoryAutoConfiguration.class)
//	public class ForceTomcatAutoConfiguration {
//
//	    @Bean
//	    TomcatServletWebServerFactory tomcat() {
//	         return new TomcatServletWebServerFactory();
//	    }
//	}
//	
	
	
	@Autowired
    private WebApplicationContext context;
	
	TestRestTemplate rt = new TestRestTemplate();
	
	@Before
    public void setup() {
        mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
        
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void canLoginDefaultUser() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("users/list"))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	
}
