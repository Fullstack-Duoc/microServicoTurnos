package cl.duoc.mineria.turnos.service;

import cl.duoc.mineria.turnos.dto.TurnoRequestDTO;
import cl.duoc.mineria.turnos.dto.TurnoResponseDTO;
import cl.duoc.mineria.turnos.exception.TurnoInvalidoException;
import cl.duoc.mineria.turnos.mapper.TurnoMapper;
import cl.duoc.mineria.turnos.model.Turno;
import cl.duoc.mineria.turnos.repository.TurnoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private TurnoMapper turnoMapper;
    
    @Autowired
    private WebClient webClient;

    // 1. Abrir un nuevo turno (POST)
    public TurnoResponseDTO abrirTurno(TurnoRequestDTO request){
        // Validamos que no tenga un turno activo

        turnoRepository.findByUsuarioIdAndEstado(request.getUsuarioId(), "ABIERTO")
                .ifPresent(turnoExistente -> {
                    throw new TurnoInvalidoException("El usuario ya tiene un turno Activo en este momento");
                });

        Turno nuevoTurno = turnoMapper.toEntity(request);
        nuevoTurno.setFechaHoraInicio(LocalDateTime.now());
        nuevoTurno.setEstado("ABIERTO");

        Turno turnoGuardado = turnoRepository.save(nuevoTurno);
        return turnoMapper.toResponseDTO(turnoGuardado);
    }

    // 2. Cerrar un turno existente (PUT)
    public TurnoResponseDTO cerrarTurno(Long id){
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoInvalidoException("No se encontró ningun turno con el ID: " + id));
        if ("FINALIZADO".equals(turno.getEstado())){
            throw new TurnoInvalidoException("Este turno ya se encuentra cerrado");
        }
        turno.setFechaHoraFin(LocalDateTime.now());
        turno.setEstado("FINALIZADO");

        Turno turnoActualizado = turnoRepository.save(turno);
        return turnoMapper.toResponseDTO(turnoActualizado);
    }

    // 3. Listar todos los turnos (GET)
    public List<TurnoResponseDTO> listarTodos(){
        return turnoRepository.findAll().stream()
                .map(turnoMapper::toResponseDTO)
                .toList();
    }

    // 4. Obtener un turno por ID (GET)
    public TurnoResponseDTO obtenerPorId(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoInvalidoException("No se encontró ningún turno con el ID: " + id));
        return turnoMapper.toResponseDTO(turno);
    }

    // 5. Eliminar un turno (DELETE)
    public void eliminarTurno(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new TurnoInvalidoException("No se encontró ningún turno con el ID: " + id);
        }
        turnoRepository.deleteById(id);
    }

    // 6. Actualizar el estado de un turno (PATCH)
    public TurnoResponseDTO actualizarEstado(Long id, String nuevoEstado) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoInvalidoException("No se encontró ningún turno con el ID: " + id));
        
        turno.setEstado(nuevoEstado);
        
        // Si el estado es FINALIZADO y no tenía fecha de fin, se la ponemos
        if ("FINALIZADO".equalsIgnoreCase(nuevoEstado) && turno.getFechaHoraFin() == null) {
            turno.setFechaHoraFin(LocalDateTime.now());
        }
        
        Turno turnoActualizado = turnoRepository.save(turno);
        return turnoMapper.toResponseDTO(turnoActualizado);
    }

    // 7. Listar turnos por Usuario (GET)
    public List<TurnoResponseDTO> listarPorUsuario(Long usuarioId) {
        return turnoRepository.findByUsuarioId(usuarioId).stream()
                .map(turnoMapper::toResponseDTO)
                .toList();
    }

    // 8. Listar turnos filtrando por un estado (GET)
    public List<TurnoResponseDTO> listarPorEstado(String estado) {
        return turnoRepository.findByEstado(estado).stream()
                .map(turnoMapper::toResponseDTO)
                .toList();
    }

    // 9. Limpiar todos los turnos (DELETE para datos de ejemplo)
    public void limpiarTurnos() {
        // Buscamos los turnos primero por estado
        List<Turno> abiertos = turnoRepository.findByEstado("ABIERTO");
        List<Turno> finalizados = turnoRepository.findByEstado("FINALIZADO");
        
        // Usamos deleteAll, que ya es transaccional por defecto en Spring y no requiere que pongamos el @ aquí
        turnoRepository.deleteAll(abiertos);
        turnoRepository.deleteAll(finalizados);
        
    }

    // 10. Eliminar historial completo de un usuario por renuncia/despido
    @Transactional
    public void eliminarHistorialUsuario(Long usuarioId) {
        // Validación de seguridad: No borrar historial si el usuario tiene un turno activo.
        // Primero debe cerrarse o anularse ese turno específico.
        turnoRepository.findByUsuarioIdAndEstado(usuarioId, "ABIERTO")
                .ifPresent(turno -> {
                    throw new TurnoInvalidoException("No se puede eliminar el historial: el usuario tiene un turno ABIERTO. Ciérrelo antes de proceder.");
                });

        turnoRepository.deleteByUsuarioId(usuarioId);
    }
}
