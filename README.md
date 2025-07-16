# Login Authentication API with Spring boot 

- Project created to be the backend of a login and registration system made in angular
- The project consist in a validation of user by a token generated with JWT (JSON web token) 
- Project will be integrated with angular soon

## Utilized technologies

### Backend
- **Java 17**
- **Spring Boot 3.5.3**
- **Spring data JPA**
- **Spring Web**
- **Spring Security**
- **JWT**
- **H2**
- **Maven**

### Maven Dependencies
Defined in `pom.xml`
- `spring-boot-starter-data-jpa`: for database integration via JPA/Hibernate
- `spring-boot-starter-web`: for building RESTful APIs
- `spring-boot-starter-security`: for implementing authentication and authorization
- `java-jwt`: for generating and validating JWT tokens (from Auth0)
- `h2`: in-memory database used for development/testing
- `spring-boot-devtools`: for automatic reload during development
- `lombok`: reduces boilerplate code using annotations like `@Getter`, `@Setter`, etc.
- `spring-boot-starter-test`: for unit and integration testing
- `spring-security-test`: for testing security configurations
