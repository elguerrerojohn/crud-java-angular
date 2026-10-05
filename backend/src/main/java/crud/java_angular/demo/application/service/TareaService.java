package crud.java_angular.demo.application.service;

import crud.java_angular.demo.application.port.in.TareaUseCase;
import crud.java_angular.demo.application.port.in.command.ActualizarTareaCommand;
import crud.java_angular.demo.application.port.in.command.CrearTareaCommand;
import crud.java_angular.demo.application.port.out.TareaRepositoryPort;
import crud.java_angular.demo.domain.exception.TareaNotFoundException;
import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de los casos de uso de tareas. Orquesta el dominio y el
 * puerto de persistencia.
 */
@Service
public class TareaService implements TareaUseCase {

    private final TareaRepositoryPort repository;

    public TareaService(TareaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Tarea crear(CrearTareaCommand command) {
        Tarea tarea = Tarea.nueva(
                command.titulo(),
                command.descripcion(),
                command.estado(),
                command.fechaVencimiento());
        return repository.guardar(tarea);
    }

    @Override
    @Transactional
    public Tarea actualizar(Long id, ActualizarTareaCommand command) {
        Tarea tarea = repository.buscarPorId(id)
                .orElseThrow(() -> new TareaNotFoundException(id));
        tarea.actualizar(
                command.titulo(),
                command.descripcion(),
                command.estado(),
                command.fechaVencimiento());
        return repository.guardar(tarea);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existePorId(id)) {
            throw new TareaNotFoundException(id);
        }
        repository.eliminarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Tarea obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new TareaNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarea> listar(EstadoTarea estado) {
        return repository.listar(estado);
    }
}
