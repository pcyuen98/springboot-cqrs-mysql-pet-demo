import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WriterPage } from './writer.page';

const routes: Routes = [
  {
    path: '',
    component: WriterPage,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WriterPageRoutingModule {}
