import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PluginRoutingModule} from './plugin-routing.module';
import {PluginComponent} from './plugin.component';


@NgModule({
  declarations: [
    PluginComponent
  ],
  imports: [
    CommonModule,
    PluginRoutingModule
  ]
})
export class PluginModule {
}
