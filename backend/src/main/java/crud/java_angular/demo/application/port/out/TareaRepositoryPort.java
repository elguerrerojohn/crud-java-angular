package crud.java_angular.demo.application.port.out;

import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: contrato de persistencia expresado en términos del dominio.
 */
public interface TareaRepositoryPort {

    Tarea guardar(Tarea tarea);

    List<Tarea> listar(EstadoTarea estado);

    Optional<Tarea> buscarPorId(Long id);

    void eliminarPorId(Long id);

    boolean existePorId(Long id);
}
