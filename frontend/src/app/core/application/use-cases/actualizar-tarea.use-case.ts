import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Tarea, TareaInput } from '../../domain/models/tarea.model';
import { TareaRepository } from '../../domain/repositories/tarea.repository';

/**
 * Caso de uso: actualizar una tarea existente.
 */
@Injectable({ providedIn: 'root' })
export class ActualizarTareaUseCase {
  private readonly repository = inject(TareaRepository);

  execute(id: number, input: TareaInput): Observable<Tarea> {
    return this.repository.actualizar(id, input);
  }
}
