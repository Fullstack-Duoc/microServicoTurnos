package cl.duoc.mineria.turnos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Este DTO se utiliza para estructurar la información del turno que se envía como respuesta al cliente
public class TurnoResponseDTO {

    private Long id;
    private Long usuarioId;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String estado;

}
