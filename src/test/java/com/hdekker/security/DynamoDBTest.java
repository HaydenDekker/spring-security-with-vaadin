package com.hdekker.security;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdekker.security.services.data.DyanmoDBDAO;
import com.hdekker.security.services.data.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DynamoDBTest {
	
	Logger log = LoggerFactory.getLogger(DynamoDBTest.class);
 
	@Autowired
	DyanmoDBDAO dynamoDBDAO;
	
	ObjectMapper om = new ObjectMapper();
	
	/**
	 * Data is now driven from the AWS workbench.
	 * 
	 * @return
	 */
	public User getMockUser() {
		
		User testUser = new User();
		testUser.setEmail("test@test.com.au");
		testUser.setFirstName("hayden");
		testUser.setSecondName("dekk");
		testUser.setUserName("hayden.dekker");
		testUser.setCreatedOn(LocalDateTime.of(2021, 01, 11, 18, 30));
		testUser.setLastModified(LocalDateTime.now());
		testUser.setIsActive(true);
		testUser.setRoles(Arrays.asList("sick","cando","sometime"));
		testUser.setPassWord("just$a2mock$password");
		
		return testUser;
	}
//	
//	public User getMockUser2() {
//		
//		User testUser = new User();
//		testUser.setEmail("gold@test.com.au");
//		testUser.setFirstName("hummer");
//		testUser.setSecondName("duckling");
//		testUser.setUserName("h.duck");
//		testUser.setCreatedOn(LocalDateTime.of(2021, 01, 11, 18, 30));
//		testUser.setLastModified(LocalDateTime.now());
//		testUser.setIsActive(true);
//		testUser.setRoles(Arrays.asList("sick","cando","sometime"));
//		testUser.setPassWord("just$a2mo..");
//		
//		return testUser;
//	}
//	
	// TODO how best to integrate test into the live system?
	// TODO should the DB always contain test-users? I suppose not.
	// The test env should use a completely different data table.
	// The application should confirm that it can create test users, regardless of the initial content
	
	@Before
	public void createTestUser() {
		
		try {
			log.info("Saving test user to the DB, user object is, " + om.writeValueAsString(getMockUser()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CompletableFuture<Optional<User>> cOptUser = dynamoDBDAO.save(getMockUser());
		
		cOptUser.join().ifPresentOrElse(user -> log.info(user.getUserName() + " added to database"), 
						()-> log.info("failed to add user to DB"));
		
		
	}
	
	@Test
	public void findAllUsers() {
		
		CompletableFuture<Optional<List<String>>> allUsers = dynamoDBDAO.findAllUsers();
		
		assertTrue(allUsers.join().filter(m->m.contains("hayden.dekker")).isPresent());
		
	}
	
	@Test
	public void getUserByUserName() {
		
		CompletableFuture<Optional<User>> user = dynamoDBDAO.findFirstByUserName("hayden.dekker");
		assertTrue(user.join().isPresent());
		
		log.info("User found, " + user.join().get().getUserName());
		
	}
	
	// Test not needed, just check against user item.
//	@Test
//	public void userHasAuthority() {
//		
//		String userRole = "TEST";
//		dynamoDBDAO.isUserAuthorised(userRole);
//		
//		
//	}
	
//	@Test
//	public void setUserInactive() {
//		
//		
//		
//		
//	}
	
	@After
	public void removeMockUser() {
		
		CompletableFuture<Boolean> del = dynamoDBDAO.delete("hayden.dekker");
		Boolean isDeleted = del.join();
		assertTrue(isDeleted);
		
	}
	
	
}
