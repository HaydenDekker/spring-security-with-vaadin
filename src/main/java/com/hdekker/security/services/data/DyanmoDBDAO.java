package com.hdekker.security.services.data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

import static com.hdekker.security.services.data.DDBApplicationData.*;
import static com.hdekker.security.services.data.DDBApplicationData.UserExpressionAttributeConstants.*;


@Service
public class DyanmoDBDAO {

	DynamoDbAsyncClient client = DynamoDbAsyncClient.builder().region(Region.AP_SOUTHEAST_2).build();
	
	@Autowired
	DynamoDBConfiguration config;
	
	/**
	 * Only updateItem is used for this module. 
	 * 
	 * @param user
	 * @return
	 */
	public CompletableFuture<Optional<User>> save(User user) {
		
		Map<String, AttributeValue> expAttrsValMap = Map.of(
				
				FirstName.getExpressionAttributeValueKey(), attB.apply(user.getFirstName()),
				Surname.getExpressionAttributeValueKey(), attB.apply(user.getSecondName()),
				Email.getExpressionAttributeValueKey(), attB.apply(user.getEmail()),
				
				UserName.getExpressionAttributeValueKey(), attB.apply(user.getUserName()),
				PassWord.getExpressionAttributeValueKey(), attB.apply(user.getPassWord()),
				Roles.getExpressionAttributeValueKey(), AttributeValue.builder().ss(user.getRoles()).build(),
				
			    LastModified.getExpressionAttributeValueKey(), attB.apply(user.getLastModified().format(dateTimeFormatter())),
			    CreatedOn.getExpressionAttributeValueKey(), attB.apply(user.getCreatedOn().format(dateTimeFormatter())),
			    IsAccountActive.getExpressionAttributeValueKey(), AttributeValue.builder().bool(user.getIsActive()).build()
				
		);
		
		Map<String, AttributeValue> key = Map.of(
				config.getPrimaryKeyName(), attB.apply(DDBApplicationData.PK_USER), 
				config.getSortKeyName(), attB.apply(user.getUserName())
		);
		
		UpdateItemRequest pir = 
				
			UpdateItemRequest.builder()
				.tableName(config.getTableName())
				.key(key)
				.expressionAttributeValues(expAttrsValMap)
				.updateExpression("SET "
						+ FirstName.getUpdateExpressionSnippet() + ", "
						+ Surname.getUpdateExpressionSnippet() + ", "
						+ Email.getUpdateExpressionSnippet() + ", "
						+ UserName.getUpdateExpressionSnippet() + ", "
						+ PassWord.getUpdateExpressionSnippet() + ", "
						+ Roles.getUpdateExpressionSnippet() + ", "
						+ LastModified.getUpdateExpressionSnippet() + ", "
						+ CreatedOn.getUpdateExpressionSnippet() + ", "
						+ IsAccountActive.getUpdateExpressionSnippet())
				.build();
		
		// Sent out command and return a suitable future.
		CompletableFuture<UpdateItemResponse> putRes = client.updateItem(pir);
		
		CompletableFuture<Optional<User>> coptUser = CompletableFuture.supplyAsync(()->{
			
			UpdateItemResponse piresp = putRes.join();
			Predicate<UpdateItemResponse> pre = (p) -> Integer.valueOf(p.sdkHttpResponse().statusCode()).equals(200);
			return Optional.of(piresp).filter(pre).map(p -> user);
			
		});
		
		return coptUser;
		
	}
	
	Function<Map<String, AttributeValue>, User> itemToUser(){
		
		return(map)->{
			
			User user = new User();
			
			user.setUserName(map.get(UserName.getAttributeName()).s());
			user.setFirstName(map.get(FirstName.getAttributeName()).s());
			user.setSecondName(map.get(Surname.getAttributeName()).s());
			
			user.setEmail(map.get(Email.getAttributeName()).s());
			user.setIsActive(map.get(IsAccountActive.getAttributeName()).bool());
			user.setRoles(map.get(Roles.getAttributeName()).ss());
			
			user.setPassWord(map.get(PassWord.getAttributeName()).s());
			user.setCreatedOn(LocalDateTime.parse(map.get(CreatedOn.getAttributeName()).s(), dateTimeFormatter()));
			user.setLastModified(LocalDateTime.parse(map.get(LastModified.getAttributeName()).s(), dateTimeFormatter()));
			
			return user;
			
		};
		
		
	}
	
