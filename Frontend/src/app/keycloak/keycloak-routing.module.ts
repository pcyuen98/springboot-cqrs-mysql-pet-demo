import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KeycloakPage } from './keycloak.page';

const routes: Routes = [
  {
    path: '',
    component: KeycloakPage,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KeycloakPageRoutingModule {}
