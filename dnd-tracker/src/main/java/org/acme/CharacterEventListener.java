package org.acme;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CharacterEventListener {

    @Incoming("character-events-in")
    public void onCharacterLevelUp(CharacterEvent event) {
        Log.info("Character leveled up: " + event.name() + " is now level " + event.newLevel());
    }
}
