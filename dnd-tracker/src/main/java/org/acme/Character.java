package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Character extends PanacheMongoEntity {
    public String name;
    public String race;
    public String characterClass;
    public int level;
}
