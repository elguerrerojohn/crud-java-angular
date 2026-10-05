package crud.java_angular.demo.infrastructure.config;

import crud.java_angular.demo.application.port.in.TareaUseCase;
import crud.java_angular.demo.application.port.in.command.CrearTareaCommand;
import crud.java_angular.demo.domain.model.EstadoTarea;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Carga tareas de ejemplo al arrancar, solo si no hay ninguna (idempotente).
 * Se puede desactivar con {@code app.seed.enabled=false} (está activo por
 * defecto para que el demo funcione sin pasos extra).
 */
@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    @ConditionalOnProperty(prefix = "app.seed", name = "enabled", havingValue = "true", matchIfMissing = true)
    ApplicationRunner seedTareas(TareaUseCase tareaUseCase) {
        return args -> {
            if (!tareaUseCase.listar(null).isEmpty()) {
                return;
            }
            LocalDate hoy = LocalDate.now();
            tareaUseCase.crear(new CrearTareaCommand(
                    "Diseñar la base de datos",
                    "Modelar la entidad Tarea y sus estados.",
                    EstadoTarea.COMPLETADA, hoy.minusDays(3)));
            tareaUseCase.crear(new CrearTareaCommand(
                    "Implementar la API REST",
                    "Endpoints CRUD en /api/tareas con Clean Architecture.",
                    EstadoTarea.EN_PROGRESO, hoy.plusDays(5)));
            tareaUseCase.crear(new CrearTareaCommand(
                    "Construir el frontend Angular",
                    "Listado y formulario consumiendo la API.",
                    EstadoTarea.PENDIENTE, hoy.plusDays(12)));
            tareaUseCase.crear(new CrearTareaCommand(
                    "Escribir tests",
                    "Dominio, caso de uso y slice web.",
                    EstadoTarea.PENDIENTE, hoy.plusDays(8)));
            tareaUseCase.crear(new CrearTareaCommand(
                    "Documentar el README",
                    "Cómo levantar backend y frontend.",
                    EstadoTarea.COMPLETADA, null));
            log.info("[seed] Tareas de ejemplo cargadas");
        };
    }
}
