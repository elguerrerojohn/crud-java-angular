package crud.java_angular.demo.application.port.in;

import crud.java_angular.demo.application.port.in.command.ActualizarTareaCommand;
import crud.java_angular.demo.application.port.in.command.CrearTareaCommand;
import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import java.util.List;

/**
 * Puerto de entrada (input port): casos de uso de tareas.
 */
public interface TareaUseCase {

    Tarea crear(CrearTareaCommand command);

    Tarea actualizar(Long id, ActualizarTareaCommand command);

    void eliminar(Long id);

    Tarea obtenerPorId(Long id);

    List<Tarea> listar(EstadoTarea estado);
}
