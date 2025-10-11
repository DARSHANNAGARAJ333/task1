import { Routes } from '@angular/router';
import { Login as LoginComponent } from './components/login/login';
import { Register as RegisterComponent } from './components/register/register';
import { Users as UsersComponent } from './components/users/users';
import { AuthGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
];
