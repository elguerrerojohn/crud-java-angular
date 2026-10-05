package crud.java_angular.demo.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import crud.java_angular.demo.application.port.in.command.ActualizarTareaCommand;
import crud.java_angular.demo.application.port.in.command.CrearTareaCommand;
import crud.java_angular.demo.application.port.out.TareaRepositoryPort;
import crud.java_angular.demo.domain.exception.TareaNotFoundException;
import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios del caso de uso: Mockito sobre el puerto de salida.
 */
@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepositoryPort repository;

    @InjectMocks
    private TareaService service;

    private Tarea tareaPersistida(Long id, String titulo, EstadoTarea estado) {
        LocalDateTime now = LocalDateTime.now();
        return new Tarea(id, titulo, "desc", estado, null, now, now);
    }

    @Test
    @DisplayName("crear() guarda la tarea y devuelve el resultado persistido")
    void crearGuardaYDevuelve() {
        CrearTareaCommand cmd = new CrearTareaCommand("Titulo", "d", EstadoTarea.PENDIENTE, null);
        when(repository.guardar(any(Tarea.class)))
                .thenReturn(tareaPersistida(1L, "Titulo", EstadoTarea.PENDIENTE));

        Tarea result = service.crear(cmd);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).guardar(any(Tarea.class));
    }

    @Test
    @DisplayName("obtenerPorId() inexistente lanza TareaNotFoundException")
    void obtenerInexistenteLanza() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerPorId(99L))
                .isInstanceOf(TareaNotFoundException.class);
    }

    @Test
    @DisplayName("actualizar() existente modifica el dominio y persiste")
    void actualizarExistenteModificaYGuarda() {
        Tarea existente = tareaPersistida(1L, "Viejo", EstadoTarea.PENDIENTE);
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(existente));
        when(repository.guardar(existente)).thenReturn(existente);

        service.actualizar(1L, new ActualizarTareaCommand("Nuevo", "nd", EstadoTarea.COMPLETADA, null));

        assertThat(existente.getTitulo()).isEqualTo("Nuevo");
        assertThat(existente.getEstado()).isEqualTo(EstadoTarea.COMPLETADA);
        verify(repository).guardar(existente);
    }

    @Test
    @DisplayName("eliminar() inexistente lanza y no borra")
    void eliminarInexistenteLanza() {
        when(repository.existePorId(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.eliminar(99L))
                .isInstanceOf(TareaNotFoundException.class);
        verify(repository, never()).eliminarPorId(any());
    }

    @Test
    @DisplayName("eliminar() existente borra por id")
    void eliminarExistenteBorra() {
        when(repository.existePorId(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(repository).eliminarPorId(1L);
    }
}
