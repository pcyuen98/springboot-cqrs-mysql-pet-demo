import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QueryPage as QueryPage } from './query.page';

const routes: Routes = [
  {
    path: '',
    component: QueryPage,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QueryPageRoutingModule {}
