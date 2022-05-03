import {Component} from '@angular/core';
import {ThemePalette} from '@angular/material/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'web-client';
  selected = 0;
  activeLink = 'task';
  background: ThemePalette = 'primary';

  gotoPage(link: string) {
    this.activeLink = link
  }
}
