# Security Module for Spring Vaadin 14 Projects

Add application security in a few simple steps.

Here, the Vaadin Security tutorial is packaged into a module that can easily be added to any existing spring application. The integrated database is DynamoDB.

https://vaadin.com/learn/tutorials/securing-your-app-with-spring-security

Please check the code before assuming it's secure.

## Functional Overview

* Application looks to configured table to authenticate and authorise users.
* Creates a temp user if no users are found on the table. usr=admin, pw=admin (does not force deletion of temp user.)
* Ensures at least one user must always have the role ADMIN.
* Provides a UI view that allows ADMIN's to invite other users and assign them suitable ROLES. (Roles dependent on the developers view specification)
* Allows developers to bolt on security then define the specific roles as they develop each application view.
* Admin's control user passwords and other account detail.

For Simplicity, it does not,

* Allow users to sign up. (Can be extended to allow this).
* Allow multi-tenants/organisations to be used on the same application. (This could be extended by the developer)


## How to use

* Clone Git, open in eclipse and install via Maven or import to an existing project directly.
* Simply add the dependency to the application POM.
* Add the spring annotations, (@ComponentScan(), @EnableVaadin()).
* Add an authorized IAM user to connect to DynamoDB, preferably using an ENV variable.
* Manually create and declare the DynamoDB table in AWS. (Better control IAM user privilege)
* Add secure routes and the required roles as you develop views using SecurityUtils static add method.
* Change master admin password - First time application start, if no users exist in AWS table app will create a temporary ADMIN password.
* Add the preconfigured LogoutButton to any view of your choice to easily allow users to logout.
* Add the configuration specified in the ..... application.properties example.. or compiled spring properties file.


## Deviation from Tutorial

Step 1 - setting-up-spring-security

* Didn’t use the two maven repos, just a signle spring spring-boot-starter-security
* Didn’t use the ErrorMVCAutoConfiguration.class, not sure what this did.
* Didn't use anyRequest authenticated, as vaadin takes care of auth.
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
