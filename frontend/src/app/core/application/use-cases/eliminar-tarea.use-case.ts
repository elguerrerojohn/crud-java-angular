import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { TareaRepository } from '../../domain/repositories/tarea.repository';

/**
 * Caso de uso: eliminar una tarea por su id.
 */
@Injectable({ providedIn: 'root' })
export class EliminarTareaUseCase {
  private readonly repository = inject(TareaRepository);

  execute(id: number): Observable<void> {
    return this.repository.eliminar(id);
  }
}
