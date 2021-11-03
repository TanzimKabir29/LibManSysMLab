# LibManSysMLab
Demo of a library management system for MonstarLab

This file entails how to run and interact with the demo library management system backend.

Specifications:
JDK version: 11
Build automation: Maven
Default Tomcat server port: localhost:9091

How to run this program:

1) (Requires JDK11 installed and added to path) You can download the libmansys-0.0.1.jar file to your computer and deploy the jar file with the command:
        java -jar libmansys-0.0.1.jar

2) (Requires maven added to path) You can download the source code and run the command in the project directory:
        mvn spring-boot:run
        
The system consists of number APIs that add CRUD functionality to User and Book entities of a library. The system also supports issuing book to users, and users submitting back their books. Lastly, using it is also possible to list out users who have been issued a book, and books which have been issued to a user.

Exposed APIs :

1) User create.
   Method = POST
   Url: /user/create
   Requestbody json fields: user_name(String), first_name(String), last_name(String)

2) User data read.
   Method = GET