	/**
	 * 
	 * Gets a single user and the entire set of attribute by their username.
	 * 
	 * @param userName
	 * @return
	 */
	public CompletableFuture<Optional<User>> findFirstByUserName(String userName) {
		
		GetItemRequest gir = GetItemRequest.builder()
						.tableName(config.getTableName())
						.projectionExpression(""
								+ FirstName.getAttributeName() + ", "
								+ Surname.getAttributeName() + ", "
								+ Email.getAttributeName() + ", "
								+ UserName.getAttributeName() + ", "
								+ PassWord.getAttributeName() + ", "
								+ Roles.getAttributeName() + ", "
								+ LastModified.getAttributeName() + ", "
								+ CreatedOn.getAttributeName() + ", "
								+ IsAccountActive.getAttributeName()
								
								)
						.key(Map.of("PK", DDBApplicationData.attB.apply(DDBApplicationData.PK_USER),
							"SK", DDBApplicationData.attB.apply(userName))
						).build();
		CompletableFuture<GetItemResponse> item = client.getItem(gir);
		
		CompletableFuture<Optional<User>> optUser = CompletableFuture.supplyAsync(()->{
			
			GetItemResponse resp = item.join();
			return Optional.ofNullable(itemToUser().apply(resp.item()));
			
		});
		
		return optUser;
	}

	/**
	 * Returns a list of all users on the database.
	 * Use get user details to find exact property values for a given user.
	 * 
	 * @return
	 */
	public CompletableFuture<Optional<List<String>>> findAllUsers() {
		
		Map<String, AttributeValue> keys = new HashMap<>();
				keys.put(":user", AttributeValue.builder().s(DDBApplicationData.PK_USER).build());
		
//		Map<String, KeysAndAttributes> keyAndA = new HashMap<>();
//		keyAndA.put(config.getTableName(), KeysAndAttributes.builder().keys(Arrays.asList(keys)).build());
//		
//		BatchGetItemRequest bgir = BatchGetItemRequest.builder().requestItems(keyAndA).build();
//		CompletableFuture<BatchGetItemResponse> bgiresp = client.batchGetItem(bgir);
//		
		QueryRequest queryRequest = QueryRequest.builder().tableName(config.getTableName())
											.expressionAttributeValues(keys)
											.keyConditionExpression("PK = :user").build();
		CompletableFuture<QueryResponse> q = client.query(queryRequest);
		
		CompletableFuture<Optional<List<String>>> fut = CompletableFuture.supplyAsync(()->{
			
			QueryResponse resp = q.join();
			Optional<List<String>> s = Optional.of(resp.items())
							.map(m->{
								// Take each result and just return the username.
								return m.stream().map(i->i.get("SK").s()).collect(Collectors.toList());
							});
												
			return s;
			
		});
		
		return fut;
		
	}
	
	/**
	 * Keeps the user record but sets user to inactive.
	 * 
	 * @param userName
	 * @return
	 */
	public CompletableFuture<User> setUserInactive(String userName) {
		
		CompletableFuture<Optional<User>> user = findFirstByUserName(userName);
		return user.thenCompose(opt-> {
			
			return CompletableFuture.supplyAsync(()->{
				return opt.map(usr->{
			
					usr.setIsActive(false);
					return save(usr).join().get();
					
				}).orElseThrow();
			});
			
		});
		
	}

	/**
	 * Deletes a item permanently from the database
	 * 
	 * @param userName
	 * @return
	 */
	public CompletableFuture<Boolean> delete(String userName) {
		
		DeleteItemRequest dir = DeleteItemRequest.builder()
				.tableName(config.getTableName())
				.key(
						Map.of("PK", attB.apply(PK_USER),
							"SK", attB.apply(userName))
		).build();
		return client.deleteItem(dir).thenComposeAsync(resp->
			// return success or failure.
			CompletableFuture.supplyAsync(()->Integer.valueOf(resp.sdkHttpResponse().statusCode()).equals(200))
		);
	
	}

	/**
	 * No longer needed, just check the list against the user object.
	 * 
	 * @param userRole
	 */
//	public void isUserAuthorised(String userRole) {
//		
////		Map<String, AttributeValue> map = Map.of("PK", attB.apply(PK_USER_ROLE),
////				"SK", attB.apply(userRole));
////		
//	}

}
