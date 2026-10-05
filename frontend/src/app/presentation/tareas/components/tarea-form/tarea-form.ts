import { Component, computed, effect, inject, input, output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { EstadoTarea } from '@core/domain/models/estado-tarea.enum';
import { Tarea, TareaInput } from '@core/domain/models/tarea.model';

/**
 * Formulario de alta/edición de tareas (presentacional).
 */
@Component({
  selector: 'app-tarea-form',
  imports: [ReactiveFormsModule],
  templateUrl: './tarea-form.html',
  styleUrl: './tarea-form.scss',
})
export class TareaForm {
  private readonly fb = inject(FormBuilder);

  readonly tareaEnEdicion = input<Tarea | null>(null);
  readonly guardar = output<TareaInput>();
  readonly cancelar = output<void>();

  readonly estados = Object.values(EstadoTarea);
  readonly esEdicion = computed(() => this.tareaEnEdicion() !== null);

  readonly form = this.fb.nonNullable.group({
    titulo: ['', [Validators.required, Validators.maxLength(255)]],
    descripcion: ['', [Validators.maxLength(1000)]],
    estado: [EstadoTarea.PENDIENTE],
    fechaVencimiento: [this.hoy()],
  });

  constructor() {
    effect(() => {
      const tarea = this.tareaEnEdicion();
      if (tarea) {
        this.form.setValue({
          titulo: tarea.titulo,
          descripcion: tarea.descripcion ?? '',
          estado: tarea.estado,
          fechaVencimiento: tarea.fechaVencimiento ?? '',
        });
      } else {
        this.reiniciar();
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const v = this.form.getRawValue();
    this.guardar.emit({
      titulo: v.titulo,
      descripcion: v.descripcion || null,
      estado: v.estado,
      fechaVencimiento: v.fechaVencimiento || null,
    });
    if (!this.esEdicion()) {
      this.reiniciar();
    }
  }

  onCancelar(): void {
    this.cancelar.emit();
  }

  private reiniciar(): void {
    this.form.reset({
      titulo: '',
      descripcion: '',
      estado: EstadoTarea.PENDIENTE,
      fechaVencimiento: this.hoy(),
    });
  }

  /** Fecha de hoy en formato yyyy-MM-dd (local) para el input type="date". */
  private hoy(): string {
    const d = new Date();
    const mes = String(d.getMonth() + 1).padStart(2, '0');
    const dia = String(d.getDate()).padStart(2, '0');
    return `${d.getFullYear()}-${mes}-${dia}`;
  }
}
