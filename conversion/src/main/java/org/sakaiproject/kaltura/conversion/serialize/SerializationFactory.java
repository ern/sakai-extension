package org.sakaiproject.kaltura.conversion.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializationFactory {

    private static final SerializationFactory factory = new SerializationFactory();
    private static final  XmlMapper mapper = new XmlMapper();

    private SerializationFactory() {
    }

    public static SerializationFactory getFactory() {
        return factory;
    }

    public String serializeObject(Object object) {
        String element = "";
        if (object != null) {
            try {
                element = mapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                log.warn("Could not serialize media to embed: [{}], {}", object, e.getMessage());
            }
        }
        return element;
    }

    public Media deserializeMedia(String element) {
        Media media = null;
        try {
            media = mapper.readValue(element, Media.class);
        } catch (IOException e) {
            log.warn("Could not deserialize embed: [{}], {}", element, e.getMessage());
        }
        return media;
    }

    public Launch deserializeLunch(String element) {
        Launch link = null;
        try {
            link = mapper.readValue(element, Launch.class);
        } catch (IOException e) {
            log.warn("Could not deserialize embed: [{}], {}", element, e.getMessage());
        }
        return link;
    }
}
