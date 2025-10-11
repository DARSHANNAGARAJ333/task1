import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth'; // Backend base URL

  constructor(private http: HttpClient) {}

  /** Login user */
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials, {
      withCredentials: true // ✅ Important if backend sets cookies or uses CORS
    });
  }

  /** Register user */
  register(user: { fullName: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user, {
      withCredentials: true
    });
  }

  /** Logout user */
  logout(): void {
    localStorage.removeItem('token');
  }

  /** Get stored JWT token */
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  /** Check login state */
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
