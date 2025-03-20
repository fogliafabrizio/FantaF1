import jwtDecode from 'jwt-decode';

export function isTokenExpired(token: string): boolean {
  if (!token) return true;

  try {
    const decoded: { exp: number } = jwtDecode(token);
    const now = Math.floor(Date.now() / 1000);
    return decoded.exp < now;
  } catch (error) {
    return true;
  }
}
