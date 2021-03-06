
<h1 align="center">
    Yahoo and MongoDB API for A StockData Application
</h1>

<p align="center">
  <img width="400" height="250" src="https://user-images.githubusercontent.com/77422313/164948693-22d3fa06-0967-4e88-84fd-e968ec38efd2.png">
</p>

<br />

## Contents
- [Summary](#summary)
- [How to Compile and Run](#how-to-compile-and-run)
- [Project Preparation](#project-preparation)
	- [SpringBoot and Maven](springBoot-and-maven)
	- [Docker](docker)
	- [MongoDB](mongoDB)
- [Visualizing the Program](#visualizing-the-program)

## Summary
*  The application is able to fetch stock data from the YahooFinance API on any ticker provided by user input, as well as, storing data to a MongoDB database. 
*  CRUD operations to and from the MongoDB database.
*  Spring Boot and Maven are used to manage dependencies and jumpstart the project.
*  Integration of Service/Model/Application packages to provide clarity for class usage.
*  Java OOP and encapsulation methods used.
*  Usage of a MultiValueMap hashSet.

## How to Compile and Run
1. Make sure you first have Maven installed. You can check with the following command in the terminal:

```
$ mvn -v
```

> * If you don't have Maven installed follow the guide below!
> * This YouTube video is a perfect guide for users using a MacOS (Click -> [LINK](https://www.youtube.com/watch?v=j0OnSAP-KtU)). 
> * Note: In the video he is using a bash compiler, if you're using zsh just use that instead as your ".zsh_profile".

2. First locate the directory the folder is located in.
3. Once, you have 'cd' to the correct folder you can compile and run the program with the following command:
```
$ ./mvnw spring-boot:run
```
4. You should then see all the plug-ins and .jar files being initialized and the application will start right after.



## Project Preparation

### _**SpringBoot and Maven**_ 
* Maven is used for building and managing any Java-based project.
* SpringBoot is an open source Java framework that I used for the creation of my application for minimum configurations. To initialize the project I visited the spring-initializr - https://start.spring.io/. For this application I used several dependencies when I have included in the pom.xml snippet below.

```js

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>

	<dependency>
	    <groupId>com.yahoofinance-api</groupId>
	    <artifactId>YahooFinanceAPI</artifactId>
	    <version>3.15.0</version>
	</dependency>

	<dependency>
	    <groupId>org.springframework.data</groupId>
	    <artifactId>spring-data-mongodb</artifactId>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-mongodb</artifactId>
	</dependency>

	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-thymeleaf</artifactId>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<scope>runtime</scope>
		<optional>true</optional>
	</dependency>

	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-web</artifactId>
	</dependency>

	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<optional>true</optional>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
		<exclusions>
			<exclusion>
				<groupId>org.junit.vintage</groupId>
				<artifactId>junit-vintage-engine</artifactId>
			</exclusion>
		</exclusions>
	</dependency>
</dependencies>

```
<br />

##### A quick run down of all the dependencies used:
|Dependency|Description|
| :- | :- |
|YahooFinanceAPI| Stock data and Statistics.|
|spring-boot-starter-data-mongodb| Store data in flexible, JSON-like documents, meaning fields can vary from document to document and data structure can be changed over time.|
|spring-boot-starter-thymeleaf| A modern server-side Java template engine for both web and standalone environments. Allows HTML to be correctly displayed in browsers and as static prototypes.|
|spring-boot-devtools| Provides fast application restarts, LiveReload, and configurations for enhanced development experience.|
|spring-boot-starter-web| Build web, including REST-full, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.|
|lombok| Java annotation library which helps to reduce boilerplate code.|
|spring-boot-starter-test| Applied Junit testing framework for Java. |

<br />
	
### _**Docker**_ 
- We use docker desktop to spin up a container to run our database. It's free to download and create an account and can be found at the link -> [here!](https://www.docker.com/products/docker-desktop/). Note, make sure you choose the correct processor chip, or you'll end up like me wondering why it won't start installing.
	- After downloading Docker Desktop, you will need to make sure you have a `docker-compose.yaml` file in the StockData package for setup of the container.
	- Make sure you set the correct `port:27017` for each service in order to load the `localhost8081` for the database. 
	
	#### Example 'docker-compose.yaml' looks like this:
	```js
	version: "3.8"
	services:
	    mongodb:
		image: mongo
		container_name: mongodb
		ports: 
		    - 27017:27017
		volumes: 
		    - data:/data
		environment: 
		    - MONGO_INITDB_ROOT_USERNAME=rootuser
		    - MONGO_INITDB_ROOT_PASSWORD=rootpass
	    mongo-express:
		image: mongo-express
		container_name: mongo-express
		restart: always
		ports:
		    - 8081:8081
		environment:
		    - ME_CONFIG_MONGODB_ADMINUSERNAME=rootuser
		    - ME_CONFIG_MONGODB_ADMINPASSWORD=rootpass
		    - ME_CONFIG_MONGODB_ENABLE_ADMIN=true
		    - ME_CONFIG_MONGODB_SERVER=mongodb
	volumes: 
	    data: {} 

	networks:
	    default: 
		name: mongodb_network
	```
<br />

* Next you should run your `docker-compose.yaml` file so the container is created and initialized.
	
<br />

### MongoDB
-  MongoDB is a modern database that can be implemented using an interface and SpringBoot.  
	- First thing we want to do to set up our `application.properties` file located in the resource folder. Make sure to use the same reference for `port:27017` and `localhost8081` to set up correctly and have access.
	
	### Example 'application.properties' file looks like this:
	
	```
	spring.data.mongodb.authentication-database=admin
	spring.data.mongodb.username=rootuser
	spring.data.mongodb.password=rootpass
	spring.data.mongodb.database=StockData
	spring.data.mongodp.port=27017
	spring.data.mongodb.host=localhost
	```
	- This file gets scanned and setup when we first initialize our program with SpringBoot.
	- The last thing you must make sure to do is create a database on Mongo and name it "StockData" for it to connect to.
		- This can be done through MongoExpress which should be accessible through the `localport:8081` you initialized. It can be accessed by typing `localhost:8081` on to the browser URL.
	- Once connected to MongoExpress just 'click' on the create a database button and make sure you name it accordingly , "StockData". 
<br />

That's all for setting up the application! You can now compile/run the program and store data to the container!
	
<br />
	
## Visualizing the Program

<br />
	
### YouTube video of application: Demo and Usage.
[![Watch the video](https://img.youtube.com/vi/NRJC9URrzqs/maxresdefault.jpg)](https://youtu.be/NRJC9URrzqs)
	
	
