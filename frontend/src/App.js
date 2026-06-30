import { useEffect, useState } from 'react';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';
const USERS_ENDPOINT = `${API_BASE_URL}/user/api/users`;
const LOGIN_ENDPOINT = `${API_BASE_URL}/oauth2/authorization/azure`;
const LOGOUT_ENDPOINT = `${API_BASE_URL}/logout`;

function App() {
  const [users, setUsers] = useState([]);
  const [status, setStatus] = useState('loading');
  const [error, setError] = useState('');

  useEffect(() => {
    loadUsers();
  }, []);

  async function loadUsers() {
    setStatus('loading');
    setError('');

    try {
      const response = await fetch(USERS_ENDPOINT, {
        credentials: 'include'
      });

      if (response.status === 401 || response.status === 403) {
        setStatus('unauthenticated');
        return;
      }

      if (!response.ok) {
        throw new Error(`Request failed with HTTP ${response.status}`);
      }

      const payload = await response.json();
      const normalized = Array.isArray(payload) ? payload : payload ? [payload] : [];
      setUsers(normalized);
      setStatus('ready');
    } catch (err) {
      // In BFF flow, unauthenticated calls can become cross-origin redirects and look like network errors in fetch.
      if (err instanceof TypeError) {
        setStatus('unauthenticated');
        return;
      }

      setStatus('error');
      setError(err.message || 'Unexpected error while loading users');
    }
  }

  function login() {
    window.location.assign(LOGIN_ENDPOINT);
  }

  async function logout() {
    try {
      await fetch(LOGOUT_ENDPOINT, {
        method: 'POST',
        credentials: 'include'
      });
    } finally {
      setUsers([]);
      setStatus('unauthenticated');
    }
  }

  return (
    <main className="page">
      <section className="card">
        <header className="row">
          <h1>Users</h1>
          <div className="row gap">
            <button type="button" onClick={loadUsers}>Refresh</button>
            {status === 'ready' && (
              <button type="button" className="secondary" onClick={logout}>Logout</button>
            )}
          </div>
        </header>

        {status === 'loading' && <p>Loading users...</p>}

        {status === 'unauthenticated' && (
          <div>
            <p>You are not logged in.</p>
            <button type="button" onClick={login}>Login</button>
          </div>
        )}

        {status === 'error' && <p className="error">{error}</p>}

        {status === 'ready' && users.length === 0 && <p>No users returned by API.</p>}

        {status === 'ready' && users.length > 0 && (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user, index) => (
                <tr key={user.id ?? index}>
                  <td>{user.id ?? '-'}</td>
                  <td>{user.name ?? '-'}</td>
                  <td>{user.email ?? '-'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    </main>
  );
}

export default App;
