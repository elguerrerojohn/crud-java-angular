package crud.java_angular.demo.domain.exception;

/**
 * Excepción de dominio: se lanza cuando no existe una tarea con el id pedido.
 */
public class TareaNotFoundException extends RuntimeException {

    public TareaNotFoundException(Long id) {
        super("Tarea no encontrada con id " + id);
    }
}
