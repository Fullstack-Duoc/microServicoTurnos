package cl.duoc.mineria.turnos.repository;

import cl.duoc.mineria.turnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {


    /**
     Spring Data JPA genera automáticamente la consulta SQL (SELECT * FROM turnos WHERE usuario_id = ?)
     con solo leer el nombre del método.
  */
    List<Turno> findByUsuarioId(Long usuarioId);

    /**
     Util para validar en la regla de negocio si un usuario ya tiene un turno "ABIERTO"
     antes de permitirle abrir uno nuevo.
  */
    Optional<Turno> findByUsuarioIdAndEstado(Long usuarioId, String estado);

    // Busca turnos por un estado específico (ABIERTO o FINALIZADO)
    List<Turno> findByEstado(String estado);
}
