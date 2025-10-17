import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChunkUpload } from './chunk-upload';

describe('ChunkUpload', () => {
  let component: ChunkUpload;
  let fixture: ComponentFixture<ChunkUpload>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChunkUpload]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChunkUpload);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
