import { Component } from '@angular/core';
import { FileService } from '../../services/file';
import { v4 as uuidv4 } from 'uuid';
import { forkJoin } from 'rxjs';
import { CommonModule, DecimalPipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-chunk-upload',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './chunk-upload.html',
  styleUrls: ['./chunk-upload.css'],
  providers: [DecimalPipe]
})
export class ChunkUploadComponent {
  uploading = false;
  progress = 0;
  fileName = '';
  fileSizeMB = 0;
  chunkSize = 5 * 1024 * 1024; // 5 MB

  constructor(private fileService: FileService) {}

  async onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (!file) return;

    this.fileName = file.name;
    this.fileSizeMB = +(file.size / (1024 * 1024)).toFixed(2);
    this.uploading = true;

    const fileId = uuidv4();
    const totalChunks = Math.ceil(file.size / this.chunkSize);
    const uploads = [];

    for (let i = 0; i < totalChunks; i++) {
      const start = i * this.chunkSize;
      const end = Math.min(file.size, start + this.chunkSize);
      const chunk = file.slice(start, end);

      uploads.push(this.fileService.uploadChunk(fileId, i + 1, chunk));
    }

    forkJoin(uploads).subscribe({
      next: () => {
        this.fileService.finalizeUpload(fileId, this.fileName, totalChunks)
          .subscribe(() => {
            this.uploading = false;
            this.progress = 100;
            alert('✅ File uploaded successfully!');
          });
      },
      error: err => {
        console.error(err);
        this.uploading = false;
      }
    });
  }
}
