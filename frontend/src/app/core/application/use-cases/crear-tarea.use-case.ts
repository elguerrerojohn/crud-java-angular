import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Tarea, TareaInput } from '../../domain/models/tarea.model';
import { TareaRepository } from '../../domain/repositories/tarea.repository';

/**
 * Caso de uso: crear una nueva tarea.
 */
@Injectable({ providedIn: 'root' })
export class CrearTareaUseCase {
  private readonly repository = inject(TareaRepository);

  execute(input: TareaInput): Observable<Tarea> {
    return this.repository.crear(input);
  }
}
