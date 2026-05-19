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

