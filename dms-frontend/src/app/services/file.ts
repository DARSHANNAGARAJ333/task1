import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class FileService {
  private BASE_URL = 'http://localhost:8080/documents'; // ✅ your new URL

  constructor(private http: HttpClient) {}

  uploadChunk(fileId: string, chunkNumber: number, chunk: Blob) {
    const formData = new FormData();
    formData.append('file', chunk);
    formData.append('fileId', fileId);
    formData.append('chunkNumber', chunkNumber.toString());

    return this.http.post(`${this.BASE_URL}/upload`, formData); // send to /documents/upload
  }

  finalizeUpload(fileId: string, fileName: string, totalChunks: number) {
    return this.http.post(`${this.BASE_URL}/finalize-upload`, {
      fileId,
      fileName,
      totalChunks
    });
  }
}
