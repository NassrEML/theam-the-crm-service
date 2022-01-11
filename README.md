# API Test  -  The CRM Service

## API Requirements
- [X] The API should be only accessible by a registered user by providing an authentication mechanism.
- [X] A user can list all customers in the database.
- [X] A user can get full customer information, including a photo URL. 
- [X] A user can create a new customer.
- [X] A user can update an existing customer.
- [X] A user can delete an existing customer. 

An admin can also manage users: 
- [X] Create users. 
- [X] Delete users. 
- [X] Update users. 
- [X] List users. 
- [X] Change admin status.

## Setup the project

### First run the docker following these commands

```bash
cd docker

docker build -t postgresql .

docker run -dp 5432:5432 postgresql
```

### Second setup the Cloudinary variables in application.properties
```
com.nassreml.cloudinary.cloud-name=CLOUD_NAME
com.nassreml.cloudinary.api-key=API_KEY
com.nassreml.cloudinary.api-secret=API_SECRET
```

### Third run the Spring Application
```bash
mvn spring-boot:run
```
