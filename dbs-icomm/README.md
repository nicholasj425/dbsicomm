1. should have good test coverage.

2. the app should not lose message if the database connections goes down.
	acknowledge message after save it to database.

3. The app should be able to auto create table if the table doesn’t exist in database.
	spring.datasource.schema=classpath:sql/icomm.sql
	spring.datasource.initialization-mode=always

4. Self-healing from database issue without human intervention.
	Druid connection pool
	
5. Good fault tolerate.
   hystrix to prevent query wait long time.



Core Dependency:

 activemq, eureka client, hystrix, mybatis
	
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-activemq</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
			<version>RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.1</version>
		</dependency>
		
Property Config:
ActiveMQ:
spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=admin

Spring Cloud Eureka Register:
server.port=9001
spring.application.name=dbs-icomm
eureka.client.serviceUrl.defaultZone=http://localhost:7001/eureka
eureka.instance.prefer-ip-address=true

Auto Create Table:
spring.datasource.schema=classpath:sql/icomm.sql
spring.datasource.initialization-mode=always

Druid connection pool:
spring.datasource.druid.initial-size=1
spring.datasource.druid.min-idle=1
spring.datasource.druid.max-active=20
spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.stat-view-servlet.allow=true


Code Package:
com.icomm.IcommApplication
com.icomm.controller.QueueController
com.icomm.jms.*
com.icomm.service.ProducerService
com.icomm.service.impl.ProducerServiceImpl