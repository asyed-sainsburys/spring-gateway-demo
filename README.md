# spring-gateway-demo

Multi-module Maven project:
- gateway-service (Spring Cloud Gateway)
- user-service (simple REST)
- report-service (simple REST + calls user-service)

## Prereqs
- Java 21 (or Java 17+)
- Maven 3.9+

## Build

```bash
mvn -q clean package
```

## Run (3 terminals)

```bash
mvn -pl user-service spring-boot:run
mvn -pl report-service spring-boot:run
mvn -pl gateway-service spring-boot:run
```

## Test via Gateway (port 8080)

```bash
curl http://localhost:8080/user/api/users
curl http://localhost:8080/report/api/reports/summary
```
```bash
curl http://localhost:8081/api/users
```

## Actuator health

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
```
