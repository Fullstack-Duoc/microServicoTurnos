package cl.duoc.mineria.turnos.mapper;

import cl.duoc.mineria.turnos.dto.*;
import cl.duoc.mineria.turnos.model.*;

import org.springframework.stereotype.Component;

@Component
// Esta clase se encarga de transformar los datos entre objetos de base de datos (Entidades) y objetos de intercambio (DTOs)
public class TurnoMapper {

    /**
     Convierte un DTO de entrada (Request) en una Entidad (Model) para guardar en BD.
     Nota: Aquí solo seteamos lo que viene del cliente. El ID, las fechas 
     y el estado se asignan en la capa de Servicio por seguridad.
  */

    public Turno toEntity(TurnoRequestDTO requestDTO){
        if (requestDTO == null){
            return null;
        }

        return Turno.builder()
                .usuarioId(requestDTO.getUsuarioId())
                .build();
    }

    /**
     * Convierte una Entidad (Model) en un DTO de salida (Response) para el cliente.
     */

    public TurnoResponseDTO toResponseDTO(Turno turno) {
        if (turno == null) {
            return null;
        }

        return TurnoResponseDTO.builder()
                .id(turno.getId())
                .usuarioId(turno.getUsuarioId())
                .fechaHoraInicio(turno.getFechaHoraInicio())
                .fechaHoraFin(turno.getFechaHoraFin())
                .estado(turno.getEstado())
                .build();
    }
}
