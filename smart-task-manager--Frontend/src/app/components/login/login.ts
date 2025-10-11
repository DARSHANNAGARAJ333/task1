import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'] // ✅ plural form (was styleUrl)
})
export class Login {
  email = '';
  password = '';
  error = '';
  success = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: (res) => {
        console.log('✅ Login success:', res); // debug log

        // ✅ Store JWT if backend returns it as 'token' or adjust the key name
        if (res && res.token) {
          localStorage.setItem('token', res.token);
          this.success = 'Login successful!';
          this.error = '';
          this.router.navigate(['/users']); // redirect after login
        } else {
          this.error = 'Invalid response from server';
        }
      },
      error: (err) => {
        console.error('❌ Login error:', err); // debug log
        this.error = err?.error?.message || 'Login failed';
        this.success = '';
      }
    });
  }
}
