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

@NgModule({
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    LayoutModule,
    MatToolbarModule,
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
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    NgScrollbarModule,
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
