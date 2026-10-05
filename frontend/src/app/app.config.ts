import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch } from '@angular/common/http';

import { routes } from './app.routes';
import { TareaRepository } from '@core/domain/repositories/tarea.repository';
import { TareaHttpRepository } from '@infrastructure/http/tarea-http.repository';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withFetch()),
    // Inversión de dependencias: el puerto de dominio se resuelve con el
    // adaptador HTTP de infraestructura.
    { provide: TareaRepository, useClass: TareaHttpRepository },
  ],
};
