# Spring Security Module for Vaadin 14

Add security in three simple steps: POM declaration, database configuration and application annotations.

Here, the Vaadin Security tutorial is packaged into a module that can easily added to any existing spring application. The integrated database is DynamoDB.

https://vaadin.com/learn/tutorials/securing-your-app-with-spring-security

## Functional Overview

* Application looks to configured table to authenticate and authorise users.
* Creates a temp user if no users are found on the table. usr=admin, pw=admin (does not force deletion of temp user.)
* Ensures at least one user must always have the role ADMIN.
* Provides a UI view that allows ADMIN's to invite other users and assign them suitable ROLES. (Roles dependent on the developers view specification)
* Allows developers to bolt on security then define the specific roles as they develop each application view. Or use a HasViewAuthorisation interface to control access to views. The latter allowing decoupling security from the view.

For Simplicity, it does not,

* Allow users to sign up. (Can be extended to allow this).
* Allow multi-tenants/organisations to be used on the same application. (This could be extended by the developer)


## How to use

* Simply add the dependency to the application POM.
* Add the spring annotations, (@ComponentScan(), @EnableVaadin()).
* Add an authorized IAM user to connect to DynamoDB, preferably using an ENV variable.
* Manually create and declare the DynamoDB table in AWS. (Better control IAM user privilege)
* Add secure routes and the required roles as you develop views using SecurityUtils static add method.
* Change master admin password - First time application start, if no users exist in AWS table app will create a temporary ADMIN password.


## Deviation from Tutorial

Step 1 - setting-up-spring-security

* Didn’t use the two maven repos, just a signle spring spring-boot-starter-security
* Didn’t use the ErrorMVCAutoConfiguration.class, not sure what this did.
* Didn't use anyRequest permited, as vaadin takes care of auth.
* SecurityConfiguration.class - Added a check for the datasource approved users and if not present an Admin is created.

Step 5 - fine-grained-access-control

* Change public by default to secured by default. In function isAccessGranted() if no @Secure is detected then the route is not allowed. Meaning programmer must define a role anonymous for each public route.

## Database Access Patterns

See DDBApplicationData.class for full spec.

* Get user by username
* Get all users
* Save user by username

key | sKey | Attributes
----|---------|-----------
'U' | UserName | Name - Roles - Password - Created On
