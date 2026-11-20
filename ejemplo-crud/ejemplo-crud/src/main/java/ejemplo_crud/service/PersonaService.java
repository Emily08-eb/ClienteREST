package ejemplo_crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import ejemplo_crud.dao.PersonaDao;
import ejemplo_crud.dto.PersonaDto;

@Service
public class PersonaService {

    private final PersonaDao personaDao;

    @Autowired
    public PersonaService(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public PersonaDto obtenerPersonaPorDocumento(String documento) {

        PersonaDto persona = personaDao.consultarPersonaIndividual(documento);

        return persona;
    }

    public PersonaDto consultarPersonaIdProfesion(String documento, String profesion) {

        PersonaDto persona = personaDao.consultarPersonaIdProfesion(documento, profesion);

        return persona;
    }

    public List<PersonaDto> obtenerListaPersonas() {
        return personaDao.obtenerListapPersonas();
    }

    public PersonaDto registrarPersona(PersonaDto persona) {

        if (persona == null || persona.getDocumento() == null
                || persona.getDocumento().isEmpty()) {
            return null;
        }

        return personaDao.registrarPersona(persona);
    }

    public PersonaDto consultarLogin(PersonaDto persona) {

        if (persona == null || persona.getDocumento() == null
                || persona.getDocumento().isEmpty()) {
            return null;
        }

        return personaDao.consultarLogin(
                persona.getDocumento(), persona.getPassword());
    }

    public PersonaDto actualizarPersona(PersonaDto persona) {

        if (persona == null || persona.getDocumento() == null
                || persona.getDocumento().isEmpty()) {
            return null;
        }

        PersonaDto personaExistente =
                personaDao.consultarPersonaIndividual(persona.getDocumento());

        if (personaExistente == null) {
            return null;
        }

        return personaDao.actualizarPersona(persona);
    }

    public boolean eliminarPersona(String documento) {

        PersonaDto personaExistente =
                personaDao.consultarPersonaIndividual(documento);

        if (personaExistente == null) {
            return false;
        }

        return personaDao.eliminarPersona(personaExistente);
    }
}