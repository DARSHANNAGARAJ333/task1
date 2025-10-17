import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  template: `
    <div class="file-upload-card">
      <h2>📤 Upload Document</h2>

      <input type="text" placeholder="Title" [(ngModel)]="title" />
      <input type="text" placeholder="Description" [(ngModel)]="description" />
      <input type="text" placeholder="Uploaded By" [(ngModel)]="uploadedBy" />
      <input type="file" (change)="onFileSelected($event)" />

      <button (click)="uploadFile()" [disabled]="!file || uploading">Upload</button>

      <p *ngIf="uploading" class="status uploading">Uploading...</p>
      <p *ngIf="uploadedFileName" class="status success">
        ✅ {{ uploadedFileName }} uploaded!
      </p>
    </div>
  `,
  styles: [`
    .file-upload-card {
      max-width: 450px;
      margin: 2rem auto;
      padding: 2rem;
      border-radius: 12px;
      border: 2px dashed #007BFF;
      display: flex;
      flex-direction: column;
      gap: 1rem;
      background-color: #f9f9f9;
      box-shadow: 0 4px 15px rgba(0,0,0,0.1);
      text-align: center;
      transition: transform 0.2s;
    }

    .file-upload-card:hover {
      transform: translateY(-3px);
    }

    input[type="text"], input[type="file"] {
      padding: 0.6rem;
      font-size: 1rem;
      border-radius: 6px;
      border: 1px solid #ccc;
      width: 100%;
      box-sizing: border-box;
      transition: border-color 0.3s;
    }

    input[type="text"]:focus {
      border-color: #007BFF;
      outline: none;
    }

    button {
      padding: 0.7rem 1.5rem;
      font-size: 1rem;
      background-color: #007BFF;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.3s, transform 0.2s;
    }

    button:hover:not(:disabled) {
      background-color: #0056b3;
      transform: scale(1.05);
    }

    button:disabled {
      background-color: #aaa;
      cursor: not-allowed;
    }

    .status {
      font-weight: bold;
    }

    .status.uploading {
      color: #ff9800;
    }

    .status.success {
      color: #28a745;
    }
  `]
})
export class FileUploadComponent {
  file?: File;
  title = '';
  description = '';
  uploadedBy = '';
  uploading = false;
  uploadedFileName = '';

  private UPLOAD_URL = 'http://localhost:8080/documents/upload';

  constructor(private http: HttpClient) {}

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  uploadFile() {
    if (!this.file) return;
    if (!this.title || !this.description || !this.uploadedBy) {
      return;
    }

    const formData = new FormData();
    formData.append('file', this.file);
    formData.append('title', this.title);
    formData.append('description', this.description);
    formData.append('uploadedBy', this.uploadedBy);

    this.uploading = true;

    this.http.post(this.UPLOAD_URL, formData).subscribe({
      next: (res) => {
        this.uploading = false;
        this.uploadedFileName = this.file!.name;

        // Clear form fields
        this.file = undefined;
        this.title = '';
        this.description = '';
        this.uploadedBy = '';

        // Reset file input
        const fileInput = document.querySelector<HTMLInputElement>('input[type="file"]');
        if (fileInput) fileInput.value = '';
      },
      error: (err) => {
        this.uploading = false;
        console.error('Upload error:', err);
      }
    });
  }
}
