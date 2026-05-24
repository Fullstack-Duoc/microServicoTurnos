# Guía de Uso - API de Gestión de Turnos Mineros

Esta API permite gestionar el ciclo de vida de los turnos de los trabajadores. La URL base es: `http://localhost:8082/api/v1/turnos`

---

## 1. Operaciones Principales (Escritura)

### Abrir un nuevo Turno
Inicia la jornada de un trabajador. El sistema valida automáticamente que el usuario **no tenga otro turno abierto** en el momento.
*   **Método:** `POST`
*   **Endpoint:** `/abrir`
*   **Cuerpo (JSON):**
```json
{
    "usuarioId": 101
}
```

### Cerrar un Turno
Finaliza un turno activo. El sistema registra la fecha y hora actual como fin del turno.
*   **Método:** `PUT`
*   **Endpoint:** `/cerrar/{id}`
*   **Ejemplo:** `/cerrar/5`

### Actualizar Estado Manualmente
Permite forzar el cambio de estado (ej. de ABIERTO a FINALIZADO) sin pasar por el flujo estándar.
*   **Método:** `PATCH`
*   **Endpoint:** `/{id}/estado?nuevoEstado={ESTADO}`
*   **Ejemplo:** `/5/estado?nuevoEstado=FINALIZADO`

---

## 2. Eliminación de Datos (Limpieza)

### Eliminar un Turno Individual (Error de Usuario)
Se usa si un trabajador inició un turno por error y se desea borrar ese registro específico de la base de datos.
*   **Método:** `DELETE`
*   **Endpoint:** `/{id}`
*   **Ejemplo:** `/12`

### Eliminar Historial de Usuario (Baja/Despido)
Borra **todos** los registros históricos de un usuario. 
*   **Regla de Seguridad:** No se permite si el usuario tiene un turno `ABIERTO` actualmente.
*   **Método:** `DELETE`
*   **Endpoint:** `/usuario/{usuarioId}/historial`
*   **Ejemplo:** `/usuario/101/historial`

### Limpiar Base de Datos (Mantenimiento)
Borra todos los turnos del sistema (abiertos y cerrados). Útil para pruebas de desarrollo.
*   **Método:** `DELETE`
*   **Endpoint:** `/mantenimiento/limpiar`

---

## 3. Consultas (Lectura)

### Listar todos los turnos
*   **Método:** `GET`
*   **Endpoint:** `/`

### Obtener un turno por ID
*   **Método:** `GET`
*   **Endpoint:** `/{id}`

### Historial por Usuario
*   **Método:** `GET`
*   **Endpoint:** `/usuario/{usuarioId}`

### Filtrar por Estado
*   **Método:** `GET`
*   **Endpoint:** `/estado/{estado}` (Valores: `ABIERTO` o `FINALIZADO`)

---

## 4. Respuestas de Error

Cuando una regla de negocio se rompe, la API devolverá un código `400 Bad Request`.

**Ejemplo al intentar borrar historial con turno abierto:**
```json
{
    "status": 400,
    "message": "No se puede eliminar el historial: el usuario tiene un turno ABIERTO. Ciérrelo antes de proceder.",
    "timestamp": "2023-10-27T14:30:00"
}
```

---

## 5. Pruebas rápidas con cURL

**Abrir Turno:**
```bash
curl -X POST http://localhost:8082/api/v1/turnos/abrir -H "Content-Type: application/json" -d '{"usuarioId": 1}'
```

**Borrar Historial:**
```bash
curl -X DELETE http://localhost:8082/api/v1/turnos/usuario/1/historial
```