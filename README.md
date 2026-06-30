# spring-gateway-demo

Multi-module Maven project:
- gateway-service (Spring Cloud Gateway)
- user-service (simple REST)
- report-service (simple REST + calls user-service)
- React app in `frontend` folder (not covered here, see `frontend/README.md`)

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
curl --location 'http://localhost:9090/realms/local-dev/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=gateway-client' \
--data-urlencode 'username=test' \
--data-urlencode 'password=test123' \
--data-urlencode 'grant_type=password'


docker volume rm keycloak_data

```bash
curl -vH "Authorization: Bearer $AUTH_JWT" http://localhost:8080/user/api/users
```

## Actuator health

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
```



user 

company

licence


-->/companies/ --> users /

company would geneate a jwt and then sent it to users
user would validate it and proceed if fine



http://localhost:8080/user/api/users