package org.sakaiproject.kaltura.conversion;

import java.util.List;
import java.util.stream.IntStream;

import org.sakaiproject.kaltura.conversion.config.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ConversionApplication implements CommandLineRunner {

    @Autowired private FieldProcessor fieldProcessor;
    @Autowired private List<Field> fields;
    @Autowired private SakaiRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ConversionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Start Kaltura media converter for LTI");

        IntStream.range(0 , args.length).forEach(i -> log.info("Argument {} = {}", i, args[i]));

        if (repository.existsLTITool()) {

            // scan database
            fields.forEach(f -> repository.executeFieldSearchQuery(f));

            // convert matches from database
            fields.stream().forEach(fieldProcessor::convert);

        } else {
            log.error("A Kaltura LTI Tool must be configured before running the conversion");
        }

    }

    private void scanDatabase() {

    }
}
