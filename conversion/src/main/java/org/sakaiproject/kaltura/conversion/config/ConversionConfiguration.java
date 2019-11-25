package org.sakaiproject.kaltura.conversion.config;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.kaltura.conversion.dom.CommonNode;
import org.sakaiproject.kaltura.conversion.serialize.SerializationFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;

@Configuration
@ConfigurationProperties("conversion")
public class ConversionConfiguration {

    @Setter
    private List<Field> fields = new ArrayList<>();

    @Bean
    public List<Field> getFields() {
        return fields;
    }

    @Bean
    public SerializationFactory getSerializationFactory() {
        SerializationFactory factory = SerializationFactory.getFactory();
        CommonNode.setFactory(factory);
        return factory;
    }

}