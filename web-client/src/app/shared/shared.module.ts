import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatDividerModule} from "@angular/material/divider";
import {MatToolbarModule} from '@angular/material/toolbar';
import {LayoutModule} from "@angular/cdk/layout";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {FlexLayoutModule} from "@angular/flex-layout";
import {NgScrollbarModule} from "ngx-scrollbar";
import {NgProgressModule} from "ngx-progressbar";
import {NgProgressHttpModule} from "ngx-progressbar/http";
import {NgProgressRouterModule} from "ngx-progressbar/router";
import {MatTabsModule} from '@angular/material/tabs';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {MatMenuModule} from "@angular/material/menu";
import {MatTableModule} from '@angular/material/table';
// AoT requires an exported function for factories
export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}


@NgModule({
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    FormsModule,
    MatMenuModule,
    LayoutModule,
    MatTableModule,
    HttpClientModule,
    MatToolbarModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    FormsModule,
    FlexLayoutModule,
    NgProgressModule,
    NgProgressHttpModule,
    NgProgressRouterModule,
    MatDividerModule
  ],
  declarations: [],
  exports: [
    CommonModule,
    MatMenuModule,
    HttpClientModule,
    MatButtonModule,
    TranslateModule,
    MatIconModule,
    MatToolbarModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    FormsModule,
    MatSidenavModule,
    MatIconModule,
    NgScrollbarModule,
    MatTableModule,
    NgProgressModule,
    NgProgressHttpModule,
    NgProgressRouterModule,
    FlexLayoutModule,
    MatListModule,
    MatTabsModule,
    MatDividerModule
  ]
})
export class SharedModule {

}
