package crud.java_angular.demo.infrastructure.adapter.in.web.dto;

import crud.java_angular.demo.domain.model.EstadoTarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO de entrada de la API REST.
 */
public record TareaRequest(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255, message = "El título no puede superar 255 caracteres")
        String titulo,

        @Size(max = 1000, message = "La descripción no puede superar 1000 caracteres")
        String descripcion,

        EstadoTarea estado,

        LocalDate fechaVencimiento
) {
}
