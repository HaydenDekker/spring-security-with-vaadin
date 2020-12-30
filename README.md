# Custom project from start.vaadin.com

This project was created from https://start.vaadin.com.

This module packages the Vaadin Security tutorial into a module that can easily added to any existing spring application.

See tutorial here https://vaadin.com/learn/tutorials/securing-your-app-with-spring-security

# Starting
This release provides an integration with mysql db.
1. Add this module as a dependency in the POM, 
2. Add the @ComponentScan(basePackages = {"com.hdekker.security",.. annotations to the application class
3. Add the @EnableVaadin(value = {"com.hdekker.security",.. annotations to the application class
4. Add the specific database configuration to the application.proprerties file.
