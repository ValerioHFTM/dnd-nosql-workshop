package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.logging.Log;

import java.util.List;

@Path("/characters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CharacterResource {

    @Inject
    CharacterRepository repository;

    @Inject
    @Channel("character-events-out")
    Emitter<CharacterEvent> characterEventEmitter;

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
    public Response createCharacter(Character character) {
        // Check if a character with the same name already exists
        if (repository.find("name", character.name).firstResult() != null) {
            return Response.status(409).entity("Character with this name already exists!").build();
        }

        repository.persist(character);
        return Response.ok("Character saved!").build();
    }

    // PATCH update character by name
    @PATCH
    @Path("/{name}")
    public Response updateCharacter(@PathParam("name") String name, Character updatedCharacter) {
        Character character = repository.find("name", name).firstResult();
        if (character == null) {
            return Response.status(404).entity("Character not found").build();
        }

        // Optimistic Locking: Prüfen, ob die Version übereinstimmt
        if (updatedCharacter.version != character.version) {
            return Response.status(409).entity("Conflict: Character was updated by someone else!").build();
        }

        // Änderungen übernehmen
        if (updatedCharacter.race != null)
            character.race = updatedCharacter.race;
        if (updatedCharacter.characterClass != null)
            character.characterClass = updatedCharacter.characterClass;
        if (updatedCharacter.level > 0)
            character.level = updatedCharacter.level;

        // Version um 1 erhöhen
        character.version++;

        repository.update(character);
        return Response.ok("Character updated!").build();
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

    @PATCH
    @Path("/{name}/level-up")
    public String levelUpCharacter(@PathParam("name") String name) {
        Character character = repository.find("name", name).firstResult();
        if (character == null) {
            throw new WebApplicationException("Character not found", 404);
        }

        character.level++;
        repository.update(character);

        Log.info("✅ Character updated, preparing Kafka event...");
        System.out.println("✅ Character updated, preparing Kafka event...");
        System.out.flush();

        CharacterEvent event = new CharacterEvent(character.name, character.level);
        Log.info("🚀 Sending Kafka event: " + event);
        System.out.println("Sending Kafka event: " + event);
        System.out.flush(); // Forces output to appear immediately
        characterEventEmitter.send(event);

        return "Character leveled up!";
    }

}
