import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly userKey = 'users';
  private readonly loggedInUserKey = 'loggedInUser';

  signup(user: any) {
    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  login(email: string, password: string): boolean {
    const user = JSON.parse(localStorage.getItem(this.userKey) || '{}');
    if (user.email === email && user.password === password) {
      localStorage.setItem(this.loggedInUserKey, JSON.stringify(user));
      return true;
    }
    return false;
  }

  logout() {
    localStorage.removeItem(this.loggedInUserKey);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem(this.loggedInUserKey);
  }
}
