import { EstadoTarea } from '@core/domain/models/estado-tarea.enum';
import { Tarea, TareaInput } from '@core/domain/models/tarea.model';
import { TareaRequestDto, TareaResponseDto } from '../dto/tarea.dto';

/**
 * Traduce entre los DTOs HTTP y el modelo de dominio.
 */
export class TareaMapper {
  static toDomain(dto: TareaResponseDto): Tarea {
    return {
      id: dto.id,
      titulo: dto.titulo,
      descripcion: dto.descripcion,
      estado: dto.estado as EstadoTarea,
      fechaVencimiento: dto.fechaVencimiento,
      fechaCreacion: dto.fechaCreacion,
      fechaActualizacion: dto.fechaActualizacion,
    };
  }

  static toRequest(input: TareaInput): TareaRequestDto {
    return {
      titulo: input.titulo,
      descripcion: input.descripcion ?? null,
      estado: input.estado ?? null,
      fechaVencimiento: input.fechaVencimiento ?? null,
    };
  }
}
