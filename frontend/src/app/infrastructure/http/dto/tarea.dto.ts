/**
 * DTOs del contrato HTTP de tareas (detalle de infraestructura).
 */
export interface TareaResponseDto {
  id: number;
  titulo: string;
  descripcion: string | null;
  estado: string;
  fechaVencimiento: string | null;
  fechaCreacion: string;
  fechaActualizacion: string;
}

export interface TareaRequestDto {
  titulo: string;
  descripcion: string | null;
  estado: string | null;
  fechaVencimiento: string | null;
}
