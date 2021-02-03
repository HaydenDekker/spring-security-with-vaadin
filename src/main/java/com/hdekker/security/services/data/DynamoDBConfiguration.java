package com.hdekker.security.services.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dynamo.users.table")
public class DynamoDBConfiguration {

	public String tableName;
	public String primaryKeyName;
	public String sortKeyName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public String getSortKeyName() {
		return sortKeyName;
	}

	public void setSortKeyName(String sortKeyName) {
		this.sortKeyName = sortKeyName;
	}
	
	
	
}
