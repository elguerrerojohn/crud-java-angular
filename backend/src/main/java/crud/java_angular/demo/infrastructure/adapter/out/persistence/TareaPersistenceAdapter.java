package crud.java_angular.demo.infrastructure.adapter.out.persistence;

import crud.java_angular.demo.application.port.out.TareaRepositoryPort;
import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Adaptador de salida de persistencia (Spring Data JPA).
 */
@Component
public class TareaPersistenceAdapter implements TareaRepositoryPort {

    private final SpringDataTareaRepository jpaRepository;
    private final TareaPersistenceMapper mapper;

    public TareaPersistenceAdapter(SpringDataTareaRepository jpaRepository,
                                   TareaPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Tarea guardar(Tarea tarea) {
        // saveAndFlush asegura que @UpdateTimestamp quede aplicado en la respuesta.
        TareaJpaEntity guardada = jpaRepository.saveAndFlush(mapper.toJpaEntity(tarea));
        return mapper.toDomain(guardada);
    }

    @Override
    public List<Tarea> listar(EstadoTarea estado) {
        List<TareaJpaEntity> entidades = (estado == null)
                ? jpaRepository.findAll(Sort.by("id").ascending())
                : jpaRepository.findByEstado(estado);
        return entidades.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Tarea> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void eliminarPorId(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }
}
