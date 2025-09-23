import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComponentWriterPage } from './component.writer.page';

const routes: Routes = [
  {
    path: '',
    component: ComponentWriterPage,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ComponentWriterPageRoutingModule {}
