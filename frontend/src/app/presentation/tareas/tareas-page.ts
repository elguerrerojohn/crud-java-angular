import { Component, OnInit, inject, signal } from '@angular/core';
import { ActualizarTareaUseCase } from '@core/application/use-cases/actualizar-tarea.use-case';
import { CrearTareaUseCase } from '@core/application/use-cases/crear-tarea.use-case';
import { EliminarTareaUseCase } from '@core/application/use-cases/eliminar-tarea.use-case';
import { ListarTareasUseCase } from '@core/application/use-cases/listar-tareas.use-case';
import { EstadoTarea } from '@core/domain/models/estado-tarea.enum';
import { Tarea, TareaInput } from '@core/domain/models/tarea.model';
import { TareaForm } from './components/tarea-form/tarea-form';
import { TareaList } from './components/tarea-list/tarea-list';

/**
 * Componente contenedor (smart): estado con signals, invoca los casos de uso
 * y compone los componentes presentacionales.
 */
@Component({
  selector: 'app-tareas-page',
  imports: [TareaForm, TareaList],
  templateUrl: './tareas-page.html',
  styleUrl: './tareas-page.scss',
})
export class TareasPage implements OnInit {
  private readonly listarUseCase = inject(ListarTareasUseCase);
  private readonly crearUseCase = inject(CrearTareaUseCase);
  private readonly actualizarUseCase = inject(ActualizarTareaUseCase);
  private readonly eliminarUseCase = inject(EliminarTareaUseCase);

  readonly tareas = signal<Tarea[]>([]);
  readonly cargando = signal(false);
  readonly error = signal<string | null>(null);
  readonly enEdicion = signal<Tarea | null>(null);
  readonly filtroEstado = signal<EstadoTarea | null>(null);

  readonly estados = Object.values(EstadoTarea);

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando.set(true);
    this.error.set(null);
    this.listarUseCase.execute(this.filtroEstado()).subscribe({
      next: (tareas) => {
        this.tareas.set(tareas);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las tareas. ¿Está corriendo el backend?');
        this.cargando.set(false);
      },
    });
  }

  onFiltroCambio(valor: string): void {
    this.filtroEstado.set(valor ? (valor as EstadoTarea) : null);
    this.cargar();
  }

  onGuardar(input: TareaInput): void {
    const editando = this.enEdicion();
    const operacion$ = editando
      ? this.actualizarUseCase.execute(editando.id, input)
      : this.crearUseCase.execute(input);

    operacion$.subscribe({
      next: () => {
        this.enEdicion.set(null);
        this.cargar();
      },
      error: () => this.error.set('No se pudo guardar la tarea.'),
    });
  }

  onEditar(tarea: Tarea): void {
    this.enEdicion.set(tarea);
  }

  onCancelarEdicion(): void {
    this.enEdicion.set(null);
  }

  onEliminar(id: number): void {
    if (!confirm('¿Eliminar esta tarea? Esta acción no se puede deshacer.')) {
      return;
    }
    this.eliminarUseCase.execute(id).subscribe({
      next: () => {
        if (this.enEdicion()?.id === id) {
          this.enEdicion.set(null);
        }
        this.cargar();
      },
      error: () => this.error.set('No se pudo eliminar la tarea.'),
    });
  }
}
