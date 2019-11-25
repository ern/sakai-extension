package org.sakaiproject.kaltura.conversion;

import org.mockito.Mockito;
import org.sakaiproject.kaltura.conversion.config.ConversionConfiguration;
import org.sakaiproject.kaltura.conversion.dom.CommonNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("application.properties")
@Import({ConversionConfiguration.class})
public class ConversionTestConfigration {

    @Bean
    public FieldProcessor getFieldProcessor() {
        FieldProcessor processor = new FieldProcessor();
        return processor;
    }

    @Bean
    public SakaiRepository getSakaiRepository() {
        SakaiRepository repository = Mockito.mock(SakaiRepository.class);
        Mockito.when(repository.insertLtiToolLink(Mockito.any())).thenReturn("http://localhost:8080/access/basiclti/site/EQLD_585N_0384/content:3");
        CommonNode.setRepository(repository);
        return repository;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return Mockito.mock(JdbcTemplate.class);
    }
}
