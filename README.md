# spring-cloud-example
An example project for practicing Spring Cloud and MSA Architecture.

This is a personal practice project that includes the following elements:

- Eureka service registry
- Hystrix circuit breaker
- Feign declarative REST client
- Ribbon client-side load balancing

## Updated project with the following elements.
- api-member
    - Port : 18081
    - Swagger : http://api-member.default.svc.cluster.local:18081/swagger-ui.html
- api-board
  - Port : 18082
  - Swagger : http://api-board.default.svc.cluster.local:18082/swagger-ui.html

To build the project, run the following command: `./gradlew build -x test`
