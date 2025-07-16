import {Component, inject, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';


export interface ProjectResponse {
  employeeId1: number;
  employeeId2: number;
  projectId: number;
  daysWorked: number;
}

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  standalone: true,
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('employee-client');
  selectedFile = signal<File | null>(null);
  uploading = signal(false);
  uploadResponse = signal<ProjectResponse[]>([]);
  private http: HttpClient = inject(HttpClient);

  isDragging = false;
  showUploadForm: boolean = false;

  toggleUploadForm() {
    this.showUploadForm = !this.showUploadForm;
    if (!this.showUploadForm) {
      this.selectedFile.set(null);
    }
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    this.isDragging = true;
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
    if (event.dataTransfer?.files.length) {
      this.onFileSelected({ target: { files: event.dataTransfer.files } } as any);
    }
  }

  onFileSelected(event: Event): void {
    const input: HTMLInputElement = event.target as HTMLInputElement;
    if (input.files?.length) {
      this.selectedFile.set(input.files[0]);
    }
  }

  onUpload(): void {
    if (!this.selectedFile()) return;

    this.uploading.set(true);
    const formData: FormData = new FormData();
    formData.append('file', this.selectedFile()!);

    this.http.post<ProjectResponse[]>('/api/employee/upload', formData)
      .subscribe({
        next: (data) => {
          this.uploadResponse.set(data);
          this.toggleUploadForm();
        },
        error: err => console.error(err),
        complete: () => this.uploading.set(false)
      });
  }
}
