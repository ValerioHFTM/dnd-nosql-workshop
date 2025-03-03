package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import jakarta.annotation.PostConstruct;

@ApplicationScoped
public class CharacterRepository implements PanacheMongoRepository<Character> {

    @PostConstruct
    void init() {
        mongoCollection().createIndex(Indexes.ascending("name"), new IndexOptions().unique(true));
    }
}
