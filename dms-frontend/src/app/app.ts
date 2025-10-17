import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FileUploadComponent } from './components/file-upload/file-upload';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FileUploadComponent],
  template: `
    <h1>DMS Frontend</h1>
    <app-file-upload></app-file-upload>
  `,
  styles: [`
    h1 { text-align: center; margin-top: 2rem; }
  `]
})
export class App {
  protected readonly title = signal('dms-frontend');
}
