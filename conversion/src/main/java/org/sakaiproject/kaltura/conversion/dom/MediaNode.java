package org.sakaiproject.kaltura.conversion.dom;

import org.sakaiproject.kaltura.conversion.serialize.Launch;
import org.sakaiproject.kaltura.conversion.serialize.Media;
import org.w3c.dom.Node;

public class MediaNode extends CommonNode {

    public MediaNode(Node node) {
        super(node);
    }

    public Media createSerializableMedia() {
        return factory.deserializeMedia(toString());
    }

    public Node convertMedia() {
        Media media = createSerializableMedia();
        String href = repository.insertLtiToolLink(media);
        Launch launch = new Launch(href, media.getImg().getTitle());
        return toNode(factory.serializeObject(launch));
    }
}
