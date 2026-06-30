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
export AUTH_JWT="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaTUR6QUY3b3M1M2ozSHQxd0VlbnpDT3VlXzA4aXBLSlR5djE5X3Y2WWh3In0.eyJleHAiOjE3NzkyMDYzOTIsImlhdCI6MTc3OTIwNjA5MiwianRpIjoib25ydHJvOmI2NWFjODc1LTFjMDgtYjlhOS1mOTEwLWE0MmJjMDAzNWVmOSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6OTA5MC9yZWFsbXMvbG9jYWwtZGV2IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImY5MzM4ZWJjLTU5YTktNDZhOS05ODA0LTAyYmM4MzdjMTE3YiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImdhdGV3YXktY2xpZW50Iiwic2lkIjoiRDlmZUZTbXBnV1A4WDlPYWFaOWZyNVc4IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbG9jYWwtZGV2Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6ImF0ZXN0IHRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0IiwiZ2l2ZW5fbmFtZSI6ImF0ZXN0IiwiZmFtaWx5X25hbWUiOiJ0ZXN0IiwiZW1haWwiOiJ0ZXN0QG5lb2xvcmUuY29tIn0.OXuiio4LFpTU8IjZvWiheLz2HubUYwNALi5IatmKufpZk-wI01EN6iH2TPNZ1jnKc-Nj67Ddy638eHiY29RcCdO-czNqdXAiihGNd7f0Q9sJs4wNpFnzatVLVGz9L7e32aGRekZKLgxdWVGMh8YsfJlm5Ouu0VIehQDQdly80RITO7pImPIS6uB13AzvYh6httyszEx2WdwtwC-XpLktIdUQgutXim9ZsKnCX-pNTWCH_Coq3o7kg2gqGJ8WcQEPxPoSzZKSimBtpp7bhrCQewtKh0eQ7XxSLhZRPOW-eJPw3qo3OP4Vvx9ZbbugqlUEgx1Rlrq5kOIXhQDuTTyfpQ"
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