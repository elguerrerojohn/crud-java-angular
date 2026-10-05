import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Tarea } from '../../domain/models/tarea.model';
import { TareaRepository } from '../../domain/repositories/tarea.repository';

/**
 * Caso de uso: obtener una tarea por su id.
 */
@Injectable({ providedIn: 'root' })
export class ObtenerTareaUseCase {
  private readonly repository = inject(TareaRepository);

  execute(id: number): Observable<Tarea> {
    return this.repository.obtenerPorId(id);
  }
}
