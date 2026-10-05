import { EstadoTarea } from './estado-tarea.enum';

/**
 * Entidad de dominio Tarea.
 */
export interface Tarea {
  readonly id: number;
  readonly titulo: string;
  readonly descripcion: string | null;
  readonly estado: EstadoTarea;
  readonly fechaVencimiento: string | null;
  readonly fechaCreacion: string;
  readonly fechaActualizacion: string;
}

/**
 * Datos de entrada para crear o actualizar una tarea.
 */
export interface TareaInput {
  titulo: string;
  descripcion?: string | null;
  estado?: EstadoTarea | null;
  fechaVencimiento?: string | null;
}
