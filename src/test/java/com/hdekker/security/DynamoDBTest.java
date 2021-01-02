package com.hdekker.security;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;

@SpringBootTest
public class DynamoDBTest {

	@Test
	public void testDBAccessCreateTable() {
		
		AmazonDynamoDBAsyncClientBuilder clientBuilder = AmazonDynamoDBAsyncClientBuilder.standard();
		clientBuilder.withRegion(Regions.AP_SOUTHEAST_2);
		AmazonDynamoDBAsync client = clientBuilder.build();
		
		
		
	}
	
}
