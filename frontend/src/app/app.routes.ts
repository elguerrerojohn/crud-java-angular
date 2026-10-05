import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'tareas',
    loadComponent: () =>
      import('@presentation/tareas/tareas-page').then((m) => m.TareasPage),
  },
  { path: '', redirectTo: 'tareas', pathMatch: 'full' },
  { path: '**', redirectTo: 'tareas' },
];
