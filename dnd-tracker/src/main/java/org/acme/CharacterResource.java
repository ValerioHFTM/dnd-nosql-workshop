package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/characters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CharacterResource {

    @Inject
    CharacterRepository repository;

    // GET all characters
    @GET
    public List<Character> getAllCharacters() {
        return repository.listAll();
    }

    // GET character by name
    @GET
    @Path("/{name}")
    public Character getCharacterByName(@PathParam("name") String name) {
        return repository.find("name", name).firstResult();
    }

    // POST create character
    @POST
    public String createCharacter(Character character) {
        repository.persist(character);
        return "Character saved!";
    }

    // PATCH update character by name
    @PATCH
    @Path("/{name}")
    public String updateCharacter(@PathParam("name") String name, Character updatedCharacter) {
        Character character = repository.find("name", name).firstResult();
        if (character == null) {
            throw new WebApplicationException("Character not found", 404);
        }

        // Update only if values are provided
        if (updatedCharacter.name != null)
            character.name = updatedCharacter.name;
        if (updatedCharacter.race != null)
            character.race = updatedCharacter.race;
        if (updatedCharacter.characterClass != null)
            character.characterClass = updatedCharacter.characterClass;
        if (updatedCharacter.level > 0)
            character.level = updatedCharacter.level;

        repository.update(character);
        return "Character updated!";
    }

    // DELETE character by name
    @DELETE
    @Path("/{name}")
    public String deleteCharacter(@PathParam("name") String name) {
        long deleted = repository.delete("name", name);
        if (deleted == 0) {
            throw new WebApplicationException("Character not found", 404);
        }
        return "Character deleted!";
    }
}
