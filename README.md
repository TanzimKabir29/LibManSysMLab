# LibManSysMLab
Demo of a library management system for MonstarLab

This file entails how to run and interact with the demo library management system backend.

### Specifications:
JDK version: 11
Build automation: Maven
Default Tomcat server port: localhost:9091

### How to run this program:

1) **(Requires JDK11 installed and added to path)** You can download the libmansys-0.0.1.jar file to your computer and deploy the jar file with the command:
        `java -jar libmansys-0.0.1.jar`
   Optionally, to run the application on a port of your choice, create a file named "application.properties" in the jar directory. Then write the line "server.port=$X" to the file (without the quotations), replacing $X with the port number of your choice.

2) **(Requires maven added to path)** You can download the source code and run the command in the project directory:
        `mvn spring-boot:run`
   Optionally, to run the application on a port of your choice, edit the file "/src/main/resources/application.properties" in the source code directory. Then find the key "server.port" (without the quotations), and set the value to the port number of your choice.


### Functionality through APIs

The system consists of number APIs that add CRUD functionality to User and Book entities of a library. The system also supports issuing book to users, and users submitting back their books. Lastly, using it is also possible to list out users who have been issued a book, and books which have been issued to a user.

### Business logic suppositions

Users are uniquely identified by their Id or their Username. Since multiple books can have the same name, the Book entities are uniquely identified by their Name,Author pair.
Users can have a maximum number of books preset by the application. This value can be found in the src/main/resources/application.properties file, under the key "user.book.limit". This value represents the maximum number of total books any user can have. Issuing a book is cross-checked against this value.

### Exposed APIs :

1) User CRUD.
   1) Create user 
      ```
      Method = POST
      Url: /user/create
      Requestbody json fields: user_name(String), first_name(String), last_name(String)
      ```
   2) Get user data
      ```
      Method = GET
      Url: /user/getbyid?id={1}
      Param: {1} -> id
      OR
      Method = GET
      Url: /user/getbyusername?username={1}
      Param: {1} -> username
      ```
   3) Update user data
      ```
      Method = PUT
      Url: /user/edit
      Requestbody json fields: id(Long), user_name(String), first_name(String), last_name(String)
      ```
   4) Delete user data
      ```
      Method = DELETE
      Url: /user/deletebyid?id={1}
      Param: {1} -> id
      OR
      Method = DELETE
      Url: /user/deletebyusername?username={1}
      Param: {1} -> username
      ```
2) Book CRUD.
   1) Create book 
      ```
      Method = POST
      Url: /book/create
      Requestbody json fields: name(String), author(String), amount(int)
      ```
   2) Get book data
      ```
      Method = GET
      Url: /book/getbyid?id={1}
      Param: {1} -> id
      OR
      Method = GET
      Url: /book/getbyname?name={1}
      Param: {1} -> name
      OR
      Method = GET
      Url: /book/getbyauthor?author={1}
      Param: {1} -> author
      OR
      Method = GET
      Url: /book/getbynameandauthor?name={1}&author={2}
      Param: {1} -> name, {2} -> author
      ```
   3) Update book data
      ```
      Method = PUT
      Url: /book/edit
      Requestbody json fields: id(Long), name(String), author(String), amount(int)
      ```
   4) Delete book data
      ```
      Method = DELETE
      Url: /book/deletebyid?id={1}
      Param: {1} -> id
      OR
      Method = DELETE
      Url: /book/deletebyname?name={1}&author={2}
      Param: {1} -> name, {2} -> author
      ```
3) Book issue/submit.
   1) Issue book to user
      ```
      Method = POST
      Url: /transaction/issue
      Requestbody json fields: user_name(String), book_name(String), book_author(String), amount(int)
      ```
   2) User submits book
      ```
      Method = POST
      Url: /transaction/submit
      Requestbody json fields: user_name(String), book_name(String), book_author(String), amount(int)
      ```
4) Lists against user/book
   1) Get books issued to user
      ```
      Method = GET
      Url: /listing/booksbyuserid?id={1}
      Param: {1} -> id
      OR
      Method = GET
      Url: /listing/booksbyusername?username={1}
      Param: {1} -> username
      ```
   2) Get users to whom book is issued
      ```
      Method = GET
      Url: /listing/usersbybookid?id={1}
      Param: {1} -> id
      OR
      Method = GET
      Url: /listing/usersbybookname?name={1}&author={2}
      Param: {1} -> name, {2} -> author
      ```
      
