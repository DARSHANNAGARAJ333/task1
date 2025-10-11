// src/app/app.ts
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router'; // needed for <router-outlet>

@Component({
  selector: 'app-root',
  standalone: true,          // important
  imports: [RouterModule],   // <-- add this!
  template: `<router-outlet></router-outlet>` // renders Login/Register/Users pages
})
export class App {}
