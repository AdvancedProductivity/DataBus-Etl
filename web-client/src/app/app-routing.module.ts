import {NgModule} from '@angular/core';
import {NavigationEnd, RouterModule, Routes} from '@angular/router';
import {NgProgressModule} from "ngx-progressbar";
import {NgProgressHttpModule} from "ngx-progressbar/http";
import {NgProgressRouterModule} from "ngx-progressbar/router";

const routes: Routes = [
  {path: '', redirectTo: '/task', pathMatch: 'full'},
  {path: 'task', loadChildren: () => import('./task/task.module').then(m => m.TaskModule)},
  {path: 'plugin', loadChildren: () => import('./plugin/plugin.module').then(m => m.PluginModule)}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    NgProgressModule,
    NgProgressHttpModule,
    NgProgressRouterModule.withConfig({
      completeEvents: [NavigationEnd],
      delay: 200,
      id: 'top-pro'
    }),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
