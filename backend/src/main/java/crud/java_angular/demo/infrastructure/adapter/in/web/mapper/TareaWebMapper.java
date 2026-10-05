package crud.java_angular.demo.infrastructure.adapter.in.web.mapper;

import crud.java_angular.demo.application.port.in.command.ActualizarTareaCommand;
import crud.java_angular.demo.application.port.in.command.CrearTareaCommand;
import crud.java_angular.demo.infrastructure.adapter.in.web.dto.TareaRequest;
import org.springframework.stereotype.Component;

/**
 * Traduce los DTOs web a los comandos de aplicación.
 */
@Component
public class TareaWebMapper {

    public CrearTareaCommand toCrearCommand(TareaRequest request) {
        return new CrearTareaCommand(
                request.titulo(),
                request.descripcion(),
                request.estado(),
                request.fechaVencimiento());
    }

    public ActualizarTareaCommand toActualizarCommand(TareaRequest request) {
        return new ActualizarTareaCommand(
                request.titulo(),
                request.descripcion(),
                request.estado(),
                request.fechaVencimiento());
    }
}
