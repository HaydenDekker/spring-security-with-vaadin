package com.hdekker.security.services.data;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * This interface defines the constans as per the DB design
 * 
 * @author HDekker
 *
 */
public interface DDBApplicationData {

	public static Function<String, AttributeValue> attB = (s)-> AttributeValue.builder().s(s).build();
	
	String PK_USER = "U";
	
	// Item - User Role
	String PK_USER_ROLE = "UR";
	
	/**
	 * The userRole is flexible and to be determined by the software developer, hence no enum.
	 * Takes a User and the role to be queried.
	 * 
	 * @return
	 */
	public static Function<Pair<User, String>, Map<String, AttributeValue>> userRoleQueryIndexBuilder(){
		return (userRolePair) -> {
			return Map.of("PK", attB.apply(PK_USER_ROLE),
						"SK", attB.apply(userRolePair.getRight()+"#"+userRolePair.getLeft().getUserName()));
		};
	}
	
	/**
	 * The date time formatter used to capture and read application data
	 * 
	 * @return
	 */
	public static DateTimeFormatter dateTimeFormatter() {
		return DateTimeFormatter.ISO_DATE_TIME;
	}
	
	
	
	/**
	 * The list of expression attribute value keys,
	 * and the attribute values
	 * that can be used to address db attributes 
	 * for the User item.
	 * 
	 * @author HDekker
	 *
	 */
	public enum UserExpressionAttributeConstants {
		
		FirstName("firstName"),
		Surname("surname"),
		Email("email"),
		UserName("usr"),
		
		PassWord("upw"),
		Roles("uroles"),
		
		CreatedOn("createdOn"),
		LastModified("lastModified"),
		IsAccountActive("isAccActive");
		
		final String attributeName;
		
		UserExpressionAttributeConstants(String attributeName){
			this.attributeName = attributeName;
		}
		
		public String getUpdateExpressionSnippet() {
			return getAttributeName() + "=" + getExpressionAttributeValueKey();
			
		}

		public String getAttributeName() {
			return attributeName;
		}

		public String getExpressionAttributeValueKey() {
			return ":" + attributeName;
		}

	}
	
}
