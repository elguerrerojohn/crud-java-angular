import { Observable } from 'rxjs';
import { EstadoTarea } from '../models/estado-tarea.enum';
import { Tarea, TareaInput } from '../models/tarea.model';

/**
 * Puerto de salida del dominio para tareas.
 */
export abstract class TareaRepository {
  abstract listar(estado: EstadoTarea | null): Observable<Tarea[]>;
  abstract obtenerPorId(id: number): Observable<Tarea>;
  abstract crear(input: TareaInput): Observable<Tarea>;
  abstract actualizar(id: number, input: TareaInput): Observable<Tarea>;
  abstract eliminar(id: number): Observable<void>;
}
