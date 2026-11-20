package ejemplo_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ejemplo_crud.dto.PersonaDto;
import ejemplo_crud.service.PersonaService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/servicio")
public class PersonaController {

    private final PersonaService personaService;

    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/hola")
    public String saludo() {
        return "Este es un saludo desde el proyecto Prueba CRUD";
    }

    // Consulta por documento usando RequestParam
    // http://localhost:8080/servicio/personas?id=111

    @GetMapping("personas")
    public ResponseEntity<?> getPersona(
            @RequestParam(value = "id", required = false) String documento) {

        if (documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El parámetro 'id' es obligatorio.");
        }

        PersonaDto persona =
                personaService.obtenerPersonaPorDocumento(documento);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento);
        }

        return ResponseEntity.ok(persona);
    }

    // Consulta por documento y profesión usando RequestParam
    // http://localhost:8080/servicio/profesion?id=111&profesion=Ingeniero

    @GetMapping("profesion")
    public ResponseEntity<?> getPersonaIdProfesion(
            @RequestParam(value = "id") String documento,
            @RequestParam String profesion) {

        PersonaDto persona =
                personaService.consultarPersonaIdProfesion(documento, profesion);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: "
                            + documento + " y profesión: " + profesion);
        }

        return ResponseEntity.ok(persona);
    }

    // Consulta por documento usando PathVariable
    // http://localhost:8080/servicio/personas/111

    @GetMapping("personas/{id}")
    public ResponseEntity<?> getPersonaId(
            @PathVariable("id") String documento) {

        if (documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El parámetro 'id' es obligatorio.");
        }

        PersonaDto persona =
                personaService.obtenerPersonaPorDocumento(documento);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento);
        }

        return ResponseEntity.ok(persona);
    }

    // Consulta por documento y profesión usando PathVariable
    // http://localhost:8080/servicio/personas/222/Estudiante

    @GetMapping("personas/{id}/{profesion}")
    public ResponseEntity<?> getPersonaIdProfesion2(
            @PathVariable("id") String documento,
            @PathVariable String profesion) {

        PersonaDto persona =
                personaService.consultarPersonaIdProfesion(documento, profesion);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para"
                            + " el documento: " + documento
                            + " y profesión: " + profesion);
        }

        return ResponseEntity.ok(persona);
    }

    // Consultar lista de personas
    // http://localhost:8080/servicio/personas-list

    @GetMapping("personas-list")
    public ResponseEntity<List<PersonaDto>> getPersonas() {

        List<PersonaDto> personas =
                personaService.obtenerListaPersonas();

        return ResponseEntity.ok(personas);
    }

    // Registrar personas
    // http://localhost:8080/servicio/guardar

    @PostMapping("guardar")
    public ResponseEntity<PersonaDto> guardarPersona(
            @RequestBody PersonaDto persona) {

        PersonaDto miPersona =
                personaService.registrarPersona(persona);

        if (miPersona != null) {
            return ResponseEntity.ok(miPersona);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Validación Login
    // http://localhost:8080/servicio/login

    @PostMapping("login")
    public ResponseEntity<?> validarUsuario(
            @RequestBody PersonaDto persona) {

        PersonaDto miPersona =
                personaService.consultarLogin(persona);

        if (miPersona != null) {
            return ResponseEntity.ok(miPersona);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas.");
        }
    }

    // Actualizar personas
    // http://localhost:8080/servicio/actualizar

    @PutMapping("actualizar")
    public ResponseEntity<?> actualizarPersona(
            @RequestBody PersonaDto persona) {

        PersonaDto personaActualizada =
                personaService.actualizarPersona(persona);

        if (personaActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró una persona con el documento especificado.");
        }

        return ResponseEntity.ok(personaActualizada);
    }

    // Eliminar personas
    // http://localhost:8080/servicio/eliminar/222

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPersona(
            @PathVariable String id) {

        if (id == null || id.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El documento es obligatorio para eliminar una persona.");
        }

        boolean eliminado = personaService.eliminarPersona(id);

        if (eliminado) {
            return ResponseEntity.ok("Persona eliminada exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró una persona con el documento especificado.");
        }
    }
}