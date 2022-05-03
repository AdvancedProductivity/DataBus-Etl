import {Component} from '@angular/core';
import {ThemePalette} from '@angular/material/core';
import {TranslateService} from "@ngx-translate/core";

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

  constructor(private translate: TranslateService) {
    // this language will be used as a fallback when a translation isn't found in the current language
    translate.setDefaultLang('en');

    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('en');
  }

  gotoPage(link: string) {
    this.activeLink = link
  }

  changeLanguage(isZh: boolean) {
    if (isZh && this.translate.currentLang !== 'zh') {
      this.translate.use('zh');
    } else if (!isZh && this.translate.currentLang !== 'en') {
      this.translate.use('en');
    }
  }
}
