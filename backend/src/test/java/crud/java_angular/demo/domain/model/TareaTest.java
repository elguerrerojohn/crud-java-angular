package crud.java_angular.demo.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import crud.java_angular.demo.domain.exception.DomainValidationException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios del dominio: puro JUnit, sin Spring ni base de datos.
 */
class TareaTest {

    @Test
    @DisplayName("nueva() sin estado queda en PENDIENTE y sin id")
    void nuevaConEstadoNullQuedaPendiente() {
        Tarea tarea = Tarea.nueva("Titulo", "Desc", null, null);

        assertThat(tarea.getEstado()).isEqualTo(EstadoTarea.PENDIENTE);
        assertThat(tarea.getId()).isNull();
    }

    @Test
    @DisplayName("nueva() con título en blanco lanza DomainValidationException")
    void nuevaConTituloVacioFalla() {
        assertThatThrownBy(() -> Tarea.nueva("   ", null, null, null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessageContaining("título");
    }

    @Test
    @DisplayName("actualizar() modifica los campos indicados")
    void actualizarCambiaCampos() {
        Tarea tarea = Tarea.nueva("Viejo", "d", EstadoTarea.PENDIENTE, null);

        tarea.actualizar("Nuevo", "nd", EstadoTarea.COMPLETADA, LocalDate.of(2026, 1, 1));

        assertThat(tarea.getTitulo()).isEqualTo("Nuevo");
        assertThat(tarea.getEstado()).isEqualTo(EstadoTarea.COMPLETADA);
        assertThat(tarea.getFechaVencimiento()).isEqualTo(LocalDate.of(2026, 1, 1));
    }

    @Test
    @DisplayName("actualizar() con estado null conserva el estado actual")
    void actualizarConEstadoNullConservaEstado() {
        Tarea tarea = Tarea.nueva("t", "d", EstadoTarea.EN_PROGRESO, null);

        tarea.actualizar("t2", "d2", null, null);

        assertThat(tarea.getEstado()).isEqualTo(EstadoTarea.EN_PROGRESO);
    }
}
