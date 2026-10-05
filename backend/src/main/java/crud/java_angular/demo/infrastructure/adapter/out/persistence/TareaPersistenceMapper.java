package crud.java_angular.demo.infrastructure.adapter.out.persistence;

import crud.java_angular.demo.domain.model.Tarea;
import org.springframework.stereotype.Component;

/**
 * Traduce entre el dominio {@link Tarea} y la entidad JPA. Aísla al dominio de JPA.
 */
@Component
public class TareaPersistenceMapper {

    public TareaJpaEntity toJpaEntity(Tarea tarea) {
        return TareaJpaEntity.builder()
                .id(tarea.getId())
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .estado(tarea.getEstado())
                .fechaVencimiento(tarea.getFechaVencimiento())
                .fechaCreacion(tarea.getFechaCreacion())
                .fechaActualizacion(tarea.getFechaActualizacion())
                .build();
    }

    public Tarea toDomain(TareaJpaEntity entity) {
        return new Tarea(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getEstado(),
                entity.getFechaVencimiento(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion());
    }
}
