package crud.java_angular.demo.infrastructure.adapter.in.web.dto;

import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de salida de la API REST.
 */
public record TareaResponse(
        Long id,
        String titulo,
        String descripcion,
        EstadoTarea estado,
        LocalDate fechaVencimiento,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {
    public static TareaResponse from(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getEstado(),
                tarea.getFechaVencimiento(),
                tarea.getFechaCreacion(),
                tarea.getFechaActualizacion());
    }
}
