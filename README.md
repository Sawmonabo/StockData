
<h1 align="center">
    Yahoo and Mongo API for StockData application
</h1>

<p align="center">
  <img width="400" height="250" src="https://user-images.githubusercontent.com/77422313/164948693-22d3fa06-0967-4e88-84fd-e968ec38efd2.png">
</p>

<br />

## Contents
- [Summary](#summary)
- [How to Compile and Run](#how-to-compile-and-run)
- [Project Preparation](#project-preparation)
- [Visualizing the Program](#visualizing-the-program)

## Summary
*  The application is able to fetch stock data on any ticker provided by the YahooFinance API, as well as, storing data to a MongoDB database. 
*  In this application we used SpringBoot and Maven to help reduce boilerplate code and to use different dependiccies.
*  Used a Full Stack solution to create a front-end application to the YahooFinance API and a back-end application using MongoDB to create a local data base. 
*  Integration of Service/Model/Application packages to provide clarity for class usage.
*  Java OOP and encapsulation methods used.
*  Usage of a MultiValueMap hashSet.

## How to Compile and Run
1. Make sure you first have Maven installed. You can check with the following command in the terminal:
```
$ mvn -v
```
- If you don't have Maven installed follow the guide below!
- This youtube video is a perfect guide for users using a MacOS (Click -> [LINK](https://www.youtube.com/watch?v=j0OnSAP-KtU)). 
- Note: In the video he is using a bash compiler, if you're using zsh just use that instead as your ".zsh_profile".
	
2. First locate the directory the folder is located in.
3. Once, you have 'cd' to the correct folder you can compile and run the program with the following command:
```
$ ./mvnw spring-boot:run
```
4. You should then see all the plug-ins and .jar files being initialzed and the application will start right after.



## Project Preparation

#### _**SpringBoot and Maven**_ 
<p>Maven is used for building and managing any Java-based project.
<p>SpringBoot is an open source Java framework that I used for the creation of my application for minimum configurations and runnablity. To initialize the project I visited the spring-initializr - https://start.spring.io/. For this applicatrion I used several dependicies when I have included in the pom.xml snippet below.

```js
{
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
},
```
A quick run down of each dependency:
|Dependicies|Description|
| :- | :- |
|YahooFinanceAPI| Stock data and Statistics.|
|spring-boot-starter-data-mongodb| Store data in flexible, JSON-like documents, meaning fields can vary from document to document and data structure can be changed over time.|
|spring-boot-starter-thymeleaf| A modern server-side Java template engine for both web and standalone environments. Allows HTML to be correctly displayed in browsers and as static prototypes.|
|spring-boot-devtools| Provides fast application restarts, LiveReload, and configurations for enhanced development experience.|
|spring-boot-starter-web| Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.|
|lombok| Java annotation library which helps to reduce boilerplate code.|
|spring-boot-starter-test| Applied Junit testing framework for Java. |

<br />
	
### _**Docker**_ 
- Docker Compose file using ".yaml"
	
<br />

### _**Setting up Application Properties File**_ 	

<br />
	
## Visualizing the Program

<br />
	
### Youtube video of application: Demo and Usage.
[![Watch the video](https://img.youtube.com/vi/NRJC9URrzqs/maxresdefault.jpg)](https://youtu.be/NRJC9URrzqs)
	
	
