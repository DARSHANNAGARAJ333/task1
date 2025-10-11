import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrls: ['./register.scss']
})
export class Register {
  fullName = '';
  email = '';
  password = '';
  error = '';
  success = '';

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    this.authService.register({
      fullName: this.fullName,
      email: this.email,
      password: this.password
    }).subscribe({
      next: (res: any) => {
        // ✅ Use backend message
        this.success = res.message;
        this.error = '';
        this.fullName = '';
        this.email = '';
        this.password = '';
      },
      error: (err) => {
        // ✅ Show backend error message or fallback
        this.error = err.error.message || 'Registration failed';
        this.success = '';
      }
    });
  }
}
