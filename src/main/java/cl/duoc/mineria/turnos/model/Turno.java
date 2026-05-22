package cl.duoc.mineria.turnos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity // Indica que esta clase sera una tabla en la base de datos
@Table(name = "turnos") // Le pone el nombre de la tabla en la base de datos
@Data // Crea automáticamente los Getters and Setters, toString, Equals y HashCode
@NoArgsConstructor // Crea un constructor vacio
@AllArgsConstructor // Crea un constructor con todos los atributos
@Builder // Ayuda a crear objetos de esta clase de forma más sencilla
public class Turno {

    @Id // Marca este campo como la primaryKey
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea auto-incremental
    private Long id;

    @NotNull(message = "El ID de usuario es Obligatorio") // No permite que el campo esté vacío al validar
    @Column(name = "id_usuario", nullable = false) // Configura el nombre y obliga a que no sea nulo en la BD
    private Long usuarioId;

    @NotNull(message = "La fecha y hora de inicio es obligatoria") // Valida que la fecha de inicio no falte
    @Column(name = "fecha_hora_inicio", nullable = false) // Configura el nombre y obligatoriedad en la BD
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin") // Define el nombre de la columna para el fin del turno
    private LocalDateTime fechaHoraFin;

    @NotNull(message = "El estado del turno es obligatorio") // Valida que el estado no sea nulo
    @Column(nullable = false) // Indica que este campo es obligatorio en la base de datos
    private String estado; // Los valores que esperamos son "ABIERTO" o "FINALIZADO"

}
