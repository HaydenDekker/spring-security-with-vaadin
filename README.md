# Spring Security Module for Vaadin 14

Add security in three simple steps: POM declaration, database configuration and application annotations.

Here, the Vaadin Security tutorial is packaged into a module that can easily added to any existing spring application. The selected database is DynamoDB.

https://vaadin.com/learn/tutorials/securing-your-app-with-spring-security

## Use

* Simply add the dependency to the application POM.
* Add the spring annotations
* Add an authorized IAM user to connect to DynamoDB
* Manually create and declare the DynamoDB table in AWS
* Add the public routes or use the required annotation


## Deviation from Tutorial

Step 1 - setting-up-spring-security

* Didn’t use the two maven repos, just a signle spring spring-boot-starter-security
* Didn’t use the ErrorMVCAutoConfiguration.class, not sure what this did.
* Added a SavedRequestAwareAuthenticationSuccessHandler.... not sure why...
* SecurityConfiguration.class - Added a check for the datasource approved users and if not present an Admin is created.
* HasPublicRoutes interface created to initialise the applications public routes, all else remain private.

## Access Patterns

* Get user credentials by username
* Get all users
* Check if user exists

Key | SortKey | Attributes
----|---------|-----------
'User' | UserId | UserName | Roles | Password
