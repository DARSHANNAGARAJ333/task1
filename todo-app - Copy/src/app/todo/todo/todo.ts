import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

interface Task {
  title: string;
  description?: string;
  completed: boolean;
}

@Component({
  selector: 'app-todo',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './todo.html'
})
export class TodoComponent {
  tasks: Task[] = JSON.parse(localStorage.getItem('tasks') || '[]');
  taskForm: FormGroup; // declare without initializing

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    // Initialize the form here
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: ['']
    });
  }

  addTask() {
    if (this.taskForm.invalid) return;
    this.tasks.push({ ...this.taskForm.value, completed: false } as Task);
    localStorage.setItem('tasks', JSON.stringify(this.tasks));
    this.taskForm.reset();
  }

  toggleComplete(task: Task) {
    task.completed = !task.completed;
    localStorage.setItem('tasks', JSON.stringify(this.tasks));
  }

  deleteTask(index: number) {
    this.tasks.splice(index, 1);
    localStorage.setItem('tasks', JSON.stringify(this.tasks));
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
