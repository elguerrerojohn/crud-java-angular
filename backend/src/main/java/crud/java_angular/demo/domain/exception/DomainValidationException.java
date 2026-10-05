package crud.java_angular.demo.domain.exception;

/**
 * Violación de una invariante del dominio (p. ej. un campo obligatorio vacío).
 * Se traduce a 400 en el borde web.
 */
public class DomainValidationException extends RuntimeException {

    public DomainValidationException(String message) {
        super(message);
    }
}
