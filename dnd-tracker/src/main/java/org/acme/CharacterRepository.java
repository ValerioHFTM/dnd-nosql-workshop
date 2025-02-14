package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CharacterRepository implements PanacheMongoRepository<Character> {
}
