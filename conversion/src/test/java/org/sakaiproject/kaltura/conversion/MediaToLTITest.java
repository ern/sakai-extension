package org.sakaiproject.kaltura.conversion;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sakaiproject.kaltura.conversion.config.ConversionConfiguration;
import org.sakaiproject.kaltura.conversion.serialize.Launch;
import org.sakaiproject.kaltura.conversion.serialize.Media;
import org.sakaiproject.kaltura.conversion.serialize.SerializationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConversionConfiguration.class})
public class MediaToLTITest {

    @Autowired private SerializationFactory factory;

    @Test
    public void deserializeLtiLaunch() {
        Launch link = factory.deserializeLunch(Launch.SAMPLE);
        Assert.assertNotNull(link);
        Assert.assertEquals("lti-launch", link.getCls());
        Assert.assertEquals("http://localhost:8080/access/basiclti/site/EQLD_585N_0384/content:3", link.getHref());
        Assert.assertEquals("noopener", link.getRel());
        Assert.assertEquals("_blank", link.getTarget());
        Assert.assertEquals("Media", link.getValue());
    }

    @Test
    public void deserializeMediaEmbed() {
        Media media = factory.deserializeMedia(Media.SAMPLE);
        Assert.assertNotNull(media);
        Assert.assertEquals("kaltura-lti-media", media.getCls());
        Assert.assertNotNull(media.getImg());
        Assert.assertEquals("", media.getImg().getAlt());
        Assert.assertEquals("285", media.getImg().getHeight());
        Assert.assertEquals("400", media.getImg().getWidth());
        Assert.assertEquals("IFrame", media.getImg().getTitle());
        Assert.assertEquals("https://2104601.kaf.kaltura.com/browseandembed/index/media/entryid/1_zd50sf3m/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/400x285/playerSkin/34223581/", media.getImg().getUrl());
        Assert.assertEquals("https://cfvod.kaltura.com/p/2104601/sp/210460100/thumbnail/entry_id/1_zd50sf3m/version/100001", media.getImg().getSrc());
    }

    @Test
    public void serializeObject() {
        Media media = factory.deserializeMedia(Media.SAMPLE);
        Assert.assertNotNull(media);
        String mediaElement = factory.serializeObject(media);
        Assert.assertNotNull(mediaElement);
        Assert.assertEquals(Media.SAMPLE, mediaElement);

        Launch link = factory.deserializeLunch(Launch.SAMPLE);
        Assert.assertNotNull(link);
        String linkElement = factory.serializeObject(link);
        Assert.assertNotNull(linkElement);
        Assert.assertEquals(Launch.SAMPLE, linkElement);
    }
}
