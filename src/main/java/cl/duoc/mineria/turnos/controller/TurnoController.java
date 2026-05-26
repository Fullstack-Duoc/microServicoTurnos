package cl.duoc.mineria.turnos.controller;

import cl.duoc.mineria.turnos.dto.TurnoRequestDTO;
import cl.duoc.mineria.turnos.dto.TurnoResponseDTO;
import cl.duoc.mineria.turnos.service.TurnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    // POST: Abrir un nuevo turno (/turnos/abrir)
    @PostMapping("/abrir")
    public ResponseEntity<TurnoResponseDTO> abrirTurno(@Valid @RequestBody TurnoRequestDTO dto) {
        TurnoResponseDTO nuevoTurno = turnoService.abrirTurno(dto);
        return new ResponseEntity<>(nuevoTurno, HttpStatus.CREATED);
    }

    // PUT: Cerrar un turno existente (/turnos/cerrar/{id})
    @PutMapping("/cerrar/{id}")
    public ResponseEntity<TurnoResponseDTO> cerrarTurno(@PathVariable Long id){
        TurnoResponseDTO respuesta = turnoService.cerrarTurno(id);
        return ResponseEntity.ok(respuesta);
    }

    // GET: Listar todos los turnos (/turnos/obtener-turnos)
    @GetMapping("/obtener-turnos")
    public ResponseEntity<List<TurnoResponseDTO>> listarTodos(){
        List<TurnoResponseDTO> lista = turnoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // GET: Obtener un turno por ID (/turnos/obtener-turnos/{id})
    @GetMapping("/obtener-turnos/{id}")
    public ResponseEntity<TurnoResponseDTO> obtenerTurno(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    // DELETE: Eliminar un turno (/turnos/borrar-turnos/{id})
    @DeleteMapping("/borrar-turnos/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH: Actualizar el estado de un turno (/turnos/cambiar-turnos/{id}/estado)
    @PatchMapping("/cambiar-turnos/{id}/estado")
    public ResponseEntity<TurnoResponseDTO> cambiarEstado(
            @PathVariable Long id, 
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(turnoService.actualizarEstado(id, nuevoEstado));
    }

    // GET: Obtener historial de un usuario (/turnos/obtener-turnos/usuario/{usuarioId})
    @GetMapping("/obtener-turnos/usuario/{usuarioId}")
    public ResponseEntity<List<TurnoResponseDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(turnoService.listarPorUsuario(usuarioId));
    }

    // GET: Obtener turnos filtrados por estado (/turnos/obtener-turnos/estado/{estado})
    @GetMapping("/obtener-turnos/estado/{estado}")
    public ResponseEntity<List<TurnoResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(turnoService.listarPorEstado(estado));
    }

    // DELETE: Limpiar todos los turnos del sistema (/turnos/mantenimiento/limpiar)
    @DeleteMapping("/mantenimiento/limpiar")
    public ResponseEntity<Void> eliminarTurnosEjemplo() {
        turnoService.limpiarTurnos();
        return ResponseEntity.noContent().build();
    }

    // DELETE: Eliminar historial completo de un usuario (/turnos/borrar-turnos/usuario/{usuarioId}/historial)
    @DeleteMapping("/borrar-turnos/usuario/{usuarioId}/historial")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long usuarioId) {
        turnoService.eliminarHistorialUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
    
}
