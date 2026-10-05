package crud.java_angular.demo.infrastructure.adapter.in.web;

import crud.java_angular.demo.application.port.in.TareaUseCase;
import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import crud.java_angular.demo.infrastructure.adapter.in.web.dto.TareaRequest;
import crud.java_angular.demo.infrastructure.adapter.in.web.dto.TareaResponse;
import crud.java_angular.demo.infrastructure.adapter.in.web.mapper.TareaWebMapper;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Adaptador de entrada (driving adapter) REST. Depende del puerto de entrada
 * {@link TareaUseCase}; sin lógica de negocio.
 */
@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaUseCase tareaUseCase;
    private final TareaWebMapper mapper;

    public TareaController(TareaUseCase tareaUseCase, TareaWebMapper mapper) {
        this.tareaUseCase = tareaUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<TareaResponse> listar(@RequestParam(required = false) EstadoTarea estado) {
        return tareaUseCase.listar(estado).stream()
                .map(TareaResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TareaResponse obtener(@PathVariable Long id) {
        return TareaResponse.from(tareaUseCase.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaRequest request,
                                               UriComponentsBuilder uriBuilder) {
        Tarea creada = tareaUseCase.crear(mapper.toCrearCommand(request));
        URI location = uriBuilder.path("/api/tareas/{id}").buildAndExpand(creada.getId()).toUri();
        return ResponseEntity.created(location).body(TareaResponse.from(creada));
    }

    @PutMapping("/{id}")
    public TareaResponse actualizar(@PathVariable Long id, @Valid @RequestBody TareaRequest request) {
        return TareaResponse.from(tareaUseCase.actualizar(id, mapper.toActualizarCommand(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaUseCase.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
