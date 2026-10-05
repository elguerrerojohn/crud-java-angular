import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { EstadoTarea } from '../../domain/models/estado-tarea.enum';
import { Tarea } from '../../domain/models/tarea.model';
import { TareaRepository } from '../../domain/repositories/tarea.repository';

/**
 * Caso de uso: listar tareas, opcionalmente filtradas por estado.
 */
@Injectable({ providedIn: 'root' })
export class ListarTareasUseCase {
  private readonly repository = inject(TareaRepository);

  execute(estado: EstadoTarea | null): Observable<Tarea[]> {
    return this.repository.listar(estado);
  }
}
