import { Component, input, output } from '@angular/core';
import { EstadoTarea } from '@core/domain/models/estado-tarea.enum';
import { Tarea } from '@core/domain/models/tarea.model';

/**
 * Lista de tareas (presentacional). Emite eventos de editar/eliminar.
 */
@Component({
  selector: 'app-tarea-list',
  templateUrl: './tarea-list.html',
  styleUrl: './tarea-list.scss',
})
export class TareaList {
  readonly tareas = input.required<Tarea[]>();
  readonly editar = output<Tarea>();
  readonly eliminar = output<number>();

  claseFila(estado: EstadoTarea): string {
    return `tarea tarea--${estado.toLowerCase()}`;
  }

  claseEstado(estado: EstadoTarea): string {
    switch (estado) {
      case EstadoTarea.PENDIENTE:
        return 'badge badge--pendiente';
      case EstadoTarea.EN_PROGRESO:
        return 'badge badge--progreso';
      case EstadoTarea.COMPLETADA:
        return 'badge badge--completada';
    }
  }

  etiquetaEstado(estado: EstadoTarea): string {
    switch (estado) {
      case EstadoTarea.PENDIENTE:
        return 'Pendiente';
      case EstadoTarea.EN_PROGRESO:
        return 'En progreso';
      case EstadoTarea.COMPLETADA:
        return 'Completada';
    }
  }
}
