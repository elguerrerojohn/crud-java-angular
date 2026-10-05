package crud.java_angular.demo.domain.model;

import crud.java_angular.demo.domain.exception.DomainValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad de dominio Tarea. POJO puro: no conoce JPA, Spring ni ninguna
 * infraestructura. Encapsula sus propias reglas de negocio (invariantes).
 */
public class Tarea {

    private final Long id;
    private String titulo;
    private String descripcion;
    private EstadoTarea estado;
    private LocalDate fechaVencimiento;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Tarea(Long id,
                 String titulo,
                 String descripcion,
                 EstadoTarea estado,
                 LocalDate fechaVencimiento,
                 LocalDateTime fechaCreacion,
                 LocalDateTime fechaActualizacion) {
        this.id = id;
        this.titulo = requerirTitulo(titulo);
        this.descripcion = descripcion;
        this.estado = estado != null ? estado : EstadoTarea.PENDIENTE;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    /** Crea una tarea nueva (aún no persistida). */
    public static Tarea nueva(String titulo, String descripcion, EstadoTarea estado, LocalDate fechaVencimiento) {
        return new Tarea(null, titulo, descripcion, estado, fechaVencimiento, null, null);
    }

    /** Aplica una modificación. Si {@code estado} es null, conserva el actual. */
    public void actualizar(String titulo, String descripcion, EstadoTarea estado, LocalDate fechaVencimiento) {
        this.titulo = requerirTitulo(titulo);
        this.descripcion = descripcion;
        if (estado != null) {
            this.estado = estado;
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    private static String requerirTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new DomainValidationException("El título de la tarea es obligatorio");
        }
        return titulo;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
}
