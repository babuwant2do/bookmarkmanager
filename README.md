
## To run the Project
goto project folder and Run following command 
- ./mvnw clean install
- ./mvnw spring-boot:run

Or Import the project in STS(Spring Tool Suite) and run as Spring Boot App.

# Access URL:

- User.
    - To Register: 
        - Method: POST
        - End point: /api/register
        - body in json format: `` {"login": "user", "password": "pass", "email": "user@abc.com"}`` 
    - To Sign In: 
        - Method: POST
        - End point: /api/auth/signin
        - body in json format: ``{"username" : "user", "password": "pass" }`` 

- Bookmark.
    - To process: 
        - Method: POST
        - End point: /api/bookmark-managers/process-bookmark?folder=true
        - Optional URL parameter: folder=true
        - body : multipart/form-data bookmark file.
        - Header: Authorization: Bearer <jwt Token received on Sign In>
        
 
    
# Technical choice:
- JavaSE 1.8
- Spring Boot 2.1.5
- JPA 2.0 
- Hibernate 5
- Spring Data
- Spring Secirity.
- H2 database

- This RESTFull web service is creates using Spring:
	- Easy to scale the service.
	- easy to integrate with different services. 
- Spring Data and Hibernate ORM used because:
	- It is Database independent.
	- Easy to manage Object relation model and synchronize changes in Model with the Database structure.
	- On the other hand Spring data hides lots of complexity of Hibernate like Transection, Query Writing.
   

# Short Description

- User must Register and Sign in first to access this service. 
- The Registered user is persist in the database. 
- On Sign in, user get a JWT, which need to send with each request to access the service.
- To Handle authentication and authorization Spring Security is used, overriding the default behavior to handle JWT based authentication.
- As the Bookmark need to process and return back to user, these data are not persisted, rather used file system.
- Decorator pattern is used to regenerate the Bookmark after processing.   



