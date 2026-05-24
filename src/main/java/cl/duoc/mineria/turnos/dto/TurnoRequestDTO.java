package cl.duoc.mineria.turnos.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
// Este DTO se utiliza para capturar y validar la información que el cliente envía al solicitar un nuevo turno
public class TurnoRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio para abrir un turno")
    private Long usuarioId;

    private String estado;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

}
