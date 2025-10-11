import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/auth';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './users.html',
  styleUrls: ['./users.scss']
})
export class Users implements OnInit {

  users: any[] = [];
  error = '';
  newUser = { fullName: '', email: '', password: '' };
  editUser: any = null;

  private baseUrl = 'http://localhost:8080/api/users'; // backend URL

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit() {
    this.loadUsers();
  }

  private getAuthHeaders(): { headers: HttpHeaders } {
    const token = this.authService.getToken() || '';
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      })
    };
  }

  loadUsers() {
    this.http.get(this.baseUrl, this.getAuthHeaders()).subscribe({
      next: (res: any) => this.users = res,
      error: (err) => this.error = err.error?.message || 'Failed to load users'
    });
  }

  createUser() {
    this.http.post(this.baseUrl, this.newUser, this.getAuthHeaders()).subscribe({
      next: () => {
        this.newUser = { fullName: '', email: '', password: '' };
        this.loadUsers();
      },
      error: (err) => this.error = err.error?.message || 'Failed to create user'
    });
  }

  updateUser() {
    this.http.put(`${this.baseUrl}/${this.editUser.id}`, this.editUser, this.getAuthHeaders()).subscribe({
      next: () => {
        this.editUser = null;
        this.loadUsers();
      },
      error: (err) => this.error = err.error?.message || 'Failed to update user'
    });
  }

  deleteUser(id: number) {
    this.http.delete(`${this.baseUrl}/${id}`, this.getAuthHeaders()).subscribe({
      next: () => this.loadUsers(),
      error: (err) => this.error = err.error?.message || 'Failed to delete user'
    });
  }

  logout() {
    this.authService.logout();
    window.location.href = '/login';
  }
}
