import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './service/AuthGuard';
import { Role } from './models/role';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home/undefined',
    pathMatch: 'full',
  },
  {
    path: 'keycloak',
    loadChildren: () => import('./keycloak/keycloak.module').then(m => m.KeycloakPageModule),
    canActivate: [AuthGuard],
    data: { roles: [Role.Keycloak, Role.Reader] }
  },
  {
    path: 'reader',
    loadChildren: () => import('./query/query.module').then(m => m.QueryPageModule),
    canActivate: [AuthGuard],
    data: { roles: [Role.Reader] }
  },
  {
    path: 'home/:redirect',
    loadChildren: () => import('./home/home.module').then(m => m.HomePageModule),
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
