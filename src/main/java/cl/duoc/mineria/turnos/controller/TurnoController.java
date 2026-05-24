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
@RequestMapping("/api/v1/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    // POST: Abrir un nuevo turno
    @PostMapping("/abrir")
    public ResponseEntity<TurnoResponseDTO> abrirTurno(@Valid @RequestBody TurnoRequestDTO dto) {
        TurnoResponseDTO nuevoTurno = turnoService.abrirTurno(dto);
        return new ResponseEntity<>(nuevoTurno, HttpStatus.CREATED);
    }

    // PUT: Cerrar un turno existente
    @PutMapping("/cerrar/{id}")
    public ResponseEntity<TurnoResponseDTO> cerrarTurno(@PathVariable Long id){
        TurnoResponseDTO respuesta = turnoService.cerrarTurno(id);
        return ResponseEntity.ok(respuesta);
    }

    // GET: Listar todos los turnos
    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> listarTodos(){
        List<TurnoResponseDTO> lista = turnoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // GET: Obtener un turno por ID
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> obtenerTurno(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    // DELETE: Eliminar un turno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH: Cambiar el estado fácilmente (Ej: /api/v1/turnos/1/estado?nuevoEstado=FINALIZADO)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TurnoResponseDTO> cambiarEstado(
            @PathVariable Long id, 
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(turnoService.actualizarEstado(id, nuevoEstado));
    }

    // GET: Obtener historial de un usuario (/api/v1/turnos/usuario/1)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TurnoResponseDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(turnoService.listarPorUsuario(usuarioId));
    }

    // GET: Obtener turnos filtrados por estado (/api/v1/turnos/estado/ABIERTO)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TurnoResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(turnoService.listarPorEstado(estado));
    }

    // DELETE: Limpiar todos los datos de ejemplo (ABIERTO y FINALIZADO)
    @DeleteMapping("/mantenimiento/limpiar")
    public ResponseEntity<Void> eliminarTurnosEjemplo() {
        turnoService.limpiarTurnos();
        return ResponseEntity.noContent().build();
    }

    // DELETE: Eliminar todo el historial de un usuario (Ej: /api/v1/turnos/usuario/1/historial)
    @DeleteMapping("/usuario/{usuarioId}/historial")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long usuarioId) {
        turnoService.eliminarHistorialUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
    
}
