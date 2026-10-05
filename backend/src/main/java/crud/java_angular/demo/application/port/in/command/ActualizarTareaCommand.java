package crud.java_angular.demo.application.port.in.command;

import crud.java_angular.demo.domain.model.EstadoTarea;
import java.time.LocalDate;

/**
 * Comando de entrada para actualizar una tarea existente.
 */
public record ActualizarTareaCommand(
        String titulo,
        String descripcion,
        EstadoTarea estado,
        LocalDate fechaVencimiento
) {
}
