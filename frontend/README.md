# Frontend (React)

This frontend runs on port `3000` and consumes the BFF endpoint `http://localhost:8080/user/api/users`.

## Setup

```bash
cp .env.example .env
npm install
npm start
```

## Behavior

- Calls `GET /user/api/users` with browser cookies (`credentials: include`)
- If unauthenticated (`401` or `403`), shows **Login** button
- Login button redirects browser to backend login endpoint: `/oauth2/authorization/keycloak`
- After successful backend login/session, refresh fetches users list

## Environment

- `REACT_APP_API_BASE_URL` defaults to `http://localhost:8080`



curl -X POST https://login.microsoftonline.com/$MY_AZURE_TENANT_ID/oauth2/v2.0/token  -H "Content-Type: application/x-www-form-urlencoded" -d "client_id=$MY_SERVICE_CLIENT_ID"  -d "client_secret=$MY_SERVICE_CLIENT_SECRET" -d "grant_type=client_credentials" -d "scope=api://a43434b4-8f3a-4213-86dc-5c9b0a521ed3/.default"