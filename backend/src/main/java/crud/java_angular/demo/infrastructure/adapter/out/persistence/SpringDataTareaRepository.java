package crud.java_angular.demo.infrastructure.adapter.out.persistence;

import crud.java_angular.demo.domain.model.EstadoTarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data JPA sobre la entidad de persistencia.
 */
public interface SpringDataTareaRepository extends JpaRepository<TareaJpaEntity, Long> {

    List<TareaJpaEntity> findByEstado(EstadoTarea estado);
}
