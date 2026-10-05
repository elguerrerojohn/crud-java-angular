package crud.java_angular.demo.infrastructure.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import crud.java_angular.demo.application.port.in.TareaUseCase;
import crud.java_angular.demo.domain.exception.TareaNotFoundException;
import crud.java_angular.demo.domain.model.EstadoTarea;
import crud.java_angular.demo.domain.model.Tarea;
import crud.java_angular.demo.infrastructure.adapter.in.web.mapper.TareaWebMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test de slice web del controller de tareas.
 */
@WebMvcTest(controllers = TareaController.class)
@Import(TareaWebMapper.class)
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TareaUseCase tareaUseCase;

    private Tarea tarea(Long id, String titulo, EstadoTarea estado) {
        LocalDateTime now = LocalDateTime.now();
        return new Tarea(id, titulo, "desc", estado, null, now, now);
    }

    @Test
    @DisplayName("POST válido devuelve 201 con el cuerpo de la tarea")
    void crearDevuelve201() throws Exception {
        when(tareaUseCase.crear(any())).thenReturn(tarea(1L, "Titulo", EstadoTarea.PENDIENTE));

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Titulo\",\"descripcion\":\"desc\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("POST con título vacío devuelve 400 con detalle de validación")
    void crearTituloVacioDevuelve400() throws Exception {
        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.titulo").exists());
    }

    @Test
    @DisplayName("GET de id inexistente devuelve 404")
    void obtenerInexistenteDevuelve404() throws Exception {
        when(tareaUseCase.obtenerPorId(99L)).thenThrow(new TareaNotFoundException(99L));

        mockMvc.perform(get("/api/tareas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET lista devuelve 200 con las tareas")
    void listarDevuelve200() throws Exception {
        when(tareaUseCase.listar(null)).thenReturn(List.of(tarea(1L, "Titulo", EstadoTarea.PENDIENTE)));

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Titulo"));
    }

    @Test
    @DisplayName("DELETE devuelve 204 e invoca el caso de uso")
    void eliminarDevuelve204() throws Exception {
        mockMvc.perform(delete("/api/tareas/1"))
                .andExpect(status().isNoContent());

        verify(tareaUseCase).eliminar(1L);
    }
}
