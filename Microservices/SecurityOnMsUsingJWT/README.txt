
Simple Spring Boot Microservices Project
---------------------------------------

Modules:
- eureka-server (port 8761)
- api-gateway (port 8080)
- user-service (port 9001)
- product-service (port 9002)

Run order:
1. mvn clean install
2. Start EurekaServerApplication
3. Start ProductServiceApplication
4. Start UserServiceApplication
5. Start ApiGatewayApplication

Test:
GET http://localhost:8080/user-service/users/1
