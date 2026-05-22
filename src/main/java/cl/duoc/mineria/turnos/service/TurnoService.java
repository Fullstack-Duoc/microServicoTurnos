package cl.duoc.mineria.turnos.service;

import cl.duoc.mineria.turnos.dto.TurnoRequestDTO;
import cl.duoc.mineria.turnos.dto.TurnoResponseDTO;
import cl.duoc.mineria.turnos.exception.TurnoInvalidoException;
import cl.duoc.mineria.turnos.mapper.TurnoMapper;
import cl.duoc.mineria.turnos.model.Turno;
import cl.duoc.mineria.turnos.repository.TurnoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final TurnoMapper turnoMapper;

    public TurnoResponseDTO abrirTurno(TurnoRequestDTO request){
        // Validamos que no tenga un turno activo

        turnoRepository.findByUsuarioIdAndEstado(request.getUsuarioId(), "ABIERTO")
                .ifPresent(turnoExistente -> {
                    throw new TurnoInvalidoException("El usuario ya tiene un turno Activo en este momento");
                });

        Turno nuevoTurno = turnoMapper.toEntity(request);
        nuevoTurno.setFechaHoraInicio(LocalDateTime.now());

    }

}
