### Microservicio de Gestión de Turnos Mineros

Este proyecto corresponde a un microservicio desarrollado con **Java + Spring Boot**, orientado a la administración de turnos dentro de una operación minera.

Su objetivo principal es permitir registrar, consultar y gestionar el ciclo completo de un turno de trabajo de un usuario, controlando estados como **ABIERTO** y **FINALIZADO**, además de validar reglas de negocio asociadas al proceso.

El microservicio expone una API REST que permite:

* **Abrir un nuevo turno** para un usuario.
* **Finalizar un turno activo** registrando su fecha y hora de cierre.
* Consultar turnos por usuario.
* Obtener listados de turnos según su estado.
* Validar que un usuario no pueda abrir más de un turno activo simultáneamente.
* Eliminar o limpiar registros de prueba.

La arquitectura del proyecto está organizada siguiendo una estructura por capas:

* **Controller** → exposición de endpoints REST.
* **Service** → lógica de negocio y validaciones.
* **Repository** → acceso a base de datos mediante Spring Data JPA.
* **DTOs** → transferencia de datos entre cliente y servidor.
* **Mapper** → transformación entre entidades y DTOs.
* **Exception Handling** → manejo global de errores personalizados.

Además, utiliza:

* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **PostgreSQL**
* **Lombok**
* **Bean Validation**
* **Swagger / OpenAPI** para documentación de endpoints

Este microservicio fue pensado como parte de una arquitectura basada en microservicios para la digitalización y automatización de procesos relacionados con la gestión operacional minera.
