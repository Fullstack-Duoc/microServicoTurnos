package cl.duoc.mineria.turnos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
// Este DTO se utiliza para capturar y validar la información que el cliente envía al solicitar un nuevo turno
public class TurnoRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio para abrir un turno")
    private Long usuarioId;

}
