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
curl --location 'http://localhost:9090/realms/local-dev/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=gateway-client' \
--data-urlencode 'username=test' \
--data-urlencode 'password=test123' \
--data-urlencode 'grant_type=password'
docker volume rm keycloak_data

```bash
export AUTH_JWT="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaTUR6QUY3b3M1M2ozSHQxd0VlbnpDT3VlXzA4aXBLSlR5djE5X3Y2WWh3In0.eyJleHAiOjE3NzkxNzk2ODgsImlhdCI6MTc3OTE3OTM4OCwianRpIjoib25ydHJvOjdkMjRlZmYwLThlMTYtNGE5MS01OGJiLTlkYmIwYjA1M2Q0MSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6OTA5MC9yZWFsbXMvbG9jYWwtZGV2IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImY5MzM4ZWJjLTU5YTktNDZhOS05ODA0LTAyYmM4MzdjMTE3YiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImdhdGV3YXktY2xpZW50Iiwic2lkIjoiTFpqbzU4YU5UajB2eGpxU2NXQjQ3bU02IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbG9jYWwtZGV2Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6ImF0ZXN0IHRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0IiwiZ2l2ZW5fbmFtZSI6ImF0ZXN0IiwiZmFtaWx5X25hbWUiOiJ0ZXN0IiwiZW1haWwiOiJ0ZXN0QG5lb2xvcmUuY29tIn0.BWwKmjY4jJOSI5gq7eaazCbmd8tG889PbqVtBe6bT86Vh5zg1CWnhljoT6wZABh_FSIypAxf9pNAP-KP69lt1cYvRp4JUW2_FVrvk7olZcsllFL5X0cU8vqKcUNrRAGqLmXip4gm7sav3RsngQFKXW81RxXKyVnHjEEWWqTOr6LJ1k9XOKzomM1WbxnQG_yu3f_YrdH78PNpa9bN-zNSqa07MXcA92aTaj-7JnkAcSWHQhuqEuM1vk-Rb-XgTcpSXtZD_xXYgrLSc02CrB3PEJ4syEp0dx3CK89wowcCtMKmQIaaSvpX8iuc1CtvEY0IyCUdpuuBcyfOGPpTKK6C-g"
curl -H "Authorization: Bearer $AUTH_JWT" http://localhost:8080/user/api/users
```

## Actuator health

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
```
