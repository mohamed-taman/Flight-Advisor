import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCommentsComponent } from './show-comments.component';

describe('CommentComponent', () => {
  let component: ShowCommentsComponent;
  let fixture: ComponentFixture<ShowCommentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowCommentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
