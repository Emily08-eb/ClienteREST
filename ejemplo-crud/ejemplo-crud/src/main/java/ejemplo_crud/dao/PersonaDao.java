package ejemplo_crud.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import ejemplo_crud.dto.PersonaDto;
import ejemplo_crud.util.PersonasUtilidades;

@Repository
public class PersonaDao {

    public PersonaDao() {
        PersonasUtilidades.iniciarLista();
    }

    public PersonaDto consultarPersonaIndividual(String documento) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(documento)) {
                return p;
            }
        }

        return null;
    }

    public PersonaDto consultarPersonaIdProfesion(String documento, String profesion) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(documento)
                    && p.getProfesion().equals(profesion)) {

                PersonaDto personaVo = new PersonaDto(
                        p.getDocumento(),
                        p.getNombre(),
                        p.getTelefono(),
                        p.getEdad(),
                        p.getProfesion(),
                        p.getPassword(),
                        p.getTipo());

                return personaVo;
            }
        }

        return null;
    }

    public List<PersonaDto> obtenerListapPersonas() {
        return PersonasUtilidades.listaPersonas;
    }

    public PersonaDto registrarPersona(PersonaDto persona) {

        for (PersonaDto obj : PersonasUtilidades.listaPersonas) {

            if (obj.getDocumento().equals(persona.getDocumento())) {
                return null;
            }
        }

        persona.setPassword(persona.getDocumento());
        PersonasUtilidades.listaPersonas.add(persona);

        return persona;
    }

    public PersonaDto consultarLogin(String documento, String pass) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(documento)
                    && p.getPassword().equals(pass)) {
                return p;
            }
        }

        return null;
    }

    public PersonaDto actualizarPersona(PersonaDto persona) {

        for (PersonaDto obj : PersonasUtilidades.listaPersonas) {

            if (obj.getDocumento().equals(persona.getDocumento())) {

                obj.setDocumento(persona.getDocumento());
                obj.setNombre(persona.getNombre());
                obj.setTelefono(persona.getTelefono());
                obj.setEdad(persona.getEdad());
                obj.setProfesion(persona.getProfesion());
                obj.setPassword(persona.getPassword());
                obj.setTipo(persona.getTipo());

                return obj;
            }
        }

        return null;
    }

    public boolean eliminarPersona(PersonaDto persona) {

        for (int i = 0; i < PersonasUtilidades.listaPersonas.size(); i++) {

            if (PersonasUtilidades.listaPersonas.get(i).getDocumento()
                    .equals(persona.getDocumento())) {

                PersonasUtilidades.listaPersonas.remove(i);
                return true;
            }
        }

        return false;
    }
}