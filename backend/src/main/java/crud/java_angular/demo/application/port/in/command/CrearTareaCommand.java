package crud.java_angular.demo.application.port.in.command;

import crud.java_angular.demo.domain.model.EstadoTarea;
import java.time.LocalDate;

/**
 * Comando de entrada para crear una tarea.
 */
public record CrearTareaCommand(
        String titulo,
        String descripcion,
        EstadoTarea estado,
        LocalDate fechaVencimiento
) {
}
