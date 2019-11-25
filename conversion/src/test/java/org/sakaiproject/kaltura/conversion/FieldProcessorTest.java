package org.sakaiproject.kaltura.conversion;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConversionTestConfigration.class})
public class FieldProcessorTest {

    @Autowired
    private FieldProcessor processor;

    @Autowired
    private SakaiRepository repository;

    @Test
    public void updateDocument() {
        ClassPathResource resource = new ClassPathResource("page.html");
        String update = processor.updateDocument(asString(resource));
        Assert.assertNotNull(update);
        Assert.assertTrue(update.contains("http://localhost:8080/access/basiclti/site/EQLD_585N_0384/content:3"));
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8")) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
